package com.mycompany.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.interacciondb.RecuperarContraseña;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class RecuperarPassController implements Initializable {

    @FXML JFXButton cancelar,enviar;
    @FXML AnchorPane root;
    @FXML JFXTextField email;
    @FXML ProgressIndicator indicador;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atras();
        recuperarPass();
    }    
    
    
    //Metodo para enviar recuperar la contraseña
    private void recuperarPass(){
        enviar.setOnAction((ActionEvent e)->{
            if(email.getText().equals("")){
                Alertas.error("Error de ingreso", "", "El campo Email se enuentra vacio");
            }else{
                
                cancelar.setDisable(true); enviar.setDisable(true);
                indicador.setVisible(true);
                RecuperarContraseña rc = new RecuperarContraseña(email.getText());
             
                rc.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent event) -> {
                  
                    int result = rc.getValue();
                    
                    switch (result) {
                        case 0:
                            Alertas.informacion("Recuperacion Exitosa", "Se ha enviado su contraseña a su correo electronico");
                            cambiarPane("/fxml/login.fxml",root);
                            break;
                        case 1:
                            Alertas.error("Error de Datos", "Usuario Inexistente", "El correo electronico ingresado no es valido");
                            break;    
                        case 2:
                            Alertas.error("Error del servidor", "Envio fallido", "Verifique su conexion a Internet");
                            break;
                        default:
                            break;
                    }
                    indicador.setVisible(false);
                    cancelar.setDisable(false); enviar.setDisable(false);
                
                });
                Thread t = new Thread(rc);
                t.start();
            }
           
        });
    }
    
    //Metodo para regresar a la pantalla de login
    private void atras(){
        cancelar.setOnAction((e)->{
            cambiarPane("/fxml/login.fxml",root);
        });
    }
    
    //Metodo para regresar a la pantalla principal del login
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
