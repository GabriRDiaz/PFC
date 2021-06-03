package grd.pfc.employee.basic;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import grd.pfc.dao.BasicViewDAO;
import grd.pfc.pojo.Seccion;
import grd.pfc.pojo.Sugerencia;
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
    private JFXComboBox<String> comboSeccion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    BasicViewDAO viewDao= new BasicViewDAO();
        ArrayList<Seccion> secciones = viewDao.getSecciones(InfoBundle.getInfoBundle().getIdEmpleado()); 
        secciones.forEach(s->{
            comboSeccion.getItems().add(s.getNombre());
        });
    }    
    
    public void clear() {
        if(Utils.alertGenerator("Aviso", "Se vaciará el campo de texto", "¿Desea continuar?", 3)){txtRequest.clear(); comboSeccion.setValue("");}
    }
    
    public void send() {
        if(Utils.alertGenerator("Pregunta", "Se enviará la sugerencia a los gerentes de la sección seleccionada", "¿Desea continuar?", 1)){
            BasicViewDAO viewDao= new BasicViewDAO();
            int idSeccion = viewDao.getIdSeccion(comboSeccion.getValue());
            System.out.println(idSeccion);
            if(idSeccion != -1 && !txtRequest.getText().trim().equals("")){
                Sugerencia sugerencia = new Sugerencia(txtRequest.getText(),idSeccion,InfoBundle.getInfoBundle().getIdEmpleado());
                System.out.println(sugerencia.getSugerencia()+"\n"+sugerencia.getIdSeccion()+"\n"+sugerencia.getIdEmpleado());
                int result = viewDao.insertSugerencia(sugerencia);
                System.out.println(result);
                Utils.alertGenerator("Éxito", "", "¡Sugerencia enviada correctamente!", 2);
                txtRequest.clear();
                comboSeccion.setValue("");
            }else{Utils.alertGenerator("Error", "", "Seleccione una sección y escriba una sugerencia", 4);}
            
        }
    }
    
}
