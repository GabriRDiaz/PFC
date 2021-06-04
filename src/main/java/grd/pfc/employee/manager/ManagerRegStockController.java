package grd.pfc.employee.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.BasicViewDAO;
import grd.pfc.pojo.IVA;
import grd.pfc.pojo.Marca;
import grd.pfc.pojo.Producto;
import grd.pfc.pojo.Seccion;
import grd.pfc.singleton.InfoBundle;
import grd.pfc.utils.Utils;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class ManagerRegStockController implements Initializable{

    @FXML
    private JFXButton butSave;

    @FXML
    private JFXButton butClean;

    @FXML
    private JFXComboBox<String> comboSeccion;

    @FXML
    private JFXComboBox<String> comboMarca;

    @FXML
    private JFXComboBox<String> comboIVA;

    @FXML
    private ImageView clean;

    @FXML
    private ImageView filter;

    @FXML
    private TableView<Producto> tableProductos;

    @FXML
    private JFXTextField productName;

    @FXML
    private ImageView minus;

    @FXML
    private JFXTextField productStock;
    
    @FXML
    private ImageView plus;
    
    @FXML
    private ImageView showDesglose;
    
    private int stockValue;
    private Producto producto;
    private String filterQuery;
    
    public void showDesglose(){
        if(productStock.textProperty().getValue().equals("") ||productStock.textProperty().getValue()==null){
            Utils.alertGenerator("ERROR", "", "¡Seleccione un producto!", 4); return;
        }
        Alert alert = new Alert(Alert.AlertType.NONE, "Referencia: #"+producto.getReferencia(), ButtonType.OK);
        alert.setTitle("Información económica");
        DecimalFormat df=new DecimalFormat("#.##");
        
        double valorIVA = producto.getPrecioSinIVA()+producto.getPrecioSinIVA()*Double.parseDouble(producto.getIvaStr())/100;

        double subtotal = valorIVA-producto.getDescuento();
        double beneficio = producto.getPrecioSinIVA()-producto.getDescuento()-producto.getCoste();
        double porcBen = beneficio*100/producto.getCoste();
        alert.setHeaderText(
        "Producto: "+producto.getNombre()+
        "\nPrecio sin IVA: "+df.format(producto.getPrecioSinIVA())+"€"+
        "\nPrecio con IVA: "+df.format(valorIVA)+"€ ("+producto.getIvaStr()+"%)"+
        "\nDescuento: "+df.format(producto.getDescuento())+"€"+
        "\nSubtotal: "+df.format(subtotal)+"€\n"+
        "-------------------------------------------------------------------"+
        "\nCoste: "+df.format(producto.getCoste())+"€"+
        "\nBeneficio: "+df.format(beneficio)+"€ ("+df.format(porcBen)+"%)");
        
        alert.showAndWait();
    }
    
    public void addStock() {
        productStock.textProperty().setValue(""+(++stockValue));
    }
    
    public void clean() {
       productName.textProperty().setValue("");
       productStock.textProperty().setValue("");
       stockValue=0;
       producto=null;
       plus.setDisable(true);
       minus.setDisable(true);
       filterQuery="";
    }

    public void cleanCombos() {
        comboSeccion.getSelectionModel().clearSelection();
        comboMarca.getSelectionModel().clearSelection();
        comboIVA.getSelectionModel().clearSelection();
        refreshTable("");
    }

    public void filter() {
        if(!comboSeccion.getSelectionModel().isEmpty()||!comboMarca.getSelectionModel().isEmpty()||!comboIVA.getSelectionModel().isEmpty()){
            filterQuery="";
            if(!comboSeccion.getSelectionModel().isEmpty()){
               filterQuery = filterQuery.concat(" AND s.Nombre='"+comboSeccion.getValue()+"'");
            }
            if(!comboMarca.getSelectionModel().isEmpty()){
                filterQuery = filterQuery.concat(" AND m.Marca='"+comboMarca.getValue()+"'");
            }
            if(!comboIVA.getSelectionModel().isEmpty()){
                filterQuery = filterQuery.concat(" AND i.iva='"+comboIVA.getValue()+"'");
            }
            refreshTable(filterQuery);
        }else{Utils.alertGenerator("ERROR", "", "Escoja alguna opción de filtrado", 4);}
    }

    public void save() {
        if(productStock.textProperty().getValue().equals("") ||productStock.textProperty().getValue()==null){
            Utils.alertGenerator("ERROR", "", "¡Seleccione un producto!", 4); return;
        }
        if(Utils.isNumeric(productStock.textProperty().getValue())){
            BasicViewDAO regDao = new BasicViewDAO();
            System.out.println(productStock.textProperty().getValue());
            System.out.println(producto.getId());
            regDao.updStock(Integer.parseInt(productStock.textProperty().getValue()), producto.getId());
            Utils.alertGenerator("Información", "", "Stock actualizado correctamente", 2);
            refreshTable("");
            comboIVA.getSelectionModel().clearSelection();
            comboMarca.getSelectionModel().clearSelection();
            comboSeccion.getSelectionModel().clearSelection();
        }else{Utils.alertGenerator("ERROR", "", "¡Valor de stock incorrecto!", 4);}
    }

    public void substract() {
        if((stockValue-1)<0){Utils.alertGenerator("ERROR", "", "¡El stock ha de ser positivo!", 4); return;}
        productStock.textProperty().setValue(""+(--stockValue));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getCombos();
        refreshTable("");
    }
    private void getCombos(){
        BasicViewDAO viewDao= new BasicViewDAO();
        ArrayList<Seccion> secciones = viewDao.getSecciones(InfoBundle.getInfoBundle().getIdEmpleado()); 
        secciones.forEach(s->{
            comboSeccion.getItems().add(s.getNombre());
        });
        ArrayList<Marca> marcas = viewDao.getMarcas();
        marcas.forEach(m->{
            comboMarca.getItems().add(m.getMarca());
        });
        ArrayList<IVA> ivas = viewDao.getIvas();
            ivas.forEach(i->{
              comboIVA.getItems().add(i.getIVA());
            });
    }

    private void refreshTable(String filter) {
        BasicViewDAO viewDao = new BasicViewDAO();
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        List<Producto> foundProductos = viewDao.getProductos(filter);
        productos.addAll(foundProductos);
        tableProductos.setItems(productos);
        
        //TODO: Add prices alert in table

        tableProductos.setOnMouseClicked( event -> {
            if(event.getClickCount()==2) {
                if(tableProductos.getSelectionModel().getSelectedItem()!=null){
                    producto = tableProductos.getSelectionModel().getSelectedItem();
                    productName.setText(producto.getNombre());
                    plus.setDisable(false);
                    minus.setDisable(false);
                    stockValue=producto.getStock();
                    productStock.textProperty().setValue(""+stockValue);
                }else{return;}
            }
        });
    }

}