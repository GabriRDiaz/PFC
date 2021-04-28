package grd.pfc.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class Login {

    @FXML
    private JFXTextField userInput;

    @FXML
    private JFXTextField pwdInput;

    @FXML
    private JFXButton butLogin;

    public void login(){
       if(userInput.getText().equals("root") && pwdInput.getText().equals("abc123.")) {
           System.out.println("Entra");
       }else{
           System.out.println("No entra");
       }
    }
    
    public void close(){
        System.exit(0);
    }
}
