package com.mycompany.gymadmin;

import com.mycompany.persistencia.Conexion;
import com.mycompany.persistencia.DataBase;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author eliaslc
 */
public class Stages {
    
    private static Stage login;
    private static Stage cuerpo;
    
    public static void createLogIn(){
          login= new Stage();
          Parent pane = null;
          try {
              pane = FXMLLoader.load(MainApp.class.getResource("/fxml/login.fxml"));
              login.setScene(new Scene(pane));
              login.setResizable(false);
              login.setTitle("Bienvenido A Monitor GYM");
              login.centerOnScreen();
              login.getIcons().add(new Image(MainApp.class.getResourceAsStream("/images/Logo.png")));
              valCerrar(login);
              login.show();
          } catch (IOException ex) {
               Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
    
    public static void cerrarLogIn(){
        login.hide(); login=null;
        System.gc();
    }

    
    public static void createCuerpo(){
        cuerpo= new Stage();
        Parent pane= null;
        try{
            pane = FXMLLoader.load(MainApp.class.getResource("/fxml/Main.fxml"));
            cuerpo.setScene(new Scene(pane));
            cuerpo.setResizable(false);
            cuerpo.setTitle("Administrador");
            cuerpo.centerOnScreen();
            cuerpo.getIcons().add(new Image(MainApp.class.getResourceAsStream("/images/Logo.png")));
            valCerrar(cuerpo);
            cuerpo.show();
        }catch(IOException ex){
             Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        login=null;
    }
    
    public static void cerrarCuerpo(){
        cuerpo.hide(); cuerpo= null;
        System.gc();
    }
    
    
        //Validad el cerrar de ventanas
     private static void valCerrar(Stage sta){
        sta.setOnCloseRequest((WindowEvent event) -> {
               DataBase.getEMF().close();
        });
        sta=null;
     }
    
}
