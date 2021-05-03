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
import grd.pfc.dao.AdministracionDAO;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
        if(createAlert("Aviso","¿Desea crear un nuevo empleado?")){
            int idContrato = adminDao.getContratoId(comboContrato.getValue());
            java.util.Date contrato = java.util.Date.from(dateContrato.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlContrato = new java.sql.Date(contrato.getTime());
            java.sql.Date sqlSalida=null;
            if(dateSalida!=null){
                java.util.Date salida = java.util.Date.from(dateSalida.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                sqlSalida = new java.sql.Date(salida.getTime());  
            }
            adminDao.addEmpleado(txtNombre.getText(), txtApellidos.getText(), sqlContrato, sqlSalida, Double.parseDouble(txtSalario.getText()), idContrato, txtUsuario.getText(), txtPwd.getText());
            clean();
        }
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
    
    private boolean createAlert(String title,String txt){
    ButtonType yes = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.OK_DONE);;
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, txt, yes,no);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.showAndWait();
    if(alert.getResult()==yes){return true;}else{return false;}
}
}    
    
