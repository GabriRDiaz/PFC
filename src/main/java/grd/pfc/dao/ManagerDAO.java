/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.dao;

import grd.pfc.pojo.Cliente;
import grd.pfc.pojo.Empleado;
import grd.pfc.pojo.Estado;
import grd.pfc.pojo.Pais;
import grd.pfc.pojo.Pedido;
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
public class ManagerDAO {
    String connectionUrl = "jdbc:sqlserver://GABRIRDIAZ\\GRDSQL;user=sa;password=abc123.";
    String insertProducto = "INSERT INTO [PFC].[dbo].[Productos](Nombre,Descripcion,PrecioSinIVA,Descuento,IdTipoIVA,IdMarca,Referencia,Modelo,Color,Stock,Coste) \n" +
                            "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    String deleteSeccionesProductos = "DELETE FROM [PFC].[dbo].[SeccionesProductos] WHERE IdProducto LIKE (select id from Productos where Referencia=?)";
    String insertSeccionesProductos = "INSERT INTO [PFC].[dbo].[SeccionesProductos](IdProducto,IdSeccion) VALUES(?,?)";
    String getIdProd = "SELECT Id FROM [PFC].[dbo].[Productos] WHERE Referencia LIKE ?";
    String getIdProdEdit = "SELECT Id FROM [PFC].[dbo].[Productos] WHERE Referencia LIKE ? AND Id<>?";
    String getMarcaId= "SELECT Id FROM [PFC].[dbo].[Marcas] WHERE Marca LIKE ?";
    String getIvaId= "SELECT Id FROM [PFC].[dbo].[IVAs] WHERE Tipo LIKE ?";
    String getIvaIdTipo= "SELECT Id FROM [PFC].[dbo].[IVAs] WHERE IVA LIKE ?";
    String getSectionId = "SELECT Id FROM [PFC].[dbo].[Secciones] WHERE Nombre LIKE ?";
    String getRequestSections = "SELECT sg.Id FROM [PFC].[dbo].[Sugerencias] sg\n" +
                                "INNER JOIN [PFC].[dbo].[Secciones] s ON sg.IdSeccion=s.Id \n" +
                                "INNER JOIN [PFC].[dbo].[EmpleadosSecciones] es ON s.Id=es.IdSeccion \n" +
                                "INNER JOIN [PFC].[dbo].[Empleados] e ON es.IdEmpleado=e.Id \n" +
                                "WHERE e.Id=?";
    String getRequests = "SELECT Id,Sugerencia,Fecha,Nombre,Revisada FROM [PFC].[dbo].[SugerenciasEmpleado] WHERE Id=?";
    String updRevisada = "UPDATE [PFC].[dbo].[Sugerencias] SET Revisada=? WHERE Id=?";
    //Get productos for EditProduct
    String getProductos = "SELECT p.Id,p.Nombre,p.Descripcion,PrecioSinIVA,Descuento,m.Marca,Referencia,Modelo,Color,Coste,i.IVA FROM [PFC].[dbo].[Productos] p " +
                          "JOIN [PFC].[dbo].[Marcas] m ON p.IdMarca=m.Id " +
                          "JOIN [PFC].[dbo].[IVAS] i ON p.IdTipoIVA=i.Id " +
                          "JOIN [PFC].[dbo].[SeccionesProductos] sp ON p.Id=sp.IdProducto " +
                          "JOIN [PFC].[dbo].[Secciones] s ON sp.IdSeccion=s.Id " +
                          "JOIN [PFC].[dbo].[EmpleadosSecciones] es ON s.Id=es.IdSeccion " +
                          "JOIN [PFC].[dbo].[Empleados] e ON es.IdEmpleado=e.Id " +
                          "WHERE e.Id=";
    //Get sections for each product in EditProduct
    String getProductSections ="SELECT s.Id,s.Nombre from [PFC].[dbo].[Secciones] s " +
                               "INNER JOIN [PFC].[dbo].[SeccionesProductos] sp ON s.Id=sp.IdSeccion " +
                               "INNER JOIN [PFC].[dbo].[Productos] p ON sp.IdProducto=p.Id " +
                               "WHERE p.Id=?";
    String updProducto = "UPDATE [PFC].[dbo].[Productos] " +
                         "SET Nombre=?, " +
                         "Descripcion=?, " +
                         "PrecioSinIVA=?, " +
                         "Descuento=?, " +
                         "IdTipoIVA=?, " +
                         "IdMarca=?, " +
                         "Referencia=?, " +
                         "Modelo=?, " +
                         "Color=?, " +
                         "Coste=? " +
                         "WHERE Id=?";
    String delSeccionesProductos = "DELETE FROM [PFC].[dbo].[SeccionesProductos] WHERE IdProducto=?";
    
