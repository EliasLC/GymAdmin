package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.Instructor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.Query;
/**
 * @author elias
 */
public class buscarInstruidos extends Task<ObservableList<TablaInstruidos>> {

    private String email;
    
    public buscarInstruidos(String email){
        this.email=email;
    }
    
     @Override
    protected ObservableList<TablaInstruidos> call() throws Exception {
       return buscar();
    }
    
    private ObservableList<TablaInstruidos> buscar(){
        ObservableList<TablaInstruidos> res = null;
        try{
            EntityManager em = DataBase.getEMF().createEntityManager();
            em.getTransaction().begin();
            Query result = em.createNamedQuery("Instructor.findByInsEmail");
            result.setParameter("insEmail", email);
            List<Instructor> in = result.getResultList();
            if(!in.isEmpty()){
                Query rest = em.createQuery("SELECT NEW com.mycompany.interacciondb.TablaInstruidos(c.susNom, c.sUSAPat, c.sUSAMat, c.sUSFechaNA, c.susTelm, c.sUSTELCelular, c.susEmail, b.fechainicio) FROM Instructor a, Instruidos b, Suscriptor c WHERE a.insId = :insId AND a.insId = b.primaryKey.instructor.insId AND c.susId = b.primaryKey.sus.susId");                                                          
                rest.setParameter("insId",in.get(0).getInsId());
                res= FXCollections.observableArrayList(rest.getResultList());
            }
            em.getTransaction().commit();
            em.close();
        }catch (Exception e){
             Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Error");
        }
        return res;
    }

   
    
}
