package grd.pfc.employee.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.BasicViewDAO;
import grd.pfc.pojo.PedidoProducto;
import grd.pfc.pojo.Producto;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class ManagerAddPedidoController implements Initializable{

    @FXML
    private TableView<Producto> tableProductos;

    @FXML
    private TableView<PedidoProducto> tableProductosIn;

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
    private Producto producto;
    private PedidoProducto productoPedido;
    private ObservableList<PedidoProducto>data = FXCollections.observableArrayList();
    public void clean() {

    }

    public void clienteClean() {

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
        BasicViewDAO viewDao = new BasicViewDAO();
        
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        List<Producto> foundProductos = viewDao.getProductos("");
        productos.addAll(foundProductos);
        tableProductos.setItems(productos);
        
            tableProductos.setOnMouseClicked(event -> {
            if(event.getClickCount()==2) {
                if(tableProductos.getSelectionModel().getSelectedItem()!=null){
                    producto = tableProductos.getSelectionModel().getSelectedItem();
                    tableProductos.getSelectionModel().getSelectedItem().setStock(tableProductos.getSelectionModel().getSelectedItem().getStock()-1);
                    productoPedido = new PedidoProducto(producto,1);
                    data.add(productoPedido);
                    tableProductosIn.setItems(data);
                }else{return;}                  
            }
        });
         tableProductosIn.setOnMouseClicked(event -> {
             if(event.getClickCount()==2) {
                if(tableProductos.getSelectionModel().getSelectedItem()!=null){
                    data.forEach(d->{System.out.println(d.getNombre());});
                    data.remove(tableProductos.getSelectionModel().getSelectedItem());
                    System.out.println("---");
                    data.forEach(d->{System.out.println(d.getNombre());});
//                    data.remove(tableProductos.getSelectionModel().getSelectedItem());
//                    tableProductosIn.getItems().clear();
//                    tableProductosIn.setItems(data);
                }else{return;}}
         });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
    }
}

