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
import grd.pfc.utils.Utils;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
    private JFXTextField empleadoId;
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
    private JFXComboBox<String> comboContrato;
    @FXML
    private JFXPasswordField txtPwd;
    private boolean editOn=false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AdministracionDAO adminDao= new AdministracionDAO();
        ArrayList<String> contratos = adminDao.getContratos();
        contratos.forEach(c->{
        comboContrato.getItems().add(c);
        });
        refreshTable();
    }
    //TODO UpdPwd
    public void save() {
        if(editOn){
            if(Utils.alertGenerator("Aviso", "Empleado: "+txtNombre.getText()+" "+txtApellidos.getText(), "¿Seguro que desea guardar los cambios?", 1)){
                AdministracionDAO adminDao = new AdministracionDAO();
                Empleado empleado = new Empleado(
                    Integer.parseInt(empleadoId.getText()),
                    txtNombre.getText(),
                    txtApellidos.getText(),
                    Utils.pickerToSql(dateContrato),
                    Utils.pickerToSql(dateSalida),
                    Double.parseDouble(txtSalario.getText()),
                    adminDao.getContratoId(comboContrato.getValue()),
                    txtUsuario.getText(),
                    txtPwd.getText()
                );
            adminDao.editEmpleado(empleado);
            Utils.alertGenerator("OK", "", "El empleado se ha modificado correctamente", 0);
            }else{Utils.alertGenerator("AVISO", "", "El empleado no se ha modificado", 0);}
        }else{
            Utils.alertGenerator("ERROR", "", "Seleccione un empleado de la tabla para editarlo", 3);
        }
        refreshTable();
        clean();
        editOn=false;
    }
    public void delete(){
        
    }
    private void clean() {
        txtNombre.setText("");
        dateContrato.setValue(null);
        txtSalario.setText("");
        txtUsuario.setText("");
        txtApellidos.setText("");
        dateSalida.setValue(null);
        txtPwd.setText("");
        comboContrato.getSelectionModel().clearSelection();
    }
    
    private void refreshTable() {
        AdministracionDAO adminDao = new AdministracionDAO();
        ObservableList<Empleado> empleados = FXCollections.observableArrayList();
        List<Empleado> foundEmpleados = adminDao.getEmpleados();
        empleados.addAll(foundEmpleados);
        tableempleados.setItems(empleados);
        
        tableempleados.setOnMouseClicked( event -> {
            if(event.getClickCount()==2) {
               editOn=true;
               Empleado empleado = tableempleados.getSelectionModel().getSelectedItem();
               empleadoId.setText(""+empleado.getId());
               txtNombre.setText(empleado.getNombre());
               txtApellidos.setText(empleado.getApellidos());
               txtUsuario.setText(empleado.getMail());
               txtSalario.setText(""+empleado.getSalario());
               dateContrato.setValue(Utils.SqlToPicker(empleado.getContrato()));
               if(empleado.getSalida()!=null){
                    dateSalida.setValue(Utils.SqlToPicker(empleado.getSalida()));
               }
               comboContrato.setValue(adminDao.getTipoContrato(empleado.getIdTipoContrato()));
               txtPwd.setText("");
          }
   });
       
    }
}
