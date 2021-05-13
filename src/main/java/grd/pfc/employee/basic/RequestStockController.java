package grd.pfc.employee.basic;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import grd.pfc.dao.BasicViewDAO;
import grd.pfc.pojo.Seccion;
import grd.pfc.singleton.InfoBundle;
import grd.pfc.utils.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class RequestStockController implements Initializable {
    @FXML
    private JFXTextArea txtRequest;
    @FXML
    private ImageView clearBut;
    @FXML
    private ImageView sendBut;
    @FXML
    private JFXComboBox<String> comboResponsable;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    BasicViewDAO viewDao= new BasicViewDAO();
        ArrayList<Seccion> secciones = viewDao.getSecciones(InfoBundle.getInfoBundle().getIdEmpleado()); 
        secciones.forEach(s->{
            comboResponsable.getItems().add("Resp. "+s.getNombre());
        });
    }    
    
    public void clear() {
        if(Utils.alertGenerator("Aviso", "Se vaciará el campo de texto", "¿Desea continuar?", 3)){txtRequest.clear();}
    }
    
    public void send() {
        if(Utils.alertGenerator("Pregunta", "Se enviará la sugerencia al responsables de sección", "¿Desea continuar?", 1)){
            
            Utils.alertGenerator("Éxito", "", "¡Sugerencia enviada correctamente!", 2);
        }
    }
    
}
