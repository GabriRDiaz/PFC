/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.employee.manager;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import static grd.pfc.employee.basic.BasicViewMenuController.stPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Gabriel
 */
public class ManagerViewMenuController implements Initializable{
    @FXML
    private AnchorPane rootPane;

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
    
    public void barSwitch() throws IOException {
        VBox box = FXMLLoader.load(getClass().getResource("/grd/pfc/managerview/managerToolbar.fxml"));
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
    
    public void logout() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stPane=subPane;
    }
}
