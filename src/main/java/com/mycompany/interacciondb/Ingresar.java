package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
/**
 *
 * @author elias
 */
public class Ingresar extends Task<Boolean> {

    private String user,pass;
    
    public Ingresar (String user,String pass){
        this.user=user; this.pass= pass;
    }
    
    @Override
    protected Boolean call() {
        System.out.println("hilito");
        boolean exito = false;
        EntityManager manager = DataBase.getEMF().createEntityManager();
        
        Object result = manager.createNamedQuery("Administrador.findByAdmEmail").setParameter("admEmail", user ).getSingleResult();
        String res = (String) result;
        manager.close();
       
        if(res.equals(pass)){
            return true;
        }
       
        return exito;
    }
    
}