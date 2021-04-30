package grd.pfc.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.DAO;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login {

    @FXML
    private JFXTextField userInput;

    @FXML
    private JFXPasswordField pwdInput;

    @FXML
    private JFXButton butLogin;

    public void login() throws ClassNotFoundException, SQLException, IOException{
        DAO dao = new DAO();
        int userId=dao.login(userInput.getText(), pwdInput.getText());
        System.out.println("USER="+userId);
        if(userId>0){
            Stage stage = new Stage();
            Parent container = new FXMLLoader().load(getClass().getResource("/grd/pfc/menu/admon/administracion.fxml"));
            Scene scene = new Scene(container, 900,550);
            stage.setTitle("Admon");
            stage.setScene(scene);
            stage.show();
        }else{
            System.out.println("Nope");
        }
    }
    
    public void close(){
        System.exit(0);
    }
}
