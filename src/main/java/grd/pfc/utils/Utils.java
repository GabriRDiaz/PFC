package grd.pfc.utils;

import java.time.ZoneId;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;

public class Utils {
    
    public static java.sql.Date dateToSql(DatePicker javaDate){
        java.util.Date utilDate = java.util.Date.from(javaDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }
    
    public static boolean alertGenerator(String title,String header,String body){
        ButtonType yes = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.OK_DONE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", yes,no);
            if(!title.equals("")){
              alert.setTitle(title);  
            }else{alert.setTitle(null);}

            if(!header.equals("")){
                alert.setHeaderText(header);
            }else{alert.setHeaderText(null);}

            if(!body.equals("")){
                alert.setContentText(body);
            }else{alert.setContentText(null);}

            alert.showAndWait();
            if(alert.getResult()==yes){return true;}else{return false;}
    }
}

