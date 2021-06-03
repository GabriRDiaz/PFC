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
    private JFXButton manageOrder;

    @FXML
    private JFXButton genInvoice;

    @FXML
    private JFXButton viewRequests;
    
    public void addOrder() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/grd/pfc/managerview/addOrder.fxml"));      
        ManagerViewMenuController.stPane.getChildren().setAll(pane);
    }

    public void addProduct() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/grd/pfc/managerview/addProduct.fxml"));      
        ManagerViewMenuController.stPane.getChildren().setAll(pane);
    }

    public void genInvoice() {

    }

    public void manageProduct() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/grd/pfc/managerview/editProducts.fxml"));      
        ManagerViewMenuController.stPane.getChildren().setAll(pane);
    }

    public void regStock() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/grd/pfc/managerview/regStock.fxml"));      
        ManagerViewMenuController.stPane.getChildren().setAll(pane);
    }

    public void manageOrders() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/grd/pfc/managerview/editOrder.fxml"));      
        ManagerViewMenuController.stPane.getChildren().setAll(pane);
    }

    public void manageRequests() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/grd/pfc/managerview/manageRequests.fxml"));      
        ManagerViewMenuController.stPane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }


}
