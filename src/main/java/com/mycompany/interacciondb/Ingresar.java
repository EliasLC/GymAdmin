package com.mycompany.interacciondb;

import com.mycompany.gymadmin.Datos;
import com.mycompany.persistencia.DataBase;
import java.util.List;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.Query;
/**
 *
 * @author elias
 */
public class Ingresar extends Task<Integer> {

    private String user,pass;
    
    public Ingresar (String user,String pass){
        this.user=user; this.pass= pass;
    }
    
    
    /*Metodo para obtener la contraseña verificar los datos de ingreso en el log-in.
      Este retornara 3 tipos posibles de valores enteros; 0 si se logueo correctamente el usuario,
      1 si el usuario ingresado no existe y 2 si la contraseña es erronea.
    */
    @Override
    protected Integer call() {
        System.out.println("hilito");
        int exito = 0;
        
        EntityManager manager = DataBase.getEMF().createEntityManager();
        Query result;
        result = manager.createNamedQuery("Administrador.findIn");
        result.setParameter("user",user);
        List<Ingreso>  re = result.getResultList();
        manager.close();
       
        if(re.isEmpty()){
            return 1;
        } else if(!re.get(0).getAdmContra().equals(pass)){
            
            return 2;
        } 
        Datos.setIngreso(re.get(0));
        return exito;
    }
    
}