package grd.pfc.employee.manager;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.ManagerDAO;
import grd.pfc.pojo.Empleado;
import grd.pfc.pojo.Producto;
import grd.pfc.pojo.Sugerencia;
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
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class ManagerRequestController implements Initializable{

    @FXML
    private TableView<Sugerencia> tableSugerencias;

    @FXML
    private ImageView markRead;

    @FXML
    private ImageView markUnread;

    @FXML
    private JFXTextArea txtSugerencia;
    
    @FXML
    private JFXTextField idSug;
    
    public void markRead() {
        if(checkSelected()) {
            ManagerDAO dao = new ManagerDAO();
            if(dao.updRevisada(1, Integer.parseInt(idSug.getText()))==1){
                Utils.alertGenerator("OK", "", "La sugerencia ha sido marcada como revisada", 2);
                refreshTable();
            }
        }
    }
    
    public void markUnread() {
        if(checkSelected()) {
            ManagerDAO dao = new ManagerDAO();
            if(dao.updRevisada(0, Integer.parseInt(idSug.getText()))==1){
                Utils.alertGenerator("OK", "", "La sugerencia ha sido marcada como no revisada", 2);
                refreshTable();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
        tableSugerencias.setOnMouseClicked(event -> {
            if(event.getClickCount()==2) {
               Sugerencia sugerencia = tableSugerencias.getSelectionModel().getSelectedItem();
               idSug.setText(""+sugerencia.getId());
               txtSugerencia.setText(sugerencia.getSugerencia());
            }
          });
    }

    private void refreshTable() {
        ManagerDAO dao = new ManagerDAO();
        ArrayList<Integer> requests = new ArrayList<Integer>();
        requests = dao.getRequestSections(InfoBundle.getInfoBundle().getIdEmpleado());
        ArrayList<Sugerencia> sugerencias = new ArrayList<Sugerencia>();
        sugerencias = dao.getRequests(requests);
        ObservableList<Sugerencia> listSugerencias = FXCollections.observableArrayList();
        listSugerencias.addAll(sugerencias);
        tableSugerencias.setItems(listSugerencias);
    }

    private boolean checkSelected() {
        if(idSug.getText().equals("") || txtSugerencia.equals("")){
            Utils.alertGenerator("ERROR", "", "Seleccione alguna sugerencia", 4);
            return false;
        }
        return true;
    }

}

