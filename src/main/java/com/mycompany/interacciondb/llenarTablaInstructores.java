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
public class llenarTablaInstructores extends Task<ObservableList<TablaInstructores>> {

    @Override
    protected ObservableList<TablaInstructores> call() throws Exception {
       ObservableList<TablaInstructores> res = null;
       return obtenerLista(res);
    }
    
    private ObservableList<TablaInstructores> obtenerLista(ObservableList<TablaInstructores> res ){
        try{
            EntityManager manager = DataBase.getEMF().createEntityManager();
            manager.getTransaction().begin();
            Query result= manager.createNamedQuery("Instructor.findTablaInstructor");
            result.setParameter("insStatus", 1);
            res = FXCollections.observableArrayList(result.getResultList());
            manager.getTransaction().commit();
            manager.close(); 
        }catch(Exception e){
            Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Error");
        }
        return res;
    }
    
}
