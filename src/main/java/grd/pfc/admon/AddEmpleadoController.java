package grd.pfc.admon;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.AdministracionDAO;
import grd.pfc.pojo.Empleado;
import grd.pfc.utils.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author Gabriel
 */
public class AddEmpleadoController implements Initializable {

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
    @FXML
    private JFXButton butSave;
    @FXML
    private JFXButton butClean;
    private AdministracionDAO adminDao;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adminDao= new AdministracionDAO();
        ArrayList<String> contratos = adminDao.getContratos();
        contratos.forEach(c->{
        comboContrato.getItems().add(c);
        });
        }
    
    public void save(){
    if(checkFields()){
        if(Utils.alertGenerator("Aviso","","¿Desea crear un nuevo empleado?",1)){
            int idContrato = adminDao.getContratoId(comboContrato.getValue());
            java.sql.Date sqlContrato = Utils.pickerToSql(dateContrato);
            java.sql.Date sqlSalida=null;
            if(dateSalida.getValue()!=null){
                sqlSalida = Utils.pickerToSql(dateSalida);
            }
            adminDao.addEmpleado(new Empleado(txtNombre.getText(), txtApellidos.getText(), sqlContrato, sqlSalida, Double.parseDouble(txtSalario.getText()), idContrato, txtUsuario.getText(), txtPwd.getText()));
            Utils.alertGenerator("OK", "", "El empleado se ha añadido correctamente", 0);
            clean();
        }
    }else{Utils.alertGenerator("ERROR", "", "Rellene todos los campos obligatorios", 4);}
    }
    public void clean(){
        txtNombre.setText("");
        dateContrato.setValue(null);
        txtSalario.setText("");
        txtUsuario.setText("");
        txtApellidos.setText("");
        dateSalida.setValue(null);
        txtPwd.setText("");
        comboContrato.getSelectionModel().clearSelection();
    }
    private boolean checkFields(){
        if(txtNombre.getText().equals("") || txtApellidos.getText().equals("") || dateContrato.getValue()==null || txtSalario.getText().equals("") || comboContrato.getValue()==null || txtUsuario.getText().equals("") || txtPwd.getText().equals("")){
            return false;
        }else{return true;}
    }
}    
    
