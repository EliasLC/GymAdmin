package com.mycompany.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.gymadmin.Stages;
import com.mycompany.interacciondb.Ingresar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;

public class LoginController implements Initializable {

    @FXML JFXTextField user;
    @FXML JFXPasswordField pass;
    @FXML JFXButton ingresar;
    @FXML ProgressIndicator prog;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ingreso();
    }    
    
    //Metodo para ingresar al sistema
    private void ingreso(){
        ingresar.setOnAction((e)->{
            ingresar.setDisable(true);
            prog.setVisible(true);
            Ingresar ing = new Ingresar(user.getText(),pass.getText());
            
            ing.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent event) -> {
                boolean result = ing.getValue();
                if(result){
                    Platform.runLater(()->{
                        Stages.cerrarLogIn();
                        Stages.createCuerpo();
                    });
                   
                } else{
                    Alertas.error("Datos Errones", "Verifique sus datos", "Usuario o contraseña incorrecto");
                }
                prog.setVisible(false);
                ingresar.setDisable(false);
            });
            
            Thread t = new Thread(ing);
            t.start();
            
        });
    }   
}