/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gymadmin;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author eliaslc
 */
public class Stages {
    
    private static Stage login;
    
    public static void createLogIn(){
          login= new Stage();
          Parent pane = null;
          try {
              pane = FXMLLoader.load(MainApp.class.getResource("/fxml/login.fxml"));
              login.setScene(new Scene(pane));
              login.setResizable(false);
              login.setTitle("Bienvenido A LunchSales");
              login.centerOnScreen();
              
              login.show();
          } catch (IOException ex) {
              ex.printStackTrace();
          }
    }

    
}
