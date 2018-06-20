package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.PeriodoTiposuc;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.Query;
/**
 * @author elias
 */
public class EliminarCosto extends Task<Integer> {
    
    
  
    
    private int id;
    private String duracion;

    @Override
    protected Integer call() throws Exception {
       return eliminar();
    }
    
    
    //Metod para eliminar 
    private int eliminar(){
        int res=1;
        try{
             EntityManager em = DataBase.getEMF().createEntityManager();
             em.getTransaction().begin();
             Query query = em.createQuery("SELECT p FROM PeriodoTiposuc p WHERE p.tsucId.tsucId = :id AND p.ptDuracion = :dur");
             query.setParameter("id", id);
             query.setParameter("dur", duracion);
             List<PeriodoTiposuc> rer = query.getResultList();
             if(rer.size()>0){
                 PeriodoTiposuc e = rer.get(0);
                 em.remove(e);
                 res=0;
             }
             em.getTransaction().commit();
             em.close();
        }catch(Exception e){
            Logger.getLogger(EliminarCosto.class.getName()).log(Level.SEVERE, null, e);
            res=2;
        }
        return res;
    }
    
      public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
}
