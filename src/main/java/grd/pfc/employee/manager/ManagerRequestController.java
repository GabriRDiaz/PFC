package grd.pfc.employee.manager;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import grd.pfc.dao.ManagerDAO;
import grd.pfc.pojo.Producto;
import grd.pfc.pojo.Sugerencia;
import grd.pfc.singleton.InfoBundle;
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
        System.out.println("leida");
    }
    
    public void markUnread() {
        System.out.println("no leida");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
    }

    private void refreshTable() {
        ManagerDAO dao = new ManagerDAO();
        ArrayList<Integer> requests = new ArrayList<Integer>();
        requests = dao.getRequestSections(InfoBundle.getInfoBundle().getIdEmpleado());
        requests.forEach(r->{System.out.println(r);});
        ArrayList<Sugerencia> sugerencias = new ArrayList<Sugerencia>();
        sugerencias = dao.getRequests(requests);
        ObservableList<Sugerencia> listSugerencias = FXCollections.observableArrayList();
        sugerencias.forEach(s->{System.out.println(s.getId());});
        listSugerencias.addAll(sugerencias);
        tableSugerencias.setItems(listSugerencias);
    }

}

