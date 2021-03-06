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
public class llenarTablaCostos extends Task<ObservableList<TablaPrecios>>{

    private int id;
    
    public llenarTablaCostos(int id){
        this.id=id;
    }
    
    @Override
    protected ObservableList<TablaPrecios> call() throws Exception {
        return lista();
    }
    
    private ObservableList<TablaPrecios> lista(){
        ObservableList<TablaPrecios> res = null;
        try{
            EntityManager manager = DataBase.getEMF().createEntityManager();
            manager.getTransaction().begin();
            Query result = manager.createQuery("SELECT NEW com.mycompany.interacciondb.TablaPrecios(p.ptDuracion, p.ptPrecio) FROM PeriodoTiposuc p WHERE p.tsucId.tsucId = :tsucId");
            result.setParameter("tsucId", id);
            res= FXCollections.observableArrayList(result.getResultList());
            manager.getTransaction().commit();
            manager.close();
        }catch(Exception e){
            Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, e);
        }
        return res;
        
    }
    
}
