/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.employee.manager;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Gabriel
 */
public class ManagerViewNavBarController implements Initializable {

    @FXML
    private JFXButton viewStock;
    @FXML
    private JFXButton regStock;
    @FXML
    private JFXButton buyProduct;
    @FXML
    private JFXButton sellProduct;
    @FXML
    private JFXButton unlockResponsable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    public void viewStock() {
        System.out.println("View");
    }

    
    public void regStock() {
        System.out.println("reg");
    }

    
    public void buyProduct() {
        System.out.println("buy");
    }

    
    public void sellProduct() {
        System.out.println("Sell");
    }

    
    public void unlockResponsable() {
        System.out.println("respon");
    }
    
}
