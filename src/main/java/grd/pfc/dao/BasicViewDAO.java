/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.dao;

import grd.pfc.pojo.Producto;
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
public class BasicViewDAO {
    public BasicViewDAO(){}
    String connectionUrl = "jdbc:sqlserver://GABRIRDIAZ\\GRDSQL;user=sa;password=abc123.";
    String getProductos = "SELECT p.Id,Nombre,Descripcion,PrecioSinIVA,Descuento,m.Marca,Referencia,Modelo,Color,Stock,i.Tipo FROM [PFC].[dbo].[Productos] p\n" +
                           "JOIN [PFC].[dbo].[Marcas] m ON p.IdMarca=m.Id\n" +
                           "JOIN [PFC].[dbo].[IVAS] i ON p.IdTipoIVA=i.Id";

    public ArrayList<Producto> getProductos(){
        ArrayList<Producto> productos = new ArrayList<Producto>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getProductos);
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
                        rs.getDouble("Tipo")
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
