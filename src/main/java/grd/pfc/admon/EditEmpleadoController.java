/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.admon;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.AdministracionDAO;
import grd.pfc.pojo.Empleado;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Gabriel
 */
public class EditEmpleadoController implements Initializable {

    @FXML
    private JFXButton butSave;
    @FXML
    private JFXButton butClean;
    @FXML
    private TableView<Empleado> tableempleados;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private DatePicker dateContrato;
    @FXML
    private JFXTextField txtSalario;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXTextField txtApellidos;
    @FXML
    private DatePicker dateSalida;
    @FXML
    private JFXComboBox<?> comboContrato;
    @FXML
    private JFXPasswordField txtPwd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
    }    

    @FXML
    private void save(ActionEvent event) {
    }

    @FXML
    private void clean(ActionEvent event) {
    }
    
    private void refreshTable() {
        AdministracionDAO adminDao = new AdministracionDAO();
        ObservableList<Empleado> empleados = FXCollections.observableArrayList();
        List<Empleado> foundEmpleados = adminDao.getEmpleados();
        empleados.addAll(foundEmpleados);
        tableempleados.setItems(empleados);
        
        tableempleados.setOnMouseClicked( event -> {
            if(event.getClickCount()==2) {
               Empleado empleado = tableempleados.getSelectionModel().getSelectedItem();
               txtNombre.setText(empleado.getNombre());
               txtApellidos.setText(empleado.getApellidos());
               txtUsuario.setText(empleado.getMail());
               txtSalario.setText(""+empleado.getSalario());
               
          }
   });
       
    }
}
