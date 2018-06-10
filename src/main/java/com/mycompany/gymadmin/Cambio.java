package com.mycompany.gymadmin;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 *
 * @author elias
 */
public class Cambio extends Task<Void> {

    @Override
    protected Void call() throws Exception {
            Platform.runLater(()->{
                Stages.cerrarCuerpo();
                Stages.createLogIn();
            });
        return null;
    }
    
    
}
