package com.mycompany.interacciondb;

import com.mycompany.persistencia.Administrador;
import com.mycompany.persistencia.DataBase;
import java.sql.Date;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;

/**
 *
 * @author elias
 */
public class InsertarAdministrador extends Task<Boolean> {

    private String nombre, appat,apmat,movil,fijo,email;
    private Date fecha;
    
    public InsertarAdministrador(String nombre, String appat,String apmat, String movil,String fijo, String email){
        this.nombre= nombre; this.appat=appat; this.apmat=apmat; this.movil= movil; this.fijo= fijo; this.email= email;
    }
    
    @Override
    protected Boolean call() throws Exception {
        boolean exito = true;
        System.out.println("nelelee");
        EntityManager em = DataBase.getEMF().createEntityManager();
        em.getTransaction().begin();
        Administrador r = new Administrador();
        r.setAdmNom(nombre);
        r.setAdmApat(appat);
        r.setAdmAmat(apmat);
        r.setAdmContra("1");
        r.setAdmTelm(movil);
        r.setAdmTelc(fijo);
        r.setAdmEmail(email);
        em.persist(r);
        System.out.println("nelelee");
        em.getTransaction().commit();
        em.close();
        return exito;
    }
    
}