package grd.pfc.employee.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.AdministracionDAO;
import grd.pfc.dao.BasicViewDAO;
import grd.pfc.dao.ManagerDAO;
import grd.pfc.pojo.IVA;
import grd.pfc.pojo.Marca;
import grd.pfc.pojo.Producto;
import grd.pfc.pojo.Seccion;
import grd.pfc.singleton.InfoBundle;
import grd.pfc.utils.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ManagerAddProductController implements Initializable{

    @FXML
    private JFXButton butSave;

    @FXML
    private JFXButton butClean;

    @FXML
    private JFXTextField txtReferencia;

    @FXML
    private JFXTextField txtProducto;

    @FXML
    private JFXTextField txtDescripcion;

    @FXML
    private JFXComboBox<String> comboMarca;

    @FXML
    private JFXTextField txtCoste;

    @FXML
    private JFXTextField txtPrecio;

    @FXML
    private JFXComboBox<String> comboIVA;

    @FXML
    private JFXTextField txtDescuento;

    @FXML
    private JFXTextField txtModelo;

    @FXML
    private JFXTextField txtColor;

    @FXML
    private JFXTextField txtStock;
    
    @FXML
    private ScrollPane scrollSecciones;
    
    private ArrayList<JFXCheckBox> checkList;
    
    public void clean() {
        comboIVA.getSelectionModel().clearSelection();
        comboMarca.getSelectionModel().clearSelection();
        txtReferencia.setText("");
        txtProducto.setText("");
        txtDescripcion.setText("");
        txtCoste.setText("");
        txtPrecio.setText("");
        txtDescuento.setText("");
        txtModelo.setText("");
        txtColor.setText("");
        txtStock.setText("");
        checkList.forEach(c->{c.setSelected(false);});
        
    }
    private boolean checkFields() {
        if(txtReferencia.textProperty().getValue().equals("") || txtProducto.textProperty().getValue().equals("") || txtCoste.textProperty().getValue().equals("") ||txtPrecio.textProperty().getValue().equals("") ||txtModelo.textProperty().getValue().equals("") || txtStock.textProperty().getValue().equals("") || comboIVA.getValue()==null || comboMarca.getValue()==null){
            Utils.alertGenerator("ERROR", "", "Complete todos los campos obligatorios (*)", 4);
            return false;
        }
        if(!Utils.isNumeric(txtPrecio.textProperty().getValue()) || !Utils.isNumeric(txtCoste.textProperty().getValue()) ||!Utils.isNumeric(txtDescuento.textProperty().getValue())){
            if(!Utils.isDouble(txtPrecio.textProperty().getValue()) || !Utils.isDouble(txtCoste.textProperty().getValue()) ||!Utils.isDouble(txtDescuento.textProperty().getValue())){
                Utils.alertGenerator("ERROR", "", "Los campos Precio, Coste y Descuento han de ser num??ricos",4);
                return false;
            }
        }
        
        if(Double.parseDouble(txtPrecio.textProperty().getValue())<0 || Double.parseDouble(txtCoste.textProperty().getValue())<0 || Double.parseDouble(txtDescuento.textProperty().getValue())<0){
            Utils.alertGenerator("ERROR", "", "Los campos Precio, Coste y Descuento han de ser positivos",4);
            return false;
        }
        if(Double.parseDouble(txtPrecio.textProperty().getValue())<Double.parseDouble(txtCoste.textProperty().getValue())){
            Utils.alertGenerator("ERROR", "", "El coste no puede ser mayor que el precio de venta",4);
            return false;
        }
        if(Double.parseDouble(txtPrecio.textProperty().getValue())<Double.parseDouble(txtDescuento.textProperty().getValue())){
            Utils.alertGenerator("ERROR", "", "El descuento no puede ser mayor que el precio de venta",4);
            return false;
        }
        ManagerDAO dao = new ManagerDAO();
        if(dao.getIdProductByRef(txtReferencia.textProperty().getValue())!=-1){
           Utils.alertGenerator("ERROR", "", "La referencia ya est?? en uso",4); 
           return false;
        }
        return true;
    }
    public void save() {
        if(checkFields()){
            ManagerDAO dao =new ManagerDAO();
            ArrayList<Integer> secciones = new ArrayList<Integer>();
            checkList.forEach(c->{
                if(c.isSelected()){
                    secciones.add(dao.getSectionId(c.getId()));
                }
            });
            Producto prod = new Producto(
                    -1,
                    txtProducto.textProperty().getValue(),
                    txtDescripcion.textProperty().getValue(),
                    Double.parseDouble(txtPrecio.textProperty().getValue()),
                    Double.parseDouble(txtDescuento.textProperty().getValue()),
                    comboMarca.getValue(),
                    txtReferencia.textProperty().getValue(),
                    txtModelo.textProperty().getValue(),
                    txtColor.textProperty().getValue(),
                    Integer.parseInt(txtStock.textProperty().getValue()),
                    comboIVA.getValue(),
                    Double.parseDouble(txtCoste.textProperty().getValue()),
                    secciones);
            dao.insertProducto(prod);
            Utils.alertGenerator("OK", "", "El producto '"+txtProducto.getText()+"' se ha guardado correctamente", 2);
            clean();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getCombos();
    }

    private void getCombos() {
        BasicViewDAO viewDao= new BasicViewDAO();
        AdministracionDAO adminDao= new AdministracionDAO();
        ArrayList<Seccion> secciones = adminDao.getSecciones();
        VBox checkListBox = new VBox();
        checkList = new ArrayList<JFXCheckBox>();
        secciones.forEach(s->{
            JFXCheckBox ch = new JFXCheckBox(s.getNombre());
            ch.setId(s.getNombre());
            ch.setStyle("-jfx-checked-color: #4059a9; -jfx-unchecked-color: #4059a9; -fx-font-size: 16px;");
            checkList.add(ch);
        });
        checkList.forEach(c->{checkListBox.getChildren().add(c);});
        scrollSecciones.setContent(checkListBox);
        ArrayList<Marca> marcas = viewDao.getMarcas();
        marcas.forEach(m->{
            comboMarca.getItems().add(m.getMarca());
        });
        ArrayList<IVA> ivas = viewDao.getIvas();
            ivas.forEach(i->{
              comboIVA.getItems().add(i.getIVA());
            });
    }

}