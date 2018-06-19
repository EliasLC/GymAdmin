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
public class llenarTablaTransaccionesRecep extends Task<ObservableList<TablaTrans>> {

    @Override
    protected ObservableList<TablaTrans> call() throws Exception {
        return lista();
    }
    
    //Metodo para obtener los elementos
    private ObservableList<TablaTrans> lista(){
        ObservableList<TablaTrans> lista = null;
        try{
             EntityManager manager = DataBase.getEMF().createEntityManager();
             manager.getTransaction().begin();
             Query query = manager.createQuery("SELECT NEW com.mycompany.interacciondb.TablaTrans(t.trFecha, t.trTipo, t.trImp) FROM TransaccionesRecep t WHERE t.trRecid.recId= :id");
             query.setParameter("id", datos.getId());
             lista = FXCollections.observableArrayList(query.getResultList());
             manager.getTransaction().commit();
             manager.close();
        }catch(Exception e){
            Logger.getLogger(llenarTablaTransaccionesRecep.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
}
