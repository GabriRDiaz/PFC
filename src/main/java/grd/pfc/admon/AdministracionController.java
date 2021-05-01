package grd.pfc.admon;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AdministracionController implements Initializable{
    

@FXML
private ImageView butLogout;

@FXML
private JFXDrawer sidebarBox;

@FXML
private JFXHamburger hamburger;

@FXML
public AnchorPane rootPane;

private boolean isOpened;
private int transitionRate=1;
private HamburgerBasicCloseTransition transition;

    @FXML
    public void barSwitch() throws IOException{
        VBox box = FXMLLoader.load(getClass().getResource("/grd/pfc/menu/admon/admonToolbar.fxml"));
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
    public void logout(){
        System.out.println("Sesión cerrada");
    }
    
    //This is the method where I try to load my FXML into my AnchorPane
    public void addEmpleado() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/grd/pfc/menu/admon/addEmpleado.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    
    public void addPerfil() {

    }

    
    public void editEmpleado() {

    }

    
    public void editPerfil() {

    }

    public void genRep() {

    }

    
    public void viewGraph() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

}
