package grd.pfc.employee.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.AdministracionDAO;
import grd.pfc.dao.BasicViewDAO;
import grd.pfc.dao.ManagerDAO;
import grd.pfc.pojo.Cliente;
import grd.pfc.pojo.Estado;
import grd.pfc.pojo.LineaPedido;
import grd.pfc.pojo.Pais;
import grd.pfc.pojo.Pedido;
import grd.pfc.pojo.Producto;
import grd.pfc.utils.Utils;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class ManagerAddPedidoController implements Initializable{

    @FXML
    private TableView<Producto> tableProductos;

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
    private JFXTextField idProd;
    
    @FXML
    private TableView<LineaPedido> tablePedido;
    
    private ArrayList<Text> productosPedido;
    private int maxQ=-1; //Product stock
    private ManagerDAO dao;
    public void addQ() {
        if(productQ.getText().equals("")){Utils.alertGenerator("Error", "", "Seleccione algún producto", 4); return;}
        if((Integer.parseInt(productQ.getText())+1)>maxQ){Utils.alertGenerator("Error", "", "El pedido no puede tener más cantidad que el stock del disponible", 4);}else{
            productQ.setText(""+(Integer.parseInt(productQ.getText())+1));
        }
    }

    public void clean() {

    }

    public void paisFilter() {
        System.out.println(txtPais.getText());
        ArrayList<Pais> paises = dao.getFilteredPaises(txtPais.getText().toUpperCase());
        System.out.println(paises);
        if(paises.isEmpty()){return;}
        comboPais.getItems().clear();
        paises.forEach(p->{
            comboPais.getItems().add(p.getPais());
        });
    }
    
    public void save() {
        if(checkFields()){
            Pedido pedido = new Pedido(
                -1,
                comboCliente.getValue(),
                null,
                Utils.pickerToSql(dateSalida),
                comboEstado.getValue(),
                txtDestinatario.getText(),
                txtDireccion.getText(),
                txtTel.getText(),
                comboPais.getValue()
            );
            ManagerDAO dao = new ManagerDAO();
            dao.addPedido(pedido);
            tablePedido.getItems().forEach(l->{
                dao.addLineaPedido(l.getId(), l.getCantidad());
            });
            Utils.alertGenerator("Éxito", "", "Producto añadido con éxito", 2);
            clearFields();
        }
       
    }
    
    public void substractQ() {
        if(productQ.getText().equals("")){Utils.alertGenerator("Error", "", "Seleccione algún producto", 4); return;}
        if(Integer.parseInt(productQ.getText())==0){
            Utils.alertGenerator("Error", "", "El pedido ha de tener al menos una unidad del producto", 4);
            productQ.setText(""+(Integer.parseInt(productQ.getText())));
        }
        else{
            productQ.setText(""+(Integer.parseInt(productQ.getText())-1));
        }
    }
    
    public void addProduct(){
        if(!idProd.getText().equals("")){
            
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
                        
                    }
                }else{return;}                  
            }
        });
         
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new ManagerDAO();
        refreshTable();
        plus.setDisable(false);
        minus.setDisable(false);
        //Eliminar Linea Pedido
        tablePedido.setOnMouseClicked(event -> {
                if(event.getClickCount()==2) {
                    if(tablePedido.getSelectionModel().getSelectedItem()!=null){
                        LineaPedido lp = tablePedido.getSelectionModel().getSelectedItem();
                        ObservableList<LineaPedido> tbLp = tablePedido.getItems();
                        ObservableList<Producto> tbPr = tableProductos.getItems();
                        
                        for(int i=0;i<tbPr.size();i++){
                            if(tbPr.get(i).getId()==lp.getId()){
                                Producto p = tbPr.get(i);
                                p.setStock(p.getStock()+lp.getCantidad());
                                tbPr.set(i, p);
                                maxQ=p.getStock()+lp.getCantidad();
                            }
                        }
                        tbLp.remove(lp);
                    }else{return;}                  
                }
        });
        
        txtPais.addEventHandler(KeyEvent.KEY_TYPED, event -> {
                paisFilter();
        });
        
        getCombos();
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
       if(tablePedido.getItems().isEmpty()){
            Utils.alertGenerator("ERROR", "","Inserte al menos un producto en el pedido",4);
            return false;
       }
        return true;
    }
    private void clearFields(){
        comboCliente.setValue("");
        comboEstado.setValue("");
        comboPais.setValue("");
        idProd.setText("");
        txtTel.setText("");
        txtProducto.setText("");
        txtDestinatario.setText("");
        txtPais.setText("");
        txtDireccion.setText("");
        maxQ=-1;
        plus.setDisable(false);
        minus.setDisable(false);
        refreshTable();
        tablePedido.getItems().clear();
        dateSalida.setValue(null);
        paisFilter();
    }
}
