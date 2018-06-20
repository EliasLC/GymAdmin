package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.PeriodoTiposuc;
import com.mycompany.persistencia.TipoSuc;
import java.util.List;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author elias
 */
public class InsertarPrecio extends Task<Integer> {



    private int idTipo;
    private String duracion;
    private float precio;
    
    public InsertarPrecio(int idTipo,String duracion, float precio){
        this.idTipo= idTipo; this.duracion= duracion; this.precio= precio;
    }
    
    @Override
    protected Integer call() throws Exception {
        return insertar();
    }


    //Metodo para insertar
    public int insertar(){
        int res =1;
        try{
            EntityManager em = DataBase.getEMF().createEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT p FROM PeriodoTiposuc p WHERE p.tsucId.tsucId = :id AND p.ptDuracion = :dur");
            query.setParameter("id", idTipo); 
            query.setParameter("dur", duracion);
            List<PeriodoTiposuc> per = query.getResultList();
            System.out.println(per.size());
            if(per.isEmpty()){
                TipoSuc ts = em.find(TipoSuc.class, idTipo);
                PeriodoTiposuc nuevo = new PeriodoTiposuc();
                nuevo.setTsucId(ts);
                nuevo.setPtDuracion(duracion);
                nuevo.setPtPrecio(precio);
                em.persist(nuevo);
                res =0;
            }
            em.getTransaction().commit();
            em.close();
        } catch(Exception e){
            res=2;
        }
        return res;
    
    }
    
    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }


    public String getDuracion() {
        return duracion;
    }


    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public float getPrecio() {
        return precio;
    }

  
    public void setPrecio(float precio) {
        this.precio = precio;
    }

}
