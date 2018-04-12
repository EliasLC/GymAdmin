package com.mycompany.persistencia;

import com.mycompany.gymadmin.MainApp;
import com.mycompany.gymadmin.Stages;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javax.swing.JOptionPane;
/**
 * @author eliaslc
 */
public class Conexion extends Task<Void> {

    @Override
    protected Void call() {
        
        try {
            DataBase.getEMF();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Imposible Conextarse Con El Servidor, Verifique Su Conexion A Internet","Error De Conexion", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
           
        
        Platform.runLater(()->{
            MainApp.closeSplash();
            Stages.createLogIn();
        });
        return null;
    }
}