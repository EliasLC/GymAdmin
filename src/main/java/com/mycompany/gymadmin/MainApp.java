package com.mycompany.gymadmin;

import com.mycompany.persistencia.Conexion;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainApp extends Application {
   
    private static Stage main;
    
    @Override
    public void start(Stage stage) throws Exception {
        main = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Splash.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage = null;
        scene.setFill(Color.TRANSPARENT);
        main.initStyle(StageStyle.TRANSPARENT);
        main.setScene(scene);
        Thread t = new Thread(new Conexion());
        main.show();
        t.start();
    }
    
    public static void closeSplash(){
        main.hide();
        main=null;
        System.gc();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
