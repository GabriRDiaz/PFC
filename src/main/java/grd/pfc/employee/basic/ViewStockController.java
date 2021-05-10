/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.employee.basic;

import grd.pfc.dao.AdministracionDAO;
import grd.pfc.dao.BasicViewDAO;
import grd.pfc.pojo.Empleado;
import grd.pfc.pojo.Producto;
import grd.pfc.utils.Utils;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Gabriel
 */
public class ViewStockController implements Initializable {

    @FXML
    private TableView<Producto> tableProductos;

        
    private void refreshTable() {
        BasicViewDAO viewDao = new BasicViewDAO();
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        List<Producto> foundProductos = viewDao.getProductos();
        productos.addAll(foundProductos);
        tableProductos.setItems(productos);
        
        tableProductos.setOnMouseClicked( event -> {
            if(event.getClickCount()==2) {
                Producto producto = tableProductos.getSelectionModel().getSelectedItem();
                double precioIVA = producto.getPrecioSinIVA() + producto.getPrecioSinIVA()*producto.getIva()/100;
                double precioDesc = precioIVA - (producto.getDescuento()*precioIVA)/100;
                System.out.println("Producto sin IVA: "+producto.getPrecioSinIVA());
                System.out.println("Producto con IVA: "+precioIVA);
                System.out.println("Producto con descuento: "+precioDesc);
            }
        });
       
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
    }    
    
    
}
