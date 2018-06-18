package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.TipoSuc;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author elias
 */
public class ModificarSuscripciones extends Task<Integer> {

    private int id;
    private String nombre,desc;
    
    public ModificarSuscripciones(int id, String nombre, String desc){
        this.id= id; this.nombre= nombre; this.desc= desc;
    }
    
    @Override
    protected Integer call() throws Exception {
       return modificar();
    }
    
    /*Metodo para modificar datos de los tipos de suscripciones
        Retorno 0 = La modificacion fue exitosa
        Retorno 1 = No se encontro el registro en la base de datos
        Retorno 2 = Se presento un error en la conexion con la base de datos
    */
    private int modificar(){
        int res =1;
        try{
            EntityManager em = DataBase.getEMF().createEntityManager();
            em.getTransaction().begin();
            Query result = em.createNamedQuery("TipoSuc.findByTsucId");
            result.setParameter("tsucId", id);
            List<TipoSuc> lis = result.getResultList();
            if(!lis.isEmpty()){
                lis.get(0).setTsucNom(nombre);
                lis.get(0).setTsusDesc(desc);
                res=0;
            }
            em.getTransaction().commit();
            em.close();
        }catch(Exception e){
            Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, e);
            res= 2;
        }
        return res;
    }
    
    
}
