package grd.pfc.employee.basic;

import com.jfoenix.controls.JFXTextArea;
import grd.pfc.utils.Utils;
import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
