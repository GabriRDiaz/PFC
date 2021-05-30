/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.dao;

import grd.pfc.pojo.Empleado;
import grd.pfc.pojo.Producto;
import grd.pfc.pojo.Sugerencia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel
 */
public class ManagerDAO {
    String connectionUrl = "jdbc:sqlserver://GABRIRDIAZ\\GRDSQL;user=sa;password=abc123.";
    String insertProducto = "INSERT INTO [PFC].[dbo].[Productos](Nombre,Descripcion,PrecioSinIVA,Descuento,IdTipoIVA,IdMarca,Referencia,Modelo,Color,Stock,Coste) \n" +
                            "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    String deleteSeccionesProductos = "DELETE FROM [PFC].[dbo].[SeccionesProductos] WHERE IdProducto LIKE (select id from Productos where Referencia=?)";
    String insertSeccionesProductos = "INSERT INTO [PFC].[dbo].[SeccionesProductos](IdProducto,IdSeccion) VALUES(?,?)";
    String getIdProd = "SELECT Id FROM [PFC].[dbo].[Productos] WHERE Referencia LIKE ?";
    String getMarcaId= "SELECT Id FROM [PFC].[dbo].[Marcas] WHERE Marca LIKE ?";
    String getIvaId= "SELECT Id FROM [PFC].[dbo].[IVAs] WHERE IVA LIKE ?";
    String getSectionId = "SELECT Id FROM [PFC].[dbo].[Secciones] WHERE Nombre LIKE ?";
    String getRequestSections = "SELECT sg.Id FROM [PFC].[dbo].[Sugerencias] sg\n" +
                                "INNER JOIN [PFC].[dbo].[Secciones] s ON sg.IdSeccion=s.Id \n" +
                                "INNER JOIN [PFC].[dbo].[EmpleadosSecciones] es ON s.Id=es.IdSeccion \n" +
                                "INNER JOIN [PFC].[dbo].[Empleados] e ON es.IdEmpleado=e.Id \n" +
                                "WHERE e.Id=?";
    String getRequests = "SELECT Id,Sugerencia,Fecha,Nombre,Revisada FROM [PFC].[dbo].[SugerenciasEmpleado] WHERE Id=?";
    public ManagerDAO(){}
    
    public ArrayList<Sugerencia> getRequests(ArrayList<Integer> requests){
        ArrayList<Sugerencia> sugerencias = new ArrayList<Sugerencia>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            requests.forEach(r->{
                try {
                    PreparedStatement ps = connectDB.prepareStatement(getRequests);
                    ps.setInt(1,r);
                    ps.executeQuery();
                    ResultSet rs = ps.getResultSet();
                    while(rs.next()){
                       sugerencias.add(new Sugerencia(
                               rs.getInt(1),
                               rs.getString(2),
                               rs.getDate(3),
                               rs.getString(4),
                               rs.getInt(5)
                       ));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });return sugerencias;
        }catch (SQLException ex) {ex.printStackTrace();}
        return null;
    }
    
    public ArrayList<Integer> getRequestSections(int idUser){
        ArrayList<Integer> sections = new ArrayList<Integer>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getRequestSections);
            ps.setInt(1,idUser);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                sections.add(rs.getInt(1));
            }
            return sections;
        }catch (SQLException ex) {ex.printStackTrace();}
        return null;
    }
    public ArrayList<Sugerencia> getSugerencias(){
        return null;
    }
    public int getMarcaId(String marca){
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getMarcaId);
            ps.setString(1,marca);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException ex) {ex.printStackTrace();}
        return -1;
    }
    
    public int getSectionId(String seccion){
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getSectionId);
            ps.setString(1,seccion);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException ex) {ex.printStackTrace();}
        return -1;
    }
    
    public int getIvaId(String iva){
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getIvaId);
            ps.setString(1,iva);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException ex) {ex.printStackTrace();}
        return -1;
    }
    
    public int getIdProductByRef(String referencia){
    try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
        
        PreparedStatement ps = connectDB.prepareStatement(getIdProd);
            ps.setString(1,referencia);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }else{return -1;}
        }catch (SQLException ex) {ex.printStackTrace();}
    return -1;
    }
    
    public int insertProducto(Producto producto){
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(insertProducto);
            ps.setString(1,producto.getNombre());
            ps.setString(2,producto.getDescripcion());
            ps.setDouble(3,producto.getPrecioSinIVA());
            ps.setDouble(4,producto.getDescuento());
            ps.setInt(5,producto.getIdIVA());
            ps.setInt(6,producto.getIdMarca());
            ps.setString(7,producto.getReferencia());
            ps.setString(8,producto.getModelo());
            ps.setString(9,producto.getColor());
            ps.setInt(10,producto.getStock());
            ps.setDouble(11,producto.getCoste());
            ps.executeUpdate();
            
            ps = connectDB.prepareStatement(insertSeccionesProductos);
            
            for(int i=0;i<producto.getSecciones().size();i++){
                System.out.println("UPD: "+getIdProductByRef(producto.getReferencia()));
                System.out.println("UPD: "+producto.getSecciones().get(i));
                ps.setInt(1,getIdProductByRef(producto.getReferencia()));
                ps.setInt(2,producto.getSecciones().get(i));
                ps.executeUpdate();
            }
            
        }catch (SQLException ex) {ex.printStackTrace();}
        
        return 1;
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
