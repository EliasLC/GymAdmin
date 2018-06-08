package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;

/**
 *
 * @author elias
 */
public class buscarAdmin extends Task<ObservableList<TablaAdministradores>> {

    @Override
    protected ObservableList<TablaAdministradores> call() throws Exception {
         ObservableList<TablaAdministradores> registros = FXCollections.observableArrayList();
         EntityManager manager = DataBase.getEMF().createEntityManager();
         manager.getTransaction().begin();
         List<Object[]> result = manager.createNamedQuery("Administrador.findAll").getResultList();
         
         result.forEach((i) -> {
             String nombre = (String) i[0];
             nombre+= " "+ (String) i[1]+" " +(String) i[2];
             registros.add(
                     new TablaAdministradores (nombre,(String) i[3],(String) i[4], (String) i[5])
             );
        });
        System.out.println(registros.size()); 
        manager.getTransaction().commit();
        manager.close();
        return registros;
    }
    
}
