package grd.pfc.employee.basic;

import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.BasicViewDAO;
import grd.pfc.pojo.Producto;
import grd.pfc.utils.Utils;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class RegStockController implements Initializable{

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
    private Text txtModelo;

    @FXML
    private Text txtMarca;

    @FXML
    private Text txtColor;

    @FXML
    private Text txtRef;

    @FXML
    private ImageView saveReg;
    private int stockValue;
    private Producto producto;
    public void addStock() {
        productStock.textProperty().setValue(""+(++stockValue));
    }
    
    public void saveReg() {
        if(productStock.textProperty().getValue().equals("") ||productStock.textProperty().getValue()==null){
            Utils.alertGenerator("ERROR", "", "¡Seleccione un producto!", 4); return;
        }
        if(Utils.isNumeric(productStock.textProperty().getValue())){
            BasicViewDAO regDao = new BasicViewDAO();
            regDao.updStock(Integer.parseInt(productStock.textProperty().getValue()), producto.getId());
            Utils.alertGenerator("Información", "", "Stock actualizado correctamente", 2);
            refreshTable();
        }else{Utils.alertGenerator("ERROR", "", "¡Valor de stock incorrecto!", 4);}
    }
    
    public void substract() {
        if((stockValue-1)<0){Utils.alertGenerator("ERROR", "", "¡El stock ha de ser positivo!", 4); return;}
        productStock.textProperty().setValue(""+(--stockValue));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
    }

    private void refreshTable() {
        BasicViewDAO viewDao = new BasicViewDAO();
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        List<Producto> foundProductos = viewDao.getProductos("");
        productos.addAll(foundProductos);
        tableProductos.setItems(productos);
        
         tableProductos.setOnMouseClicked( event -> {
            if(event.getClickCount()==2) {
                plus.setDisable(false);
                minus.setDisable(false);
                producto = tableProductos.getSelectionModel().getSelectedItem();
                productName.textProperty().setValue(producto.getNombre());
                stockValue=producto.getStock();
                productStock.textProperty().setValue(""+stockValue);
                txtModelo.textProperty().setValue(producto.getModelo());
                txtMarca.textProperty().setValue(producto.getMarca());
                txtColor.textProperty().setValue(producto.getColor());
                txtRef.textProperty().setValue(producto.getReferencia());
            }
        });
    }

}