package com.mycompany.interacciondb;

import com.mycompany.persistencia.Administrador;
import com.mycompany.persistencia.DataBase;
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
 *
 * @author elias
 */
public class ModIAdm extends Task<Integer> {
    private int Id; 
    private String nombre, paterno,materno, fijo,movil, email, fecha;
    private Date date;
    
    public ModIAdm(int id, String nombre, String paterno, String materno, String fijo,String movil, 
            String email, String fecha){
        this.Id=id; this.nombre= nombre; this.paterno=paterno; this.materno= materno;
        this.fijo= fijo; this.movil= movil; this.email= email; this.fecha= fecha;
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
        int result =2;
        try{
            EntityManager manager = DataBase.getEMF().createEntityManager();
            manager.getTransaction().begin();
            Query res = manager.createNamedQuery("Administrador.findByAdmId");
            res.setParameter("admId",Id);
            List<Administrador> adm = res.getResultList();
            if(adm.size()>0){
                Administrador a = (Administrador) adm.get(0);
                a.setAdmNom(nombre); a.setAdmApat(paterno);
                a.setAdmAmat(materno); a.setAdmTelm(movil); 
                a.setAdmTelc(fijo); a.setAdmEmail(email);
                a.setAdmFna(nacimiento());
                result=1;
            }
            manager.getTransaction().commit();
            manager.close();
        }catch(Exception e){
            Logger.getLogger(RecuperarContraseña.class.getName()).log(Level.SEVERE, null, e);
            return 3;
        }
        return result;
    }
    
    //Crear fecha
    private Date nacimiento(){
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }
}
