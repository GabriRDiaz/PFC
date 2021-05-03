/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.admon;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Gabriel
 */
public class NavBarController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void addEmpleado() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/grd/pfc/menu/admon/AddEmpleado.fxml"));    
        AdministracionController.stPane.getChildren().setAll(pane);
    }

    @FXML
    private void editEmpleado(ActionEvent event) {
    }

    @FXML
    private void addPerfil(ActionEvent event) {
    }

    @FXML
    private void editPerfil(ActionEvent event) {
    }

    @FXML
    private void viewGraph(ActionEvent event) {
    }

    @FXML
    private void genRep(ActionEvent event) {
    }
    
}
