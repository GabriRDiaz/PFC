package grd.pfc.admon;

import grd.pfc.dao.ManagerDAO;
import grd.pfc.pojo.Pedido;
import grd.pfc.utils.InvoiceGenerator;
import grd.pfc.utils.Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

public class GenInvoiceController implements Initializable{

    @FXML
    private TableView<Pedido> tablePedidos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
        
        tablePedidos.setOnMouseClicked(event -> {
            if(event.getClickCount()==2) {
                if(tablePedidos.getSelectionModel().getSelectedItem()!=null){
                    try {
                        FileChooser fc = new FileChooser();
                        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo PDF", "*.pdf"));
                        File file = fc.showSaveDialog(tablePedidos.getScene().getWindow());
                        
                        Pedido pedido = tablePedidos.getSelectionModel().getSelectedItem();
                        
                        InvoiceGenerator ig = new InvoiceGenerator(file,pedido);
                        ig.generateInvoice();
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(GenInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                }else{return;}
        });
    }

    private void refreshTable() {
        ManagerDAO dao = new ManagerDAO();
        ObservableList<Pedido> pedidos = FXCollections.observableArrayList();
        pedidos.addAll(dao.getPedidos());
        tablePedidos.setItems(pedidos);
    }
    
    
}
