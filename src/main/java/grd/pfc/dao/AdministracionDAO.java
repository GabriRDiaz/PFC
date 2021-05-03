/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
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
public class AdministracionDAO {
    String connectionUrl = "jdbc:sqlserver://GABRIRDIAZ\\GRDSQL;user=sa;password=abc123.";
    String getContratos = "SELECT [TipoContrato] AS contrato from [PFC].[dbo].[TiposContrato] WHERE [TipoContrato] NOT LIKE 'Accionista'";
    String getContratoId = "SELECT [Id] AS id from [PFC].[dbo].[TiposContrato] WHERE [TipoContrato]=?";
    String addEmpleado = "DECLARE @pResult SMALLINT \n" +
                        "EXEC pAddEmpleado \n" +
                        "@pNombre=?,\n" +
                        "@pApellidos=?,\n" +
                        "@pFechaContratacion=?,\n" +
                        "@pFechaSalida=?,\n" +
                        "@pSalario=?,\n" +
                        "@pIdTipoContrato=?,\n" +
                        "@pEmail =?,\n" +
                        "@pPassword=?,\n" +
                        "@pResult=@pResult OUTPUT";
    public AdministracionDAO(){}
    
    public ArrayList<String> getContratos(){
        ArrayList<String> contratos = new ArrayList<String>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getContratos);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                contratos.add(rs.getString("contrato"));
            }
        }catch (SQLException ex) {ex.printStackTrace();}
        return contratos;
        }
    
    public int addEmpleado(String nombre,String apellidos,Date fechaContratacion, Date fechaSalida,double salario,int idContrato,String email,String pwd){
        Connection conn=getConnetion();
        if(conn!=null){
            CallableStatement cstmt;
            try {
                cstmt = conn.prepareCall("{call [PFC].[dbo].[pAddEmpleado](?,?,?,?,?,?,?,?,?)}");
                cstmt.setString(1,nombre);
                cstmt.setString(2,apellidos);
                cstmt.setDate(3,fechaContratacion);
                cstmt.setDate(4,fechaSalida);
                cstmt.setDouble(5,salario);
                cstmt.setInt(6,idContrato);
                cstmt.setString(7,email);
                cstmt.setString(8,pwd);
            cstmt.registerOutParameter(9, Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(9);
            } catch (SQLException ex) {
                Logger.getLogger(AdministracionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }
        return -1;
    }
    
    public int getContratoId(String selection){
        int id=-1;
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getContratoId);
            ps.setString(1,selection);
            ResultSet rs = ps.executeQuery();
            rs.next();
            id=rs.getInt("id");
        }catch (SQLException ex) {ex.printStackTrace();}
        return id;
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
