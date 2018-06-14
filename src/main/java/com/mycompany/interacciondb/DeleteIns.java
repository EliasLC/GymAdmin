package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.Instructor;
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
public class DeleteIns extends Task<Integer> {

    private String email;
    
    public DeleteIns(String email){
        this.email= email;
    }
    
    @Override
    protected Integer call() throws Exception {
        return eliminar();
    }
    
    /*Metodo para cambiar el status
        Resultado 0: No se encuentra el usuario en la base de datos
        Resultado 1: La Eliminacion resulto exitosa
        Resultado 2: Ha ocurrido un error
    */
    private Integer eliminar(){
        int resultado =0;
        try{
            EntityManager em = DataBase.getEMF().createEntityManager();
            em.getTransaction().begin();
            Query result = em.createNamedQuery("Instructor.findByInsEmail");
            result.setParameter("insEmail", email);
            List<Instructor> lista = result.getResultList();
            if(lista.size()>0){
                lista.get(0).setInsStatus(0);
                resultado=1;
            }
            em.getTransaction().commit();
            em.close();
        }catch(Exception e){
            Logger.getLogger(RecuperarContraseña.class.getName()).log(Level.SEVERE, null, e);
            resultado= 2;
        }
        return resultado;
    }
    
}