    String getFilteredPaises = "SELECT id,nombre FROM [PFC].[dbo].[Paises] WHERE nombre LIKE ?";
    String getClientes = "SELECT Id,CONCAT(Nombre,' ',Apellidos) AS 'Nombre' FROM [PFC].[dbo].[Clientes]";
    String getEstados = "SELECT Id,Estado FROM [PFC].[dbo].[EstadosPedidos]";
   
    
    String getPedidos = "SELECT ped.Id,CONCAT(c.Nombre,' ',c.Apellidos) AS Cliente,FechaExp,FechaEnvio,e.Estado,Destinatario,Direccion,TelefonoContacto,p.Nombre AS 'Pais' FROM [PFC].[dbo].[Pedidos] ped " +
                        "INNER JOIN [PFC].[dbo].[Clientes] c ON ped.IdCliente=c.Id " +
                        "INNER JOIN [PFC].[dbo].[Paises] p ON ped.IdPais=p.Id " +
                        "INNER JOIN [PFC].[dbo].[EstadosPedidos] e ON ped.IdEstado=e.Id "+
                        "WHERE e.Id<>5";
    
    public ManagerDAO(){}
    
    public int editPedido(Pedido pedido){
        Connection conn=getConnection();
        if(conn!=null){
            CallableStatement cstmt;
            try {
                cstmt = conn.prepareCall("{call [PFC].[dbo].[pEditPedido](?,?,?,?,?,?,?,?,?)}");
                cstmt.setInt(1,pedido.getId());
                cstmt.setString(2,pedido.getCliente());
                cstmt.setDate(3,pedido.getFechaEnvio());
                cstmt.setString(4,pedido.getEstado());
                cstmt.setString(5,pedido.getDestinatario());
                cstmt.setString(6,pedido.getDireccion());
                cstmt.setString(7,pedido.getTelefono());
                cstmt.setString(8,pedido.getPais());
            cstmt.registerOutParameter(9, Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(9);
            } catch (SQLException ex) {
                Logger.getLogger(AdministracionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
        return -1;
    }
    
    public ArrayList<Pedido> getPedidos(){
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getPedidos);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                pedidos.add(new Pedido(
                        rs.getInt("Id"),
                        rs.getString("Cliente"),
                        rs.getDate("FechaExp"),
                        rs.getDate("FechaEnvio"),
                        rs.getString("Estado"),
                        rs.getString("Destinatario"),
                        rs.getString("Direccion"),
                        rs.getString("TelefonoContacto"),
                        rs.getString("Pais")
                ));
            }
        }catch (SQLException ex) {ex.printStackTrace();}
        return pedidos;
    }
    
    public int addLineaPedido(int idProducto,int cantidad){
        Connection conn=getConnection();
        if(conn!=null){
            CallableStatement cstmt;
            try {
                cstmt = conn.prepareCall("{call [PFC].[dbo].[pAddLineaPedido](?,?,?)}");
                cstmt.setInt(1,idProducto);
                cstmt.setInt(2,cantidad);
            cstmt.registerOutParameter(3, Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(3);
            } catch (SQLException ex) {
                Logger.getLogger(AdministracionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
        return -1;
    }
    
    public int addPedido(Pedido pedido){
        Connection conn=getConnection();
        if(conn!=null){
            CallableStatement cstmt;
            try {
                cstmt = conn.prepareCall("{call [PFC].[dbo].[pAddPedido](?,?,?,?,?,?,?,?)}");
                cstmt.setString(1,pedido.getCliente());
                cstmt.setDate(2,pedido.getFechaEnvio());
                cstmt.setString(3,pedido.getEstado());
                cstmt.setString(4,pedido.getDestinatario());
                cstmt.setString(5,pedido.getDireccion());
                cstmt.setString(6,pedido.getTelefono());
                cstmt.setString(7,pedido.getPais());
            cstmt.registerOutParameter(8, Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(8);
            } catch (SQLException ex) {
                Logger.getLogger(AdministracionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
        return -1;
    }
    
    
    public ArrayList<Estado> getEstados(){
        ArrayList<Estado> estados = new ArrayList<Estado>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getEstados);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Estado estado = new Estado(
                        rs.getInt("id"),
                        rs.getString("Estado")
                );
                estados.add(estado);
            }
        }catch (SQLException ex) {ex.printStackTrace();}
        return estados;
    }
    
    public ArrayList<Pais> getFilteredPaises(String filter){
        ArrayList<Pais> paises = new ArrayList<Pais>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getFilteredPaises);
            ps.setString(1, filter+"%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Pais pais = new Pais(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
                paises.add(pais);
            }
        }catch (SQLException ex) {ex.printStackTrace();}
        return paises;
    }
        
    
    public ArrayList<Cliente> getClientes(){
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getClientes);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
                clientes.add(cliente);
            }
        }catch (SQLException ex) {ex.printStackTrace();}
        return clientes;
    }
    
    public int updProducto(Producto producto){
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(updProducto);
            ps.setString(1,producto.getNombre());
            ps.setString(2,producto.getDescripcion());
            ps.setDouble(3,producto.getPrecioSinIVA());
            ps.setDouble(4,producto.getDescuento());
            ps.setInt(5,producto.getIdIVA());
            ps.setInt(6,producto.getIdMarca());
            ps.setString(7,producto.getReferencia());
            ps.setString(8,producto.getModelo());
            ps.setString(9,producto.getColor());
            ps.setDouble(10,producto.getCoste());
            ps.setInt(11,producto.getId());
            ps.executeUpdate();
            
            ps = connectDB.prepareStatement(delSeccionesProductos);
            ps.setInt(1,producto.getId());
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
    
    //Get secciones producto for EditProduct
        public ArrayList<Seccion> getSeccionesProducto(int idProd){
               ArrayList<Seccion> secciones = new ArrayList<Seccion>();
               try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
                   PreparedStatement ps = connectDB.prepareStatement(getProductSections);
                   ps.setInt(1,idProd);
                   ResultSet rs = ps.executeQuery();
                   while(rs.next()){
                       secciones.add(new Seccion(
                       rs.getInt(1),
                       rs.getString(2)
                       ));
                   }
               }catch (SQLException ex) {ex.printStackTrace();}
               return secciones;
           }
    //Get productos for EditProduct
    public ArrayList<Producto> getProductos(){
        ArrayList<Producto> productos = new ArrayList<Producto>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getProductos.concat(""+InfoBundle.getInfoBundle().getIdEmpleado()));
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
                        rs.getString("IVA"),
                        rs.getDouble("Coste")
                );
                productos.add(producto);
            }
        }catch (SQLException ex) {ex.printStackTrace();}
        return productos;
    }
    
    public int updRevisada(int isRevisada, int idSug){
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(updRevisada);
            ps.setInt(1,isRevisada);
            ps.setInt(2,idSug);
            ps.executeUpdate();
            return 1;
        }catch (SQLException ex) {ex.printStackTrace();}
        return -1;
    }
    
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
            PreparedStatement ps = connectDB.prepareStatement(getIvaIdTipo);
            ps.setString(1,iva);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException ex) {ex.printStackTrace();}
        return -1;
    }
    public int getIvaIdTipo(String iva){
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
    
    public int getIdProductByRefEdit(String referencia, int idProd){
    try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
        
        PreparedStatement ps = connectDB.prepareStatement(getIdProdEdit);
            ps.setString(1,referencia);
            ps.setInt(2,idProd);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }else{return -1;}
        }catch (SQLException ex) {ex.printStackTrace();}
    return -1;
    }
    public int insertProducto(Producto producto){
            Connection conn=getConnection();
            if(conn!=null){
                CallableStatement cstmt;
                try {
                    cstmt = conn.prepareCall("{call [PFC].[dbo].[pAddProducto](?,?,?,?,?,?,?,?,?,?,?,?)}");
                    cstmt.setString(1,producto.getNombre());
                    cstmt.setString(2,producto.getDescripcion());
                    cstmt.setDouble(3,producto.getPrecioSinIVA());
                    cstmt.setDouble(4,producto.getDescuento());
                    cstmt.setString(5,producto.getIvaStr());
                    cstmt.setString(6,producto.getMarca());
                    cstmt.setString(7,producto.getReferencia());
                    cstmt.setString(8,producto.getModelo());
                    cstmt.setString(9,producto.getColor());
                    cstmt.setInt(10,producto.getStock());
                    cstmt.setDouble(11,producto.getCoste());
                cstmt.registerOutParameter(12, Types.INTEGER);
                cstmt.execute();
                
                int idProd=-1;
                
                try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
                    PreparedStatement ps = connectDB.prepareStatement("SELECT MAX(Id) FROM [PFC].[dbo].[Productos]");
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    idProd=rs.getInt(1);
                }catch (SQLException ex) {ex.printStackTrace();}
                
                for(int i=0;i<producto.getSecciones().size();i++){
                    try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
                            PreparedStatement ps = connectDB.prepareStatement(insertSeccionesProductos);
                            ps.setInt(1,idProd);
                            ps.setInt(2,producto.getSecciones().get(i));
                            ps.executeUpdate();
                    }catch (SQLException ex) {ex.printStackTrace();}
                }

                
                return cstmt.getInt(12);
                } catch (SQLException ex) {
                    Logger.getLogger(AdministracionDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
            return -1;
    }
    
    private Connection getConnection() {
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
