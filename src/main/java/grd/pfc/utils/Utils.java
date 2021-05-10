package grd.pfc.utils;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;

public class Utils {
    
    public static java.sql.Date pickerToSql(DatePicker pickerDate){
        java.util.Date utilDate = java.util.Date.from(pickerDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }
    
    
    public static java.sql.Date dateToSql(java.util.Date javaDate){
        java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
        return sqlDate;
    }
    
    public static LocalDate SqlToPicker(Date sqlDate){
        LocalDate fechaPicker = Instant.ofEpochMilli(sqlDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        return fechaPicker;
    }
    
    public static boolean alertGenerator(String title,String header,String body,int type){
        //Types: 0-NONE,1-CONFIRMATION,2-INFORMATION 3.WARNING, 4-ERROR
        ButtonType yes = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.OK_DONE);
        Alert alert;
        switch(type){
           case 0: alert = new Alert(Alert.AlertType.NONE, "", yes); break;
           case 1: alert = new Alert(Alert.AlertType.CONFIRMATION, "", yes,no); break;
           case 2: alert = new Alert(Alert.AlertType.INFORMATION, "", yes); break;
           case 3: alert = new Alert(Alert.AlertType.WARNING, "", yes,no); break;
           case 4: alert = new Alert(Alert.AlertType.ERROR); break;
           default: alert = new Alert(Alert.AlertType.CONFIRMATION,"",yes); break;
           
        }
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
    
    public static boolean isNumeric(String str) { 
        try {  
          Integer.parseInt(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
    }
}

