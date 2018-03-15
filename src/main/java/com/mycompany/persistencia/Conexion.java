package com.mycompany.persistencia;

import com.mycompany.gymadmin.MainApp;
import com.mycompany.gymadmin.Stages;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * @author eliaslc
 */
public class Conexion extends Thread {

    @Override
    public void run() {
        try {
                Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        Platform.runLater(()->{
            MainApp.closeSplash();
        Stages.createLogIn();
        });
        
        
    }
    
}
