package com.mycompany.interacciondb;

import com.mycompany.persistencia.Administrador;
import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.TipoSuc;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;

/**
 *
 * @author elias
 */
public class InsertarSuscripcion extends Task<Integer> {

    private int idAdm; 
    private String nomb,desc;

    public InsertarSuscripcion(int idAdm, String nomb,String desc){
        this.idAdm = idAdm; this.nomb= nomb; this.desc= desc;
    }
    
    @Override
    protected Integer call() throws Exception {
        return result();
    }
    
    private int result(){
        int res =0;
        try{
            EntityManager em = DataBase.getEMF().createEntityManager();
            em.getTransaction().begin();
            TipoSuc ts = new TipoSuc();
            ts.setTsucNom(nomb); ts.setTsusDesc(desc);
            ts.setTsusStatus(1);
            Administrador ad = new Administrador();
            ad.setAdmId(idAdm); 
            ts.setTsucAdmid(ad);
            em.persist(ts);
            em.getTransaction().commit();
            em.close();
        }catch(Exception e){
            res=1;
        }
        return res;
    }
    
}
