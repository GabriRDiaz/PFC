package grd.pfc.employee.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.ManagerDAO;
import grd.pfc.pojo.Cliente;
import grd.pfc.pojo.Estado;
import grd.pfc.pojo.Pais;
import grd.pfc.pojo.Pedido;
import grd.pfc.pojo.Producto;
import grd.pfc.utils.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;

public class ManagerEditPedidoController implements Initializable{
    
    @FXML
    private JFXTextField txtPais;

    @FXML
    private JFXComboBox<String> comboPais;

    @FXML
    private JFXButton butSave;

    @FXML
    private JFXButton butClean;

    @FXML
    private JFXTextField txtTel;

    @FXML
    private JFXComboBox<String> comboEstado;

    @FXML
    private JFXTextField txtDestinatario;

    @FXML
    private JFXTextField txtDireccion;

    @FXML
    private JFXComboBox<String> comboCliente;

    @FXML
    private DatePicker dateSalida;

    @FXML
    private JFXTextField idPedido;
    
    @FXML
    private JFXButton butInfo;

    @FXML
    private TableView<Pedido> listaPedidos;

    private ManagerDAO dao;
    private Pedido pedido;
    
    public void showInfo(){
        if(idPedido.getText().equals("")){
            Utils.alertGenerator("Error", "", "Seleccione un pedido de la tabla", 4);
            return;
        }
            Alert alert = new Alert(Alert.AlertType.NONE, "Pedido: #"+pedido.getId(), ButtonType.OK);
            alert.setTitle("Vista de pedido");

            alert.setHeaderText("Cliente: "+pedido.getCliente()+"\nTeléfono: "+pedido.getTelefono()+"\nDestinatario: "+pedido.getDestinatario()
            +"\nDireccion: "+pedido.getDireccion()+"\nPaís: "+pedido.getPais()+"\nFecha Exp.: "+pedido.getFechaExp()+"\nFecha envío: "
            +pedido.getFechaEnvio()+"\nEstado: "+pedido.getEstado());
            alert.showAndWait();
        
    }
    
    public void clean() {
        idPedido.setText("");
        comboCliente.setValue("");
        comboEstado.setValue("");
        comboPais.setValue("");
        idPedido.setText("");
        txtTel.setText("");
        txtDestinatario.setText("");
        txtPais.setText("");
        txtDireccion.setText("");
        refreshTable();
        dateSalida.setValue(null);
        paisFilter();
        lockFields(true);
    }

    public void paisFilter() {
        ArrayList<Pais> paises = dao.getFilteredPaises(txtPais.getText().toUpperCase());
        if(paises.isEmpty()){return;}
        comboPais.getItems().clear();
        paises.forEach(p->{
            comboPais.getItems().add(p.getPais());
        });
    }
    
    public void save() {
        if(checkFields()){
            Pedido pedidoUpdate = new Pedido(
                Integer.parseInt(idPedido.getText()),
                comboCliente.getValue(),
                null,
                Utils.pickerToSql(dateSalida),
                comboEstado.getValue(),
                txtDestinatario.getText(),
                txtDireccion.getText(),
                txtTel.getText(),
                comboPais.getValue()
            );
            int display = dao.editPedido(pedidoUpdate);
            Utils.alertGenerator("Éxito", "", "Pedido editado con éxito", 2);
            refreshTable();
            clean();
        }
       
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new ManagerDAO();
        refreshTable();
        
        txtPais.addEventHandler(KeyEvent.KEY_TYPED, event -> {
                paisFilter();
        });
        listaPedidos.setOnMouseClicked(event -> {
            if(event.getClickCount()==2) {
                if(listaPedidos.getSelectionModel().getSelectedItem()!=null){
                        pedido = listaPedidos.getSelectionModel().getSelectedItem();
                        comboCliente.setValue(pedido.getCliente());
                        comboEstado.setValue(pedido.getEstado());
                        comboPais.setValue(pedido.getPais());
                        idPedido.setText(""+pedido.getId());
                        dateSalida.setValue(Utils.SqlToPicker(pedido.getFechaEnvio()));
                        txtTel.setText(pedido.getTelefono());
                        txtDestinatario.setText(pedido.getDestinatario());
                        txtDireccion.setText(pedido.getDireccion());
                        lockFields(false);
                    }
                }else{return;}
        });
        getCombos();
        lockFields(true);
    }
    private void getCombos(){
        ArrayList<Cliente> clientes = dao.getClientes();
        clientes.forEach(c->{
            comboCliente.getItems().add(c.getNombre());
        });
        ArrayList<Pais> paises = dao.getFilteredPaises("");
        paises.forEach(p->{
            comboPais.getItems().add(p.getPais());
        });
        ArrayList<Estado> estados = dao.getEstados();
         estados.forEach(e->{
            comboEstado.getItems().add(e.getEstado());
        });
    }
    
    private boolean checkFields(){
       if(txtTel.textProperty().getValue().equals("") || txtDestinatario.textProperty().getValue().equals("") ||txtDireccion.textProperty().getValue().equals("") || dateSalida.getValue()==null || comboCliente.getValue()==null || comboEstado.getValue()==null || comboPais.getValue()==null){
           Utils.alertGenerator("ERROR", "", "Complete todos los campos obligatorios (*)", 4);
            return false;
        }
        return true;
    }

    private void refreshTable() {
        ObservableList<Pedido> pedidos = FXCollections.observableArrayList();
        pedidos.addAll(dao.getPedidos());
        listaPedidos.setItems(pedidos);
    }
    private void lockFields(boolean isLocked){
        if(isLocked){
            comboCliente.setDisable(true);
            comboEstado.setDisable(true);
            comboPais.setDisable(true);
            dateSalida.setDisable(true);
            idPedido.setEditable(false);
            txtTel.setEditable(false);
            txtDestinatario.setEditable(false);
            txtPais.setEditable(false);
            txtDireccion.setEditable(false);
        }else{
            comboCliente.setDisable(false);
            comboEstado.setDisable(false);
            comboPais.setDisable(false);
            dateSalida.setDisable(false);
            txtTel.setEditable(true);
            txtDestinatario.setEditable(true);
            txtPais.setEditable(true);
            txtDireccion.setEditable(true);
    }
    }
}
