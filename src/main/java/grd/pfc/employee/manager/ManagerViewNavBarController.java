package grd.pfc.employee.manager;

import com.jfoenix.controls.JFXButton;
import grd.pfc.employee.basic.BasicViewMenuController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class ManagerViewNavBarController implements Initializable{

    @FXML
    private JFXButton regStock;

    @FXML
    private JFXButton addProduct;

    @FXML
    private JFXButton manageProduct;

    @FXML
    private JFXButton addOrder;

    @FXML
    private JFXButton manangeOrder;

    @FXML
    private JFXButton genInvoice;

    @FXML
    private JFXButton viewRequests;
    
    public void addOrder() {

    }

    public void addProduct() {

    }

    public void genInvoice() {

    }

    public void manageProduct() {

    }

    public void regStock() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/grd/pfc/managerview/regStock.fxml"));      
        ManagerViewMenuController.stPane.getChildren().setAll(pane);
    }

    public void unlockResponsablmanangeOrdere() {

    }

    public void viewRequests() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
