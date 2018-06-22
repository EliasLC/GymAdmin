package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.Recepcionista;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author elias
 */
public class ModificarRecep extends Task<Integer> {
    
    private String email,nom,materno,paterno,telmovil,telfijo,colonia,manzana,lote,nacimiento;
    private int id;        
    
    public ModificarRecep(int id,String nom,String paterno,String materno,String telmovil,String telfijo,
        String email,String colonia,String manzana,String lote,String nacimiento){
        
        this.nom=nom; this.paterno=paterno; this.materno=materno; this.telmovil= telmovil; 
        this.telfijo=telfijo; this.email= email; this.colonia=colonia; this.manzana=manzana;
        this.lote=lote; this.nacimiento= nacimiento; this.id=id;
        
    }
    
    @Override
    protected Integer call() throws Exception {
       return cambio();
    }
    
    /*Metodo para cambiar los datos
      Retorno 1: Se ha realizado con exito el cambio
      Retorno 2: No se encontro al usuario
      Retorno 3: No se pudo conectar con el servidor
    */

    private int cambio(){
        int res=2;
        try{
            EntityManager manager = DataBase.getEMF().createEntityManager();
            manager.getTransaction().begin();
            Query result = manager.createQuery("SELECT r FROM Recepcionista r WHERE r.recEmail = :email AND r.recStatus = :status");
            result.setParameter("email",email);
            result.setParameter("status", 1);
            List<Recepcionista> rep = result.getResultList();
            if(rep.size()>0){
                Recepcionista r = rep.get(0);
                r.setRecNom(nom); r.setRecApat(paterno); r.setRecAmat(materno);
                r.setRecEmail(email); r.setRecFna(nacimiento()); r.setRecTelc(telfijo);
                r.setRecTelm(telmovil);  r.setRecStatus(1);
                r.getDireccionrec().setColonia(colonia); r.getDireccionrec().setManzana(manzana);
                r.getDireccionrec().setLote(lote);
                res=1;
            }
            manager.getTransaction().commit();
            manager.close();
        }catch(Exception e){
            return 3;
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
