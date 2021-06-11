/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.employee.basic;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Gabriel
 */
public class BasicViewNavBarController implements Initializable {

    @FXML
    private JFXButton viewStock;
    @FXML
    private JFXButton regStock;
    @FXML
    private JFXButton requestProd;

    public void viewStock() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/grd/pfc/basicview/viewStock.fxml"));    
        BasicViewMenuController.stPane.getChildren().setAll(pane);
    }

    public void regStock() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/grd/pfc/basicview/regStock.fxml"));    
        BasicViewMenuController.stPane.getChildren().setAll(pane);
    }

    public void requestProd() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/grd/pfc/basicview/requestStock.fxml"));    
        BasicViewMenuController.stPane.getChildren().setAll(pane);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
