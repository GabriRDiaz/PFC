package grd.pfc.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.DAO;
import java.sql.SQLException;
import javafx.fxml.FXML;

public class Login {

    @FXML
    private JFXTextField userInput;

    @FXML
    private JFXPasswordField pwdInput;

    @FXML
    private JFXButton butLogin;

    public void login() throws ClassNotFoundException, SQLException{
        DAO dao = new DAO();
        if(dao.login(userInput.getText(), pwdInput.getText())){
            System.out.println("Entra");
        }else{
            System.out.println("Nope");
        }
    }
    
    public void close(){
        System.exit(0);
    }
}
