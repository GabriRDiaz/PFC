/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gabriel
 */
public class ChartDAO {
    String connectionUrl = "jdbc:sqlserver://GABRIRDIAZ\\GRDSQL;user=sa;password=abc123.";
    
    String getPedidosCliente = "SELECT Cliente,Pedidos FROM [PFC].[dbo].[PedidosClienteMes]";
    String getEmpleadosSeccion = "SELECT Seccion,Empleados FROM [PFC].[dbo].[EmpleadosPorSeccion]";
    public ChartDAO(){}
    
    public Map<String,Integer> getClientePedidos(){
        Map<String, Integer> list = new HashMap<>();
            try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
                    PreparedStatement ps = connectDB.prepareStatement(getPedidosCliente);
                    ResultSet rs = ps.executeQuery();
                     while(rs.next()){
                        list.put(rs.getString("Cliente"), rs.getInt("Pedidos"));
                }
                
            } catch (SQLException ex) {ex.printStackTrace();}
            return list;
    }
    
    public Map<String,Integer> getEmpleadosSeccion(){
        Map<String, Integer> list = new HashMap<>();
            try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
                    PreparedStatement ps = connectDB.prepareStatement(getEmpleadosSeccion);
                    ResultSet rs = ps.executeQuery();
                     while(rs.next()){
                        list.put(rs.getString("Seccion"), rs.getInt("Empleados"));
                }
                
            } catch (SQLException ex) {ex.printStackTrace();}
            return list;
    }
}
