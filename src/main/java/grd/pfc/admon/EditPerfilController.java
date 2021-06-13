package grd.pfc.admon;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.AdministracionDAO;
import grd.pfc.pojo.Empleado;
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

public class EditPerfilController implements Initializable{

    @FXML
    private JFXButton butSave;

    @FXML
    private JFXButton butClear;

    @FXML
    private TableView<Empleado> tableempleados;

    @FXML
    private JFXTextField empleadoId;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtApellidos;

    @FXML
    private JFXComboBox<String> comboPerfiles;

    
    public void clear() {
        empleadoId.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        comboPerfiles.setValue("");
        comboPerfiles.setDisable(true);
    }
    
    public void save() {
        if(!empleadoId.getText().equals("") && !comboPerfiles.getValue().equals("")){
            if(Utils.alertGenerator("Aviso", "Empleado: "+txtNombre.getText()+" "+txtApellidos.getText(), "¿Seguro que desea guardar los cambios?", 1)){
                AdministracionDAO adminDao = new AdministracionDAO();
                int idPerfil = adminDao.getPerfilId(comboPerfiles.getValue());
                adminDao.editPerfilEmpleado(idPerfil, Integer.parseInt(empleadoId.getText()));
                refreshTable();
                Utils.alertGenerator("OK", "", "El perfil del empleado se ha modificado correctamente", 0);
                clear();
            } else{Utils.alertGenerator("OK", "", "El empleado no ha sido modificado", 0);}
        }else{Utils.alertGenerator("ERROR", "", "Seleccione un empleado de la tabla y un perfil", 4);}
    }
        
    private void refreshTable() {
        AdministracionDAO adminDao = new AdministracionDAO();
        ObservableList<Empleado> empleados = FXCollections.observableArrayList();
        List<Empleado> foundEmpleados = adminDao.getEmpleadosPerfil();
        empleados.addAll(foundEmpleados);
        tableempleados.setItems(empleados);
        tableempleados.setOnMouseClicked( event -> {
            if(event.getClickCount()==2) {
                if(tableempleados.getSelectionModel().getSelectedItem()==null){return;}
               comboPerfiles.setDisable(false);
               Empleado empleado = tableempleados.getSelectionModel().getSelectedItem();
               empleadoId.setText(""+empleado.getId());
               txtNombre.setText(empleado.getNombre());
               txtApellidos.setText(empleado.getApellidos());
               comboPerfiles.setValue(empleado.getPerfil());
          }
   });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AdministracionDAO adminDao= new AdministracionDAO();
        ArrayList<String> perfiles = adminDao.getPerfiles();
        perfiles.forEach(c->{
        comboPerfiles.getItems().add(c);
        });
        comboPerfiles.setDisable(true);
        refreshTable();
    }
}