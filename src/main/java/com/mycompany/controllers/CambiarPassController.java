package com.mycompany.controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.interacciondb.CambiaPass;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.paint.Color;

public class CambiarPassController implements Initializable {

    @FXML private JFXPasswordField PassNueva, PassRNueva, PassAntigua;
    @FXML private JFXButton PassMod;
    @FXML private ProgressIndicator IndiCambiarPass;
    @FXML private Label labelNoIguales;
    
    private boolean igualdad;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        igualdad= true;
        cambiarPass(); validarNuevaContra();
    }    
    
    //Metodo para cambiar la contraseña del usuario administrador
     private void cambiarPass(){
        PassMod.setOnAction((e)->{
            
            System.out.println("ERER");
           if(PassNueva.getText().equals("")||PassRNueva.getText().equals("")||PassAntigua.getText().equals("")){
               Alertas.error("Error de ingreso", "", "Se encuentran campos vacios");
           } else if(igualdad){
               
               PassMod.setDisable(true);
               IndiCambiarPass.setVisible(true);
               PassNueva.setDisable(true); PassRNueva.setDisable(true); PassAntigua.setDisable(true);
               CambiaPass cp = new CambiaPass(PassAntigua.getText(),PassNueva.getText());
               
               cp.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent event) -> {
                   int result = cp.getValue();
                   switch (result) {
                       case 1:
                           System.out.println("Funco");
                           Alertas.informacion("Cambio de contraseña", "La constraseña se ha cambiado correctamente");
                           PassNueva.setText(""); PassRNueva.setText(""); PassAntigua.setText("");
                           break;
                       case 2:
                           System.out.println("No se encontro");
                           Alertas.error("Error de ingreso", "Contraseña incorrecta", "La contraseña introducida es erronea");
                           break;
                       case 3:
                           System.out.println("No funco");
                           Alertas.error("Error de conexion", "Error de servidor", "Imposible conectar con el servidor verifique su conexion a Internet");
                           break;
                       default:
                           break;
                   }
                IndiCambiarPass.setVisible(false);
                PassNueva.setDisable(false); PassRNueva.setDisable(false);
                PassAntigua.setDisable(false); PassMod.setDisable(false);
         
               });
               new Thread(cp).start();
           } else{
               Alertas.error("Error de ingreso", "Contraseña nueva", "Los campos de nueva contraseña no coinciden");
           }
            
        });
        
    }
     
     //Metodo para validar el contenido de dos textfields
    private void validarNuevaContra(){
        PassRNueva.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
           if (PassNueva.getText().equals(PassRNueva.getText())) {
               System.out.println("Iguales");
               cambiarColores(true);
           } else{
               System.out.println("No iguales");
               cambiarColores(false);
           }
       });
        
        PassNueva.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
           if (PassNueva.getText().equals(PassRNueva.getText())) {
               System.out.println("Iguales");
               cambiarColores(true);
           } else{
               System.out.println("No iguales");
               cambiarColores(false);
           }
       });
    }
    
    //Metodo para cambiar el color a los JFXTextfields de la nueva contraseña
    private void cambiarColores(boolean status){
        if(status){
            igualdad= status;
            PassNueva.setFocusColor(Color.web("#395368"));
            PassNueva.setUnFocusColor(Color.web("#4d4d4d"));
            PassRNueva.setFocusColor(Color.web("#395368"));
            PassRNueva.setUnFocusColor(Color.web("#4d4d4d"));
            labelNoIguales.setVisible(!status);
        } else{
            igualdad= status;
            PassNueva.setFocusColor(Color.web("#e92727"));
            PassNueva.setUnFocusColor(Color.web("#e92727"));
            PassRNueva.setFocusColor(Color.web("#e92727"));
            PassRNueva.setUnFocusColor(Color.web("#e92727"));
            labelNoIguales.setVisible(!status);
        }
    }
}