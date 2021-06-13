package grd.pfc.admon;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.AdministracionDAO;
import grd.pfc.pojo.Empleado;
import grd.pfc.pojo.Seccion;
import grd.pfc.utils.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class SetSeccionesController implements Initializable{

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
    private ScrollPane scrollCheckList;
    private ArrayList<JFXCheckBox> checkList;
    public void clear() {
        empleadoId.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        checkList.forEach(c->{c.setDisable(true); c.setSelected(false);});
    }
    
    public void save() {
        if(!empleadoId.getText().equals("")){
            if(Utils.alertGenerator("Aviso", "Empleado: "+txtNombre.getText()+" "+txtApellidos.getText(), "¿Seguro que desea guardar los cambios?", 1)){
                AdministracionDAO adminDao = new AdministracionDAO();
                adminDao.delSeccionesEmpleados(Integer.parseInt(empleadoId.getText()));
                checkList.forEach(c->{
                    if(c.isSelected()){
                        adminDao.updSeccionesEmpleados(c.getId(),Integer.parseInt(empleadoId.getText()));
                    }
                });
                refreshTable();
                Utils.alertGenerator("OK", "", "Las secciones del empleado se han modificado correctamente", 0);
                clear();
            } else{Utils.alertGenerator("OK", "", "Las secciones del empleado no han sido modificadas", 0);}
        }else{Utils.alertGenerator("ERROR", "", "Seleccione un empleado de la tabla", 4);}
    }
    private void refreshTable(){
        AdministracionDAO adminDao = new AdministracionDAO();
        ObservableList<Empleado> empleados = FXCollections.observableArrayList();
        List<Empleado> foundEmpleados = adminDao.getEmpleadosSecciones();
        empleados.addAll(foundEmpleados);
        tableempleados.setItems(empleados);
        tableempleados.setOnMouseClicked(event -> {
            if(event.getClickCount()==2) {
               if(tableempleados.getSelectionModel().getSelectedItem()==null){return;}
               Empleado empleado = tableempleados.getSelectionModel().getSelectedItem();
               empleadoId.setText(""+empleado.getId());
               txtNombre.setText(empleado.getNombre());
               txtApellidos.setText(empleado.getApellidos());
               ArrayList<String> secciones = empleado.getSecciones();
               checkList.forEach(c->{
                   c.setDisable(false);
                   if(secciones.contains(c.getId())){
                       c.setSelected(true);
                   }else{c.setSelected(false);}
               });
          }
   });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AdministracionDAO adminDao= new AdministracionDAO();
        ArrayList<Seccion> secciones = adminDao.getSecciones();
        VBox checkListBox = new VBox();
        checkList = new ArrayList<JFXCheckBox>();
        secciones.forEach(s->{
            JFXCheckBox ch = new JFXCheckBox(s.getNombre());
            ch.setId(s.getNombre());
            ch.setDisable(true);
            ch.setStyle("-jfx-checked-color: #4059a9; -jfx-unchecked-color: #4059a9; -fx-font-size: 16px;");
            checkList.add(ch);
        });
        checkList.forEach(c->{checkListBox.getChildren().add(c);});
        scrollCheckList.setContent(checkListBox);
        refreshTable();
    }
}
