package grd.pfc.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {
    String connectionUrl = "jdbc:sqlserver://GABRIRDIAZ\\GRDSQL;user=sa;password=abc123.";
    String checkLogin = "DECLARE @responseMessage NVARCHAR(250)\n" +
                        "EXEC PFC.pLogin\n" +
                        "	@pEmail=?,\n" +
                        "	@pPwd=?,\n" +
                        "	@responseMessage=@responseMessage OUTPUT\n" +
                        "SELECT @responseMessage as 'Msg'";
    String getProfileId = "SELECT IdPerfil FROM [PFC].[dbo].[Empleados] WHERE Id=?";
    public DAO(){}
    
    public int getProfileId(int idUsr){
        int id=-1;
        try(Connection connectDB = DriverManager.getConnection(connectionUrl)){
            PreparedStatement ps = connectDB.prepareStatement(getProfileId);
            ps.setInt(1,idUsr);
            ResultSet rs = ps.executeQuery();
            rs.next();
            id=rs.getInt("IdPerfil");
        }catch (SQLException ex) {ex.printStackTrace();}
        return id;
    }
    
    public void testDb() throws ClassNotFoundException, SQLException{
        Connection conn=getConnetion();
        if (conn != null) {
            System.out.println("Connected");
        }
    }
    
    public int login(String usr,String pwd) throws SQLException{
        Connection conn=getConnetion();
        if(conn!=null){
            CallableStatement cstmt = conn.prepareCall(
                "{?=call [PFC].[dbo].[pLogin](?,?)}");
            cstmt.setString(2,usr);
            cstmt.setString(3,pwd);
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.execute();
            return cstmt.getInt(1);
    }
        return -1;
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