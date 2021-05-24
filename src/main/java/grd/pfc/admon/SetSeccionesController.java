package grd.pfc.admon;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.AdministracionDAO;
import grd.pfc.pojo.Empleado;
import grd.pfc.pojo.Seccion;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
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

    }
    
    public void save() {
        checkList.forEach(c->{
            if(c.isSelected()){
                System.out.println("Sección "+c.getId()+" Marcado");
            }else{System.out.println("Sección "+c.getId()+" No marcado");}
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
            ch.setId(""+s.getId());
            ch.setStyle("-jfx-checked-color: #4059a9; -jfx-unchecked-color: #4059a9; -fx-font: Arial; -fx-font-size: 16px;");
            checkList.add(ch); 
        });
        checkList.forEach(c->{checkListBox.getChildren().add(c);});
        scrollCheckList.setContent(checkListBox);
    }
}
