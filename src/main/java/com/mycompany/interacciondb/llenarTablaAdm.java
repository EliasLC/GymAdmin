
package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author elias
 */
public class llenarTablaAdm extends Task<ObservableList<TablaAdministradores>>{

    @Override
    protected ObservableList<TablaAdministradores> call() throws Exception {
        ObservableList<TablaAdministradores> res = null;
        return obtenerAdm(res);
    }
    
    private ObservableList<TablaAdministradores> obtenerAdm(ObservableList<TablaAdministradores> res){
        
        try{
            EntityManager manager = DataBase.getEMF().createEntityManager();
            manager.getTransaction().begin();
            Query result= manager.createNamedQuery("Administrador.findTablaAdmin");
            result.setParameter("admStatus", 1);
            res = FXCollections.observableArrayList(result.getResultList());
            manager.getTransaction().commit();
            manager.close();
        } catch(Exception e){
            Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Error");
        }
            return res;
        }
    
}
