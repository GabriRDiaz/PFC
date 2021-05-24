/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.dao;

import grd.pfc.pojo.Empleado;
import grd.pfc.pojo.Seccion;
import grd.pfc.utils.Utils;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
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
    String getTipoContrato = "SELECT [TipoContrato] AS tipo from [PFC].[dbo].[TiposContrato] WHERE [Id]=?";
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
    String getEmpleados = "SELECT Id,Nombre,Apellidos,FechaContratacion,FechaSalida,Salario,IdTipoContrato,Email,Pwd FROM [PFC].[dbo].[Empleados] WHERE IdTipoContrato<>9";
    String editEmpleado = "UPDATE [PFC].[dbo].[Empleados] "
            + "SET Nombre=?,Apellidos=?,FechaContratacion=?,FechaSalida=?,Salario=?,IdTipoContrato=?,Email=? "
            + "WHERE Id=?";
    String getEmpPerfil = "SELECT emp.Id,emp.Nombre,emp.Apellidos,p.Perfil from [PFC].[dbo].[Empleados] emp JOIN [PFC].[dbo].[Perfiles] p ON emp.IdPerfil=p.Id WHERE p.Perfil NOT LIKE 'Jefe'";
    String getPerfiles = "SELECT [Perfil] AS perfil from [PFC].[dbo].[Perfiles] WHERE Perfil NOT LIKE 'Jefe'";
    String getPerfilId = "SELECT [Id] AS id from [PFC].[dbo].[Perfiles] WHERE [Perfil] LIKE ?";
    String editPerfilEmpleado = "UPDATE [PFC].[dbo].[Empleados] SET IdPerfil=? WHERE Id=?";
    String getSecciones = "SELECT sec.Id,sec.Nombre,sec.Descripcion,emp.Nombre FROM [PFC].[dbo].[Secciones] sec " +
                           "JOIN [PFC].[dbo].[EmpleadosSecciones] es ON sec.IdResponsable=es.IdEmpleado " +
                           "JOIN [PFC].[dbo].[Empleados] emp ON es.IdEmpleado=emp.Id ORDER BY sec.Id ASC";
    
    public ArrayList<Empleado> getEmpleadosPerfil(){
        ArrayList<Empleado> empleados = new ArrayList<Empleado>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getEmpPerfil);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                empleados.add(new Empleado(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4)
                ));
            }
        }catch (SQLException ex) {ex.printStackTrace();}
        return empleados;
    }
    
    public ArrayList<Seccion> getSecciones(){
        ArrayList<Seccion> secciones = new ArrayList<Seccion>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getSecciones);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                secciones.add(new Seccion(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4)
                ));
            }
        }catch (SQLException ex) {ex.printStackTrace();}
        return secciones;
    }
    
    public void editPerfilEmpleado(int idPerfil, int idEmpleado){
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(editPerfilEmpleado);
            ps.setInt(1,idPerfil);
            ps.setInt(2,idEmpleado);
            ps.executeUpdate();
        }catch (SQLException ex) {ex.printStackTrace();}
    }
    
    public ArrayList<String> getPerfiles(){
        ArrayList<String> perfiles = new ArrayList<String>();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getPerfiles);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                perfiles.add(rs.getString(1));
            }
        }catch (SQLException ex) {ex.printStackTrace();}
        return perfiles;
        }
    
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
    
    public int addEmpleado(Empleado empleado){
        Connection conn=getConnetion();
        if(conn!=null){
            CallableStatement cstmt;
            try {
                cstmt = conn.prepareCall("{call [PFC].[dbo].[pAddEmpleado](?,?,?,?,?,?,?,?,?)}");
                cstmt.setString(1,empleado.getNombre());
                cstmt.setString(2,empleado.getApellidos());
                cstmt.setDate(3,empleado.getContrato());
                cstmt.setDate(4,empleado.getSalida());
                cstmt.setDouble(5,empleado.getSalario());
                cstmt.setInt(6,empleado.getIdTipoContrato());
                cstmt.setString(7,empleado.getMail());
                cstmt.setString(8,empleado.getPwd());
            cstmt.registerOutParameter(9, Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(9);
            } catch (SQLException ex) {
                Logger.getLogger(AdministracionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }
        return -1;
    }
    public void editEmpleado(Empleado empleado){
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(editEmpleado);
            ps.setString(1,empleado.getNombre());
            ps.setString(2,empleado.getApellidos());
            ps.setDate(3,Utils.dateToSql(empleado.getContrato()));
            ps.setDate(4,Utils.dateToSql(empleado.getSalida()));
            ps.setDouble(5,empleado.getSalario());
            ps.setInt(6,empleado.getIdTipoContrato());
            ps.setString(7,empleado.getMail());
            ps.setInt(8,empleado.getId());
            ps.executeUpdate();
        }catch (SQLException ex) {ex.printStackTrace();}
            if(empleado.getPwd().equals("")){
                System.out.println("No modifica pwd");
            }else{
                System.out.println("Modifica pwd");
            }
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
    
     public int getPerfilId(String selection){
        int id=-1;
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getPerfilId);
            ps.setString(1,selection);
            ResultSet rs = ps.executeQuery();
            rs.next();
            id=rs.getInt("id");
        }catch (SQLException ex) {ex.printStackTrace();}
        return id;
    }
     
    public String getTipoContrato(int selection){
        String tipo="";
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getTipoContrato);
            ps.setInt(1,selection);
            ResultSet rs = ps.executeQuery();
            rs.next();
            tipo=rs.getString("tipo");
        }catch (SQLException ex) {ex.printStackTrace();}
        return tipo;
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
        
    public ArrayList<Empleado> getEmpleados(){
        ArrayList<Empleado> empleados = new ArrayList();
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getEmpleados);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Empleado empleado = new Empleado(
                        rs.getInt("Id"),
                        rs.getString("Nombre"),
                        rs.getString("Apellidos"),
                        rs.getDate("FechaContratacion"),
                        rs.getDate("FechaSalida"),
                        rs.getDouble("Salario"),
                        rs.getInt("IdTipoContrato"),
                        rs.getString("Email"),
                        rs.getString("Pwd"));
                empleados.add(empleado);
            }
        }catch(Exception e){e.printStackTrace();} 
        return empleados;
    }
}
