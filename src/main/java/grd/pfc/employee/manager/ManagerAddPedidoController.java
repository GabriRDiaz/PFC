package grd.pfc.employee.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.pojo.Producto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class ManagerAddPedidoController implements Initializable{

    @FXML
    private TableView<Producto> tableProductos;

    @FXML
    private TableView<Producto> tableProductosIn;

    @FXML
    private ImageView clienteFilter;

    @FXML
    private JFXComboBox<String> comboCliente;

    @FXML
    private DatePicker dateEntrada;

    @FXML
    private JFXTextField txtDestinatario;

    @FXML
    private JFXTextField txtDireccion;

    @FXML
    private JFXTextField txtTel;

    @FXML
    private JFXComboBox<String> comboEstado;

    @FXML
    private ImageView clienteClean;

    @FXML
    private JFXTextField txtCliente;

    @FXML
    private JFXTextField txtPais;

    @FXML
    private ImageView paisFilter;

    @FXML
    private ImageView paisClean;

    @FXML
    private JFXComboBox<String> comboPais;

    @FXML
    private JFXButton butSave;

    @FXML
    private JFXButton butClean;

    public void clean() {

    }

    public void clienteClien() {

    }

    public void clienteFilter() {

    }

    public void paisClean() {

    }

    public void paisFilter() {

    }

    public void save() {

    }
    
    private void refreshTable() {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
    }
}

