package grd.pfc.admon;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class AdministracionController implements Initializable {
@FXML
private JFXButton addEmpleado;

@FXML
private JFXButton editEmpleado;

@FXML
private JFXButton addPerfil;

@FXML
private JFXButton editPerfil;

@FXML
private JFXButton viewGraph;

@FXML
private JFXButton genRep;
@FXML
private ImageView butLogout;

@FXML
private JFXDrawer sidebarBox;

@FXML
private JFXHamburger menuSwitch;


    public void menuSwitch(){
        
    }
    public void logout(){
        System.out.println("Sesión cerrada");
    }
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
    try {
        VBox menuSwitch = new FXMLLoader(getClass().getResource("/grd/pfc/menu/admon/admonToolbar.fxml")).load();
        sidebarBox.setSidePane(menuSwitch);
    } catch (IOException ex) {
        Logger.getLogger(AdministracionController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    

    public void addEmpleado(ActionEvent event) {

    }

    
    public void addPerfil(ActionEvent event) {

    }

    
    public void editEmpleado(ActionEvent event) {

    }

    
    public void editPerfil(ActionEvent event) {

    }

    public void genRep(ActionEvent event) {

    }

    
    public void viewGraph(ActionEvent event) {

    }
}
