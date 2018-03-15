package com.mycompany.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SplashController implements Initializable {
    
    @FXML AnchorPane Pane;
    @FXML ImageView logo;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ScaleTransition st = new ScaleTransition(Duration.seconds(3),logo);
        st.setToX(4); st.setToY(4); st.play();
    }    
 
}
