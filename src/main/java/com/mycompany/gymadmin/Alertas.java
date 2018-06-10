package com.mycompany.gymadmin;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * @author elias
 */
public class Alertas {
             //Mostrar alerta de aviso
          public static void warning(String titulo, String encabezado, String texto){
       
           Alert alert= new Alert(AlertType.WARNING);
           alert.setTitle(titulo);
           alert.setHeaderText(encabezado);
           alert.setContentText(texto);
           alert.showAndWait();
           
       }
       
          //Alerta de informacion
       public static void informacionEncabezado(String titulo, String encabezado, String texto){
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle(titulo);
           alert.setHeaderText(encabezado);
           alert.setContentText(texto);
           alert.showAndWait();
       }
       
       //Alerta de informacion
       public static void informacion(String titulo, String texto){
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle(titulo);
           alert.setHeaderText(null);
           alert.setContentText(texto);
           alert.showAndWait();
       }
       
       //Alert de error
       public static void error (String titulo, String encabezado, String texto){
           Alert alert = new Alert(AlertType.ERROR);
           alert.setTitle(titulo);
           alert.setHeaderText(encabezado);
           alert.setContentText(texto);
           alert.showAndWait();
       }
       
       //Alerta de confirmacion
       public static void confirmacionPregunta(int input){ 
           switch(input){
               case 1: 
                      Optional<ButtonType> result = pregunta("Cerrar Secion","Esta apunto de cerrar secion","¿Desea cerrar la secion actal?").showAndWait();
                      if (result.get() == ButtonType.OK){
                          new Thread(new Cambio()).start();
                      } else{
                      
                      } break;
           }
          
        }
       
       private static Alert pregunta(String titulo, String enca, String texto){
           Alert alert = new Alert(AlertType.CONFIRMATION);
           alert.setTitle(titulo);
           alert.setHeaderText(enca);
           alert.setContentText(texto);
           
           return alert;
       }
 
}
