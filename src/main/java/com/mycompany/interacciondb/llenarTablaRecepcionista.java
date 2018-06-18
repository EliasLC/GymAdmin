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
public class llenarTablaRecepcionista extends Task<ObservableList<TablaRecepcionista>> {

    @Override
    protected ObservableList<TablaRecepcionista> call() throws Exception {
        return lista();
    }
    
    //Metodo para obtener lista 
    private ObservableList<TablaRecepcionista> lista(){
        ObservableList<TablaRecepcionista> lista =null;
        try{
            EntityManager manager = DataBase.getEMF().createEntityManager();
            manager.getTransaction().begin();
            Query result = manager.createQuery("SELECT NEW com.mycompany.interacciondb.TablaRecepcionista (a.recNom,a.recApat,a.recAmat,a.recEmail,a.recFna,a.recTelc,a.recTelm,b.colonia,b.manzana,b.lote) FROM Recepcionista a, Direccion_REC b WHERE a.recId = b.recepcionista.recId AND a.recStatus= :status");
            result.setParameter("status", 1);
            lista= FXCollections.observableArrayList(result.getResultList());
            manager.getTransaction().commit();
            manager.close(); 
        }catch(Exception e){
             Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
    
}
