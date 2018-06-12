package com.mycompany.gymadmin;

import com.mycompany.interacciondb.RecuperarContraseña;
import com.mycompany.persistencia.Administrador;
import com.mycompany.persistencia.DataBase;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author elias
 */
public class DeleteAdm extends Task<Integer>  {

    private String email;
    
    
    public DeleteAdm(String email){
        this.email=email;
    }
    
    @Override
    protected Integer call() throws Exception {
        return cambio();
    }
    
    /*Metodo para cambiar el status
        Resultado 0: No se encuentra el usuario en la base de datos
        Resultado 1: La Eliminacion resulto exitosa
        Resultado 2: Ha ocurrido un error
    */
    private int cambio(){
        int resultado = 0;
        try{
            EntityManager em = DataBase.getEMF().createEntityManager();
            em.getTransaction().begin();
            Query result= em.createNamedQuery("Administrador.findByAdmEmail");
            result.setParameter("admEmail", email);
            List<Administrador> res=  result.getResultList();
            if(res.size()>0){
                Administrador adm = res.get(0);
                adm.setAdmStatus(0);
                resultado =1;
            }
            em.getTransaction().commit();
            em.close();   
        } catch(Exception e){
            Logger.getLogger(RecuperarContraseña.class.getName()).log(Level.SEVERE, null, e);
            resultado= 2;
        }
        return resultado;
    }
    
}
