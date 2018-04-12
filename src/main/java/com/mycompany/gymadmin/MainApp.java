package com.mycompany.gymadmin;

import com.mycompany.persistencia.Conexion;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void start(Stage stage) {
        try {
            main = stage;
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Splash.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            stage = null;
            scene.setFill(Color.TRANSPARENT);
            main.initStyle(StageStyle.TRANSPARENT);
            main.setScene(scene);
            main.show();
            Thread t = new Thread(new Conexion());
            t.start();
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
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
