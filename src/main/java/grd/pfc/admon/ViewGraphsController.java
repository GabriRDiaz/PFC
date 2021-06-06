package grd.pfc.admon;

import grd.pfc.dao.ChartDAO;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class ViewGraphsController implements Initializable{

    @FXML
    private BarChart<String, Integer> chartEmpleados;

    @FXML
    private CategoryAxis axisSecciones;

    @FXML
    private BarChart<String, Integer> chartPedidos;

    @FXML
    private CategoryAxis axisClientes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCharts();
    }
    
       private void loadCharts(){
        ChartDAO dao = new ChartDAO();
        Map<String,Integer> empSec = dao.getEmpleadosSeccion();

        ObservableList<String> listaSecciones = FXCollections.observableArrayList();
        List<String> secciones =  new ArrayList<String>();
        
        empSec.forEach((seccion,empleados)->{
            secciones.add(seccion);
        });
        
        listaSecciones.addAll(secciones);
        axisSecciones.setCategories(listaSecciones);
        
        XYChart.Series<String, Integer> seriesDataSec = new XYChart.Series<>();
        empSec.forEach((seccion,empleados)->{
            seriesDataSec.getData().add(new XYChart.Data<>(seccion, empleados));
        });
        chartEmpleados.getData().add(seriesDataSec);
        chartEmpleados.setLegendVisible(false);
        
        Map<String,Integer> pedClt = dao.getClientePedidos();

        ObservableList<String> listaClientes = FXCollections.observableArrayList();
        List<String> clientes =  new ArrayList<String>();
        
        pedClt.forEach((cliente,pedidos)->{
            clientes.add(cliente);
            System.out.println(cliente);
        });
        
        listaClientes.addAll(clientes);
        axisClientes.setCategories(listaClientes);
        
        XYChart.Series<String, Integer> seriesDataClt = new XYChart.Series<>();
        pedClt.forEach((cliente,pedidos)->{
            seriesDataClt.getData().add(new XYChart.Data<>(cliente, pedidos));
        });
        chartPedidos.getData().add(seriesDataClt);
        chartPedidos.setLegendVisible(false);
    }
    
}