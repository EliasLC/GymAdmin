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
public class ModPrecios extends Task<Integer> {

    private int id;
    private String periodo;
    private float costo;
    
    public ModPrecios(int id, String periodo,float nuevocosto){
        this.id=id; this.periodo=periodo;
        this.costo=nuevocosto; 
    }
    
        
    @Override
    protected Integer call() throws Exception {
        return resultado();
    }
    
    
    /*Metodo para modificar registro
        0: la actualizacion fue exitosa
        1: No se encontro el registro en la base de datos
        2: Error en la conexion con la base de datos 
    */
    private int resultado(){
        int res=1;
        try{
            EntityManager manager = DataBase.getEMF().createEntityManager();
            manager.getTransaction().begin();
            Query result = manager.createQuery("SELECT p FROM PeriodoTiposuc p WHERE p.ptDuracion = :dur AND p.tsucId.tsucId = :id ");
            result.setParameter("id", id);
            result.setParameter("dur", periodo);
            List<PeriodoTiposuc> p = result.getResultList();  
            if(p.size()>0){
                PeriodoTiposuc er = p.get(0);
                er.setPtPrecio(costo);
                res=0;
            }
            manager.getTransaction().commit();
            manager.close();
            
        }catch(Exception e){
            Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, e);
            res =2;
        }
            return res;
    }
    
}
