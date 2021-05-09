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
//        
//        tableempleados.setOnMouseClicked( event -> {
//            if(event.getClickCount()==2) {
//               editOn=true;
//               Empleado empleado = tableempleados.getSelectionModel().getSelectedItem();
//               empleadoId.setText(""+empleado.getId());
//               txtNombre.setText(empleado.getNombre());
//               txtApellidos.setText(empleado.getApellidos());
//               txtUsuario.setText(empleado.getMail());
//               txtSalario.setText(""+empleado.getSalario());
//               dateContrato.setValue(Utils.SqlToPicker(empleado.getContrato()));
//               if(empleado.getSalida()!=null){
//                    dateSalida.setValue(Utils.SqlToPicker(empleado.getSalida()));
//               }
//               comboContrato.setValue(adminDao.getTipoContrato(empleado.getIdTipoContrato()));
//               txtPwd.setText("");
//          }
//   });
       
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
    }    
    
    
}
