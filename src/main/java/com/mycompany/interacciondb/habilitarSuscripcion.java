package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.TipoSuc;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;

/**
 * @author elias
 */
public class habilitarSuscripcion extends Task<Integer> {

    private int id;
    
    public habilitarSuscripcion(int id){
        this.id=id;
    }
    
    @Override
    protected Integer call() throws Exception {
        return habilitar();
    }
    
    private int habilitar(){
        int r =0;
        try{
            EntityManager em = DataBase.getEMF().createEntityManager();
            em.getTransaction().begin();
            TipoSuc t = em.find(TipoSuc.class, id);
            t.setTsusStatus(1);
            em.getTransaction().commit();
            em.close();
        }catch(Exception e){
            r=1;
        }
        return r;
    }
    
    
}