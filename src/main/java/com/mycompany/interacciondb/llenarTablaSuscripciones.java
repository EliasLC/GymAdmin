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
public class llenarTablaSuscripciones extends Task<ObservableList<TablaSuscripciones>> {
    
    private int status;
    
    public llenarTablaSuscripciones(int status){
        this.status=status;
    }
    
    @Override
    protected ObservableList<TablaSuscripciones> call() throws Exception {
       return elementos();
    }
    
    //Metodo para obtener todas las suscripciones
    private ObservableList<TablaSuscripciones> elementos(){
        ObservableList<TablaSuscripciones> res = null;
        try{
            EntityManager manager = DataBase.getEMF().createEntityManager();
            manager.getTransaction().begin();
            Query q= manager.createNamedQuery("TipoSuc.tablaSusc");
            q.setParameter("tsusStatus",status);
            res = FXCollections.observableArrayList(q.getResultList());
            manager.getTransaction().commit();
            manager.close();
        }catch(Exception e){
            Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, e);
        }
        return res;
    }
}
