package com.mycompany.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.gymadmin.Stages;
import com.mycompany.interacciondb.Ingresar;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LoginController implements Initializable {

    @FXML private JFXTextField user;
    @FXML private JFXPasswordField pass;
    @FXML private JFXButton ingresar;
    @FXML private ProgressIndicator prog;
    @FXML private Label olvidopass;
    @FXML private AnchorPane root;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ingreso();
       recuperarPass();
    }    
    
    //Metodo para cambiar a recuperar contraseña
    private void recuperarPass(){
        olvidopass.setOnMouseClicked((e)->{
            cambiarPane("/fxml/RecuperarPass.fxml",root);
        });
    }
    
    //Metodo para ingresar al sistema
    private void ingreso(){
        
         ingresar.setOnAction((e)->{
             
            if(user.getText().equals("")||pass.getText().equals("")){
                    Alertas.error("Error de ingreso", "", "Se encuentran campos vacios");
            } else{
            user.setDisable(true); pass.setDisable(true);
            ingresar.setDisable(true);
            prog.setVisible(true);
            Ingresar ing = new Ingresar(user.getText(),pass.getText());
            
            ing.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent event) -> {
                int result = ing.getValue();
                switch (result) {
                    case 0:
                        Platform.runLater(()->{
                            Stages.cerrarLogIn();
                            Stages.createCuerpo();
                        }); break;
                    case 1:
                        Alertas.error("Datos Errones", "Verifique sus datos", "Usuario Incorrecto");
                        break;
                    case 2:
                        Alertas.error("Datos Errones", "Verifique sus datos", "Contraseña Incorrecta");
                        break;
                    default:
                        break;
                }
                prog.setVisible(false);
                ingresar.setDisable(false);
                user.setDisable(false); pass.setDisable(false);
                user.requestFocus();
            });
            
            Thread t = new Thread(ing);
            t.start();
            }
        });
    }
    
    //Metodo para cargar la siguiente ventana
     private void cambiarPane(String archivo, AnchorPane root){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(archivo));
            FadeTransition fd = new FadeTransition(Duration.seconds(1),pane);
            root.getChildren().setAll(pane);
            fd.setFromValue(0); fd.setToValue(1);
            fd.setCycleCount(1);
            fd.play();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}