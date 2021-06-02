package grd.pfc.employee.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.BasicViewDAO;
import grd.pfc.pojo.LineaPedido;
import grd.pfc.pojo.Producto;
import grd.pfc.utils.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ManagerAddPedidoController implements Initializable{

    @FXML
    private TableView<Producto> tableProductos;

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
    private JFXTextField txtProducto;

    @FXML
    private ImageView minus;

    @FXML
    private JFXTextField productQ;

    @FXML
    private ImageView plus;

    @FXML
    private ScrollPane scrollProductos;

    @FXML
    private JFXButton butAdd;

    @FXML
    private JFXButton butRemove;

    @FXML
    private JFXTextField idProd;
    
    @FXML
    private TableView<LineaPedido> tablePedido;
    
    private ArrayList<Text> productosPedido;
    private int maxQ=-1; //Product stock
    
    public void addQ() {
        if((Integer.parseInt(productQ.getText())+1)>maxQ){Utils.alertGenerator("Error", "", "El pedido no puede tener más cantidad que el stock del disponible", 4);}else{
            productQ.setText(""+(Integer.parseInt(productQ.getText())+1));
        }
    }

    public void clean() {

    }

    public void paisClean() {

    }

    public void paisFilter() {

    }
    
    public void save() {

    }
    
    public void substractQ() {
        if(Integer.parseInt(productQ.getText())==0){
            Utils.alertGenerator("Error", "", "El pedido ha de tener al menos una unidad del producto", 4);
            productQ.setText(""+(Integer.parseInt(productQ.getText())));
        }
        else{
            productQ.setText(""+(Integer.parseInt(productQ.getText())-1));
        }
    }
    
    public void addProduct(){
        if(!idProd.equals("")){
            
            LineaPedido lp = new LineaPedido(Integer.parseInt(idProd.getText()),txtProducto.getText(),Integer.parseInt(productQ.getText()));
            
            ObservableList<Producto> tp = tableProductos.getItems();
            for(int i=0;i<tp.size();i++){
                if(tp.get(i).getId()==lp.getId()){
                    Producto p = tp.get(i);
                    p.setStock(p.getStock()-lp.getCantidad());
                    tp.set(i,p);
                }
            }
            
            ObservableList<LineaPedido> tbLp = tablePedido.getItems();
            if(tbLp.isEmpty()){
                tablePedido.getItems().add(lp);
            }else{
                boolean exists=false;
            for(int i=0;i<tbLp.size();i++){
                if(tbLp.get(i).getId()==lp.getId()){
                    lp.setCantidad(tbLp.get(i).getCantidad()+lp.getCantidad());
                    tbLp.set(i,lp);
                    exists=true;
                }
            }if(!exists){tablePedido.getItems().add(lp);}
            }
            
            
            
            idProd.setText("");
            txtProducto.setText("");
            productQ.setText("");
        }else{Utils.alertGenerator("Error", "", "Seleccione algún producto para añadir al pedido", 4);}
    }

    public void removeProduct(){
        
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
                    Producto producto = tableProductos.getSelectionModel().getSelectedItem();
                    if(producto.getStock()==0){
                        Utils.alertGenerator("Error", "", "No puede trabajar con productos sin stock", 4);
                    }else {
                        idProd.setText(""+producto.getId());
                        txtProducto.setText(producto.getNombre());
                        productQ.setText(""+producto.getStock());
                        maxQ = producto.getStock();
                        plus.setDisable(false);
                        minus.setDisable(false);
                    }
                }else{return;}                  
            }
        });
         
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
        plus.setDisable(true);
        minus.setDisable(true);
    }
}
