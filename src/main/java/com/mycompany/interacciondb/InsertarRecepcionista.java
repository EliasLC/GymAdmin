package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.Direccion_REC;
import com.mycompany.persistencia.Recepcionista;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author elias
 */
public class InsertarRecepcionista extends Task<Integer> {

    private String nombre,paterno,materno,movil,fijo,nacimiento, email,colonia,manzana,lote;
    private Date fecha;
    
    public InsertarRecepcionista(String nombre,String paterno,String materno,String movil, String fijo,
            String nacimiento, String email, String colonia,String manzana,String lote){
        this.nombre= nombre; this.paterno= paterno; this.materno=materno; this.movil= movil; this.fijo= fijo;
        this.nacimiento= nacimiento; this.email= email; this.colonia= colonia; this.manzana= manzana; 
        this.lote= lote;
    }
    
    @Override
    protected Integer call() throws Exception {
        return ingresar();
    }
    
    
    private int ingresar(){
        int res =1;
        try{
            EntityManager em = DataBase.getEMF().createEntityManager();
            em.getTransaction().begin();
            Query result= em.createNamedQuery("Recepcionista.findByRecEmail");
            result.setParameter("recEmail", email);
            if(result.getResultList().isEmpty()){
                Recepcionista r = new Recepcionista();
                r.setRecNom(nombre); r.setRecApat(paterno);
                r.setRecAmat(materno); r.setRecEmail(email);
                r.setRecContra("0101010"); r.setRecFna(nacimiento());
                r.setRecTelm(movil); r.setRecTelc(fijo);
                r.setRecStatus(1);
                Direccion_REC dr = new Direccion_REC();
                dr.setRecepcionista(r); dr.setManzana(manzana);
                dr.setColonia(colonia); dr.setLote(lote);
                em.persist(r); em.persist(dr);
                res=0;
            }
            em.getTransaction().commit();
            em.close();
        }catch(Exception e){
            res=2;
        }
        
    
        return res;
    }
    
    
     //Crear fecha
    private Date nacimiento(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(nacimiento);
        } catch (ParseException ex) {
            Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }
}
