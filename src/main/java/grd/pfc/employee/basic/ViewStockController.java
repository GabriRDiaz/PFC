/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.employee.basic;

import com.jfoenix.controls.JFXComboBox;
import grd.pfc.dao.AdministracionDAO;
import grd.pfc.dao.BasicViewDAO;
import grd.pfc.pojo.Empleado;
import grd.pfc.pojo.IVA;
import grd.pfc.pojo.Marca;
import grd.pfc.pojo.Producto;
import grd.pfc.pojo.Seccion;
import grd.pfc.singleton.InfoBundle;
import grd.pfc.utils.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Gabriel
 */
public class ViewStockController implements Initializable {
   
    @FXML
    private TableView<Producto> tableProductos;
    
    @FXML
    private JFXComboBox<String> comboSeccion;

    @FXML
    private JFXComboBox<String> comboMarca;

    @FXML
    private JFXComboBox<String> comboIVA;
    
    @FXML
    private ImageView filter;
    
    @FXML
    private ImageView clean;
    
    public void filter(){
        if(!comboSeccion.getSelectionModel().isEmpty()||!comboMarca.getSelectionModel().isEmpty()||!comboIVA.getSelectionModel().isEmpty()){
            String filter="";
            if(!comboSeccion.getSelectionModel().isEmpty()){
               filter = filter.concat(" AND s.Nombre='"+comboSeccion.getValue()+"'");
            }
            if(!comboMarca.getSelectionModel().isEmpty()){
                filter = filter.concat(" AND m.Marca='"+comboMarca.getValue()+"'");
            }
            if(!comboIVA.getSelectionModel().isEmpty()){
                filter = filter.concat(" AND i.iva='"+comboIVA.getValue()+"'");
            }
            refreshTable(filter);
        }else{Utils.alertGenerator("ERROR", "", "Escoja alguna opción de filtrado", 4);}
        
    }
    
    public void cleanCombos(){
        comboSeccion.getSelectionModel().clearSelection();
        comboMarca.getSelectionModel().clearSelection();
        comboIVA.getSelectionModel().clearSelection();
        refreshTable("");
    }
    
    private void refreshTable(String filter) {
        BasicViewDAO viewDao = new BasicViewDAO();
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        List<Producto> foundProductos = viewDao.getProductos(filter);
        productos.addAll(foundProductos);
        tableProductos.setItems(productos);
        
        //TODO: Add prices alert in table

        tableProductos.setOnMouseClicked( event -> {
            if(event.getClickCount()==2) {
                if(tableProductos.getSelectionModel().getSelectedItem()!=null){
                    Producto producto = tableProductos.getSelectionModel().getSelectedItem();
                    double precioIVA = producto.getPrecioSinIVA() + producto.getPrecioSinIVA()*producto.getIva()/100;
                    double precioDesc = precioIVA - (producto.getDescuento()*precioIVA)/100;
                    System.out.println("Producto sin IVA: "+producto.getPrecioSinIVA());
                    System.out.println("Producto con IVA: "+precioIVA);
                    System.out.println("Producto con descuento: "+precioDesc);
                }else{return;}
            }
        });
       
    }
    private void getCombos(){
        BasicViewDAO viewDao= new BasicViewDAO();
        ArrayList<Seccion> secciones = viewDao.getSecciones(InfoBundle.getInfoBundle().getIdEmpleado()); 
        secciones.forEach(s->{
            comboSeccion.getItems().add(s.getNombre());
        });
        ArrayList<Marca> marcas = viewDao.getMarcas();
        marcas.forEach(m->{
            comboMarca.getItems().add(m.getMarca());
        });
        ArrayList<IVA> ivas = viewDao.getIvas();
            ivas.forEach(i->{
              comboIVA.getItems().add(i.getIVA());
            });
        }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getCombos();
        refreshTable("");
    }    
    

}
