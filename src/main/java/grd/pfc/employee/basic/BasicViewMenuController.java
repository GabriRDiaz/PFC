package grd.pfc.employee.basic;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BasicViewMenuController implements Initializable{
    

@FXML
private ImageView butLogout;

@FXML
private JFXDrawer sidebarBox;

@FXML
private JFXHamburger hamburger;

@FXML
private AnchorPane subPane;

public static AnchorPane stPane;
private boolean isOpened;
private int transitionRate=1;
private HamburgerBasicCloseTransition transition;
    @FXML
    private AnchorPane rootPane;
//TODO: Refactor barSwitch in Utils
    @FXML
    public void barSwitch() throws IOException{
        VBox box = FXMLLoader.load(getClass().getResource("/grd/pfc/basicview/basicToolbar.fxml"));
        sidebarBox.setSidePane(box); 
        sidebarBox.setMinWidth(0);
        if(transition==null){
           transition = new HamburgerBasicCloseTransition(hamburger);
        }
        transition.setRate(transitionRate);
        transition.play();
        if(isOpened){
            sidebarBox.setMinWidth(0);
            sidebarBox.close();
        }else{
            sidebarBox.setMinWidth(150);
            sidebarBox.open();
        }
        isOpened=!isOpened;
        transitionRate*=-1;
    }

    @FXML
    public void logout() throws IOException{
        Stage stage = new Stage();
        Parent container = new FXMLLoader().load(getClass().getResource("/grd/pfc/login/login.fxml"));
        Scene scene = new Scene(container, 900,550);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        stage = (Stage) butLogout.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stPane=subPane;
    }

}
