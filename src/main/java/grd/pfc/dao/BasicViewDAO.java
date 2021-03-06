/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.dao;

import grd.pfc.pojo.IVA;
import grd.pfc.pojo.Marca;
import grd.pfc.pojo.Producto;
import grd.pfc.pojo.Seccion;
import grd.pfc.pojo.Sugerencia;
import grd.pfc.singleton.InfoBundle;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel
 */
public class BasicViewDAO {
    public BasicViewDAO(){}
    String connectionUrl = "jdbc:sqlserver://GABRIRDIAZ\\GRDSQL;user=sa;password=abc123.";
    String getProductos =   "SELECT DISTINCT p.Id,p.Nombre,p.Descripcion,PrecioSinIVA,Descuento,m.Marca,Referencia,Modelo,Color,Stock,i.Tipo,Coste FROM [PFC].[dbo].[Productos] p\n" +
                            "JOIN [PFC].[dbo].[Marcas] m ON p.IdMarca=m.Id\n" +
                            "JOIN [PFC].[dbo].[IVAS] i ON p.IdTipoIVA=i.Id\n" +
                            "JOIN [PFC].[dbo].[SeccionesProductos] sp ON p.Id=sp.IdProducto\n" +
                            "JOIN [PFC].[dbo].[Secciones] s ON sp.IdSeccion=s.Id\n" +
                            "JOIN [PFC].[dbo].[EmpleadosSecciones] es ON s.Id=es.IdSeccion\n" +
                            "JOIN [PFC].[dbo].[Empleados] e ON es.IdEmpleado=e.Id\n" +
                            "WHERE e.Id=";
    
    String getSecciones = "SELECT s.Id,s.Nombre,s.Descripcion FROM [PFC].[dbo].[Secciones] s \n" +
                          "JOIN [PFC].[dbo].[EmpleadosSecciones] es ON s.Id=es.IdSeccion\n" +
                          "JOIN [PFC].[dbo].[Empleados] e ON es.IdEmpleado=e.Id\n" +
                          "WHERE e.Id=";
    String getIdSeccion = "SELECT s.Id FROM [PFC].[dbo].[Secciones] s WHERE s.Nombre=?";
    String getMarcas = "SELECT m.Id,m.Marca FROM [PFC].[dbo].[Marcas] m";
    String getIva = "SELECT i.Id,i.iva,i.tipo FROM [PFC].[dbo].[IVAS] i";
    String updStock = "UPDATE [PFC].[dbo].[Productos] SET Stock=? WHERE Id=?";
    String insertSugerencia =   "DECLARE @pResult SMALLINT\n" +
                                "EXEC pAddSugerencia\n" +
                                "@pSugerencia=?,\n" +
                                "@pIdSeccion=?,\n" +
                                "@pIdEmpleado=?,\n" +
                                "@pResult = @pResult OUTPUT";
    
   public int insertSugerencia(Sugerencia sugerencia){
    Connection conn=getConnetion();
        if(conn!=null){
            CallableStatement cstmt;
            try {
                cstmt = conn.prepareCall("{call [PFC].[dbo].[pAddSugerencia](?,?,?,?)}");
                cstmt.setString(1, sugerencia.getSugerencia());
                cstmt.setInt(2, sugerencia.getIdSeccion());
                cstmt.setInt(3,sugerencia.getIdEmpleado());
                cstmt.registerOutParameter(4, Types.INTEGER);
                cstmt.execute();
            return cstmt.getInt(4);
            } catch (SQLException ex) {
                Logger.getLogger(AdministracionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }
        return -1;
   }
    
    
    public ArrayList<Seccion> getSecciones(int idEmpleado){
        ArrayList<Seccion> secciones = new ArrayList<Seccion>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getSecciones.concat(""+idEmpleado));
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Seccion seccion = new Seccion(
                        rs.getInt("Id"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion")
                );
                secciones.add(seccion);
            }
        }catch (SQLException ex) {ex.printStackTrace();}
        return secciones;
    }
    
    public int getIdSeccion(String seccion){ //No controlo que existan varios con mismo nombre porque ya se controla en el form de creaci??n de secciones.
        int idSeccion = -1;
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){ 
            PreparedStatement ps = connectDB.prepareStatement(getIdSeccion);
            ps.setString(1, seccion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                    idSeccion = rs.getInt(1);
            }
        }catch (SQLException ex) {ex.printStackTrace();}
        return idSeccion;
    }
    
    public ArrayList<Marca> getMarcas(){
    ArrayList<Marca> marcas = new ArrayList<Marca>();
    try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
        PreparedStatement ps = connectDB.prepareStatement(getMarcas);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Marca marca = new Marca(
                    rs.getInt("Id"),
                    rs.getString("Marca")
            );
            marcas.add(marca);
        }
    }catch (SQLException ex) {ex.printStackTrace();}
    return marcas;
}
public int updStock(int stock,int id){
    try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
        PreparedStatement ps = connectDB.prepareStatement(updStock);
        ps.setInt(1,stock);
        ps.setInt(2,id);
        return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BasicViewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    return -1;
}
public ArrayList<IVA> getIvas(){
    ArrayList<IVA> ivas = new ArrayList<IVA>();
    try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
        PreparedStatement ps = connectDB.prepareStatement(getIva);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            IVA iva = new IVA(
                    rs.getInt("Id"),
                    rs.getString("Iva"),
                    rs.getDouble("Tipo")
            );
            ivas.add(iva);
        }
    }catch (SQLException ex) {ex.printStackTrace();}
    return ivas;
}
    public ArrayList<Producto> getProductos(String filter){
        ArrayList<Producto> productos = new ArrayList<Producto>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getProductos.concat(InfoBundle.getInfoBundle().getIdEmpleado()+filter+" AND p.Eliminado=0"));
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Producto producto = new Producto(
                        rs.getInt("Id"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getDouble("PrecioSinIVA"),
                        rs.getDouble("Descuento"),
                        rs.getString("Marca"),
                        rs.getString("Referencia"),
                        rs.getString("Modelo"),
                        rs.getString("Color"),
                        rs.getInt("Stock"),
                        rs.getString("Tipo"),
                        rs.getDouble("Coste"),
                        null
                );
                productos.add(producto);
            }
        }catch (SQLException ex) {ex.printStackTrace();}
        return productos;
        }
    
    private Connection getConnetion() {
        Connection conn=null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(connectionUrl);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) { 
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
