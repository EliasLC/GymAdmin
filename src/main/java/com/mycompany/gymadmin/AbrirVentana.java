package com.mycompany.gymadmin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author elias
 */
public class AbrirVentana extends Task<Void> {

    String ruta, nomVentana;

    public AbrirVentana(String ruta, String nomVentana) {
        this.ruta = ruta;
        this.nomVentana = nomVentana;
    }
    
    
    @Override
    protected Void call() throws Exception {
             Platform.runLater(() -> {
            Parent root;
            try {
                root = (Parent) FXMLLoader.load(MainApp.class.getResource(ruta));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle(nomVentana);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/images/Logo.png")));
                stage.showAndWait();
                stage = null;
                System.gc();
            } catch (IOException ex) {
                Logger.getLogger(AbrirVentana.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return null;
    }
}