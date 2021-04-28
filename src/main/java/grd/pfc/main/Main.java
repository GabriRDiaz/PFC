package grd.pfc.main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    
    @Override
    public void start(Stage primaryStage) throws Exception{
    Parent container = new FXMLLoader().load(getClass().getResource("/grd/pfc/login/login.fxml"));
    Scene scene = new Scene(container, 900,550);
    primaryStage.setTitle("Login");
    primaryStage.setScene(scene);
    primaryStage.show();
    }
    
    private static void main(String[] args){
        launch();
    }
}
