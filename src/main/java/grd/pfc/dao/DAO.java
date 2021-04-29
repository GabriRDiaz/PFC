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
    public DAO(){}
    
    public void testDb() throws ClassNotFoundException, SQLException{
        Connection conn=getConnetion();
        if (conn != null) {
            System.out.println("Connected");
        }
    }
    
    public boolean login(String usr,String pwd) throws SQLException{
        Connection conn=getConnetion();
        if(conn!=null){
            CallableStatement cstmt = conn.prepareCall(
                "{call [PFC].[dbo].[pLogin](?,?,?)}");
            cstmt.setString(1,usr);
            cstmt.setString(2,pwd);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.execute();
            if(cstmt.getString(3).equals("y")){
                return true;
            }else{return false;}
    }
        return false;
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