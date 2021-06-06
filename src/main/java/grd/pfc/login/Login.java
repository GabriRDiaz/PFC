package grd.pfc.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.DAO;
import grd.pfc.singleton.InfoBundle;
import grd.pfc.utils.Utils;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
//        System.out.println("USER="+userId);
//        userId=1; //3-> 2s 
        if(userId>0){
            InfoBundle.getInfoBundle().setIdEmpleado(userId);
            Stage stage = new Stage();
            Parent container;
            Scene scene;
            int profileId = dao.getProfileId(userId);
//            profileId=3;
            switch(profileId){
                case 0:
                    Utils.alertGenerator("ERROR", "Usuario sin perfil", "Su usuario está registrado pero todavía no cuenta con un perfil válido. Contacte con el apartado de administración de su empresa", 4);
                    break;
                case 1:
                    container = new FXMLLoader().load(getClass().getResource("/grd/pfc/basicview/basicviewMenu.fxml"));
                    scene = new Scene(container, 900,550);
                    stage.setTitle("Mozo de almacén");
                    stage.setScene(scene);
                    stage.show();
                    stage = (Stage) userInput.getScene().getWindow();
                    stage.close();
                    break;
                case 2:
                    container = new FXMLLoader().load(getClass().getResource("/grd/pfc/managerview/managerviewMenu.fxml"));
                    scene = new Scene(container, 1170,742);
                    stage.setTitle("Gerente");
                    stage.setScene(scene);
                    stage.show();
                    stage = (Stage) userInput.getScene().getWindow();
                    stage.close();
                    break;
                case 3:
                    container = new FXMLLoader().load(getClass().getResource("/grd/pfc/menu/admon/administracion.fxml"));
                    scene = new Scene(container, 900,550);
                    stage.setTitle("Administrativo");
                    stage.setScene(scene);
                    stage.show();
                    stage = (Stage) userInput.getScene().getWindow();
                    stage.close();
                    break;
                case 4:
                    break;
                default:
                    Utils.alertGenerator("ERROR", "Credenciales incorrectas", "Para cambiar sus credenciales contacte con el apartado de administración de su empresa", 4);
                    return;
            }
            
        }else{
            Utils.alertGenerator("ERROR", "Credenciales incorrectas", "Para cambiar sus credenciales contacte con el apartado de administración de su empresa", 4);
        }
    }
    
    public void close(){
       if(Utils.alertGenerator("AVISO", "", "¿Seguro que desea salir?", 1)){
        System.exit(0);
    }
    }
}
