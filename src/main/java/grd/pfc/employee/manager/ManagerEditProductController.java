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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ManagerEditProductController implements Initializable{

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
    private ScrollPane scrollSecciones;

    @FXML
    private TableView<Producto> tableProductos;
    
    @FXML
    private ImageView delProducto;

    @FXML
    private JFXTextField idProd;
    private ArrayList<JFXCheckBox> checkList;
    private Producto producto;
    
    public void delProducto(){
        if(idProd.textProperty().getValue().equals("")){
            Utils.alertGenerator("ERROR", "", "Seleccione un producto para eliminarlo", 4);
            return;
        }
        if(Utils.alertGenerator("ATENCIÓN", "", "Está a punto de eliminar un producto. Esta acción es irreversible y su referencia quedará en uso. ¿Desea continuar?", 3)){
           ManagerDAO dao = new ManagerDAO();
           dao.delProducto(Integer.parseInt(idProd.getText()));
           Utils.alertGenerator("Éxito", "", "Producto eliminado con éxito", 2);
           clean();
           refreshTable();
        }
    }
    
    public void clean() {
        idProd.setText("");
        txtReferencia.setText("");
        txtProducto.setText("");
        txtDescripcion.setText("");
        txtCoste.setText("");
        txtPrecio.setText("");
        comboIVA.getSelectionModel().select("");
        comboMarca.getSelectionModel().select("");
        txtDescuento.setText("");
        txtModelo.setText("");
        txtColor.setText("");
        checkList.forEach(c->{
            c.setSelected(false);
        });
    }
    
    public void save() {
        if(checkFields()){
            ManagerDAO dao = new ManagerDAO();
            ArrayList<Integer> secciones = new ArrayList<Integer>();
            checkList.forEach(c->{
                if(c.isSelected()){
                    secciones.add(dao.getSectionId(c.getId()));
                }
            });
            Producto producto = new Producto(
                Integer.parseInt(idProd.textProperty().getValue()),
                txtProducto.textProperty().getValue(),
                txtDescripcion.textProperty().getValue(),
                Double.parseDouble(txtPrecio.textProperty().getValue()),
                Double.parseDouble(txtDescuento.textProperty().getValue()),
                comboMarca.getValue(),
                txtReferencia.textProperty().getValue(),
                txtModelo.textProperty().getValue(),
                txtColor.textProperty().getValue(),
                -1,
                comboIVA.getValue(),
                Double.parseDouble(txtCoste.textProperty().getValue()),
                secciones
            );
             dao.updProducto(producto);
             Utils.alertGenerator("Éxito", "", "Producto actualizado con éxito", 2);
             clean();
            refreshTable();
        }
    }
    private boolean checkFields() {
        if(idProd.textProperty().getValue().equals("")){
            Utils.alertGenerator("ERROR", "", "Seleccione un producto para editarlo", 4);
            return false;
        }
        
        if(txtReferencia.textProperty().getValue().equals("") || txtProducto.textProperty().getValue().equals("") || txtCoste.textProperty().getValue().equals("") ||txtPrecio.textProperty().getValue().equals("") ||txtModelo.textProperty().getValue().equals("") || comboIVA.getValue()==null || comboMarca.getValue()==null){
            Utils.alertGenerator("ERROR", "", "Complete todos los campos obligatorios (*)", 4);
            return false;
        }
        if(!Utils.isNumeric(txtPrecio.textProperty().getValue()) || !Utils.isNumeric(txtCoste.textProperty().getValue()) ||!Utils.isNumeric(txtDescuento.textProperty().getValue())){
            if(!Utils.isDouble(txtPrecio.textProperty().getValue()) || !Utils.isDouble(txtCoste.textProperty().getValue()) ||!Utils.isDouble(txtDescuento.textProperty().getValue())){
                Utils.alertGenerator("ERROR", "", "Los campos Precio, Coste y Descuento han de ser numéricos",4);
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
        if(dao.getIdProductByRefEdit(txtReferencia.textProperty().getValue(),Integer.parseInt(idProd.textProperty().getValue()))!=-1){
           Utils.alertGenerator("ERROR", "", "La referencia ya está en uso",4); 
           return false;
        }
        return true;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getCombos();
        refreshTable();
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

    private void refreshTable() {
        ManagerDAO dao = new ManagerDAO();
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        List<Producto> foundProductos = dao.getProductos(InfoBundle.getInfoBundle().getIdEmpleado());
        productos.addAll(foundProductos);
        tableProductos.setItems(productos);
        
        tableProductos.setOnMouseClicked( event -> {
            if(event.getClickCount()==2) {
                if(tableProductos.getSelectionModel().getSelectedItem()!=null){
                    producto = tableProductos.getSelectionModel().getSelectedItem();
                    idProd.setText(""+producto.getId());
                    txtReferencia.setText(producto.getReferencia());
                    txtProducto.setText(producto.getNombre());
                    txtDescripcion.setText(producto.getDescripcion());
                    txtCoste.setText(""+producto.getCoste());
                    txtPrecio.setText(""+producto.getPrecioSinIVA());
                    System.out.println(producto.getIvaStr());
                    comboIVA.getSelectionModel().select(producto.getIvaStr());
                    comboMarca.getSelectionModel().select(producto.getMarca());
                    txtDescuento.setText(""+producto.getDescuento());
                    txtModelo.setText(producto.getModelo());
                    txtColor.setText(producto.getColor());
                    
                    ArrayList<Seccion> secciones = dao.getSeccionesProducto(producto.getId());
                    ArrayList<String> sect = new ArrayList<String>();
                    secciones.forEach(s->{sect.add(s.getNombre());});
                        checkList.forEach(c->{
                            if(sect.contains(c.getId())){
                                c.setSelected(true);
                            }else{c.setSelected(false);}
                        });
                }
            }   
        });
    }
}