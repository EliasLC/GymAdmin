package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author elias
 */
public class llenarTablaSuscriptores extends Task<ObservableList<TablaSuscriptores>> {

    @Override
    protected ObservableList<TablaSuscriptores> call() throws Exception {
        return lista();
    }
    
    private ObservableList<TablaSuscriptores> lista(){
        ObservableList<TablaSuscriptores> res = null;
        try{
            EntityManager manager = DataBase.getEMF().createEntityManager();
            manager.getTransaction().begin();
            Query result = manager.createQuery("SELECT NEW com.mycompany.interacciondb.TablaSuscriptores(a.susNom,a.sUSAPat, a.sUSAMat,a.sUSFechaNA,a.susEmail,b.colonia, b.manzana,b.lote,d.tsucNom,c.sucFfp)  FROM Suscriptor a, Direccion_SUS b,Suscripcion c,TipoSuc d WHERE a.susId = b.suscriptor.susId AND c.sucSusid = a.susId AND c.sucTsuc = d.tsucId AND c.sucFfp >= :final");
            result.setParameter("final", new Date());
            res = FXCollections.observableArrayList(result.getResultList());
            manager.getTransaction().commit();
            manager.close();
        }catch(Exception e){
            System.out.println("Error");
        }
        return res;
    }
    
}
