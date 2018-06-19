package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.Recepcionista;
import java.util.List;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author elias
 */
public class EliminarRecepcionista extends Task<Integer> {

    private String email;
    
    public EliminarRecepcionista(String email){
        this.email= email;
    }
    
    @Override
    protected Integer call() throws Exception {
        return eliminar();
    }
    
    //Metodo para eliminar un dato
    private Integer eliminar(){
        int res=1;
        try{
            EntityManager manager = DataBase.getEMF().createEntityManager();
            manager.getTransaction().begin();
            Query query = manager.createQuery("SELECT r FROM Recepcionista r WHERE r.recEmail = :email AND r.recStatus = :status");
            query.setParameter("email", email);
            query.setParameter("status", 1);
            List<Recepcionista> rp = query.getResultList();
            if(rp.size()>0){
                Recepcionista r = rp.get(0);
                r.setRecStatus(0);
                res =0;
            }
            manager.getTransaction().commit();
            manager.close();
        }catch(Exception e){
            res =2;
        }
        return res;
    }
    
}