package com.mycompany.controllers;
import com.jfoenix.controls.JFXButton;
import com.mycompany.interacciondb.InfoSuscriptores;
import com.mycompany.interacciondb.datos;
import com.mycompany.interacciondb.extra;
import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.Instructor;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javax.persistence.EntityManager;
import javax.persistence.Query;
/**
 * FXML Controller class
 *
 * @author elias
 */
public class MasSuscriptoresController implements Initializable {

    @FXML private AnchorPane pane;

    @FXML
    private Label Nombre,edad, colonia, calle,ml,celular,Fijo, email,inst;

    @FXML
    private JFXButton cancelar;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cerrar(); 
       hilo();
    }    
    
    //Metodo para cerrar la ventana
    private void cerrar(){
        cancelar.setOnAction((e)->{
            pane.getScene().getWindow().hide();
        });
    }
     
    private void hilo(){
         info i = new info();
         i.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent e)->{
             InfoSuscriptores in = i.getValue();
             Nombre.setText(extra.getNombre());
             email.setText(extra.getEmail());
             edad.setText(extra.getEdad());
             colonia.setText(extra.getColonia());
             ml.setText(extra.getMl());
             celular.setText(" "+in.getCelular());
             Fijo.setText(" "+in.getFijo());
             calle.setText(in.getCalle());
             inst.setText(in.getInstructor());
         });
        new Thread(i).start();
    }
    
    
    //Clase para obtener la info
    private class info extends Task<InfoSuscriptores>{

        @Override
        protected InfoSuscriptores call() throws Exception {
         
            return obtener();
        }
    
        private InfoSuscriptores obtener(){
            InfoSuscriptores ip = null;
            try{
                EntityManager manager = DataBase.getEMF().createEntityManager();
                manager.getTransaction().begin();
                Query result = manager.createQuery("SELECT NEW com.mycompany.interacciondb.InfoSuscriptores(b.calle,a.sUSTELCelular,a.susTelm) FROM Suscriptor a, Direccion_SUS b WHERE a.susId = b.suscriptor.susId AND a.susId= :id");
                result.setParameter("id", datos.getId());
                List<InfoSuscriptores> info = result.getResultList();
                if(info.size()>0){
                    ip = info.get(0);
                }
                Query re = manager.createQuery("SELECT a FROM Instructor a, Suscriptor b, Instruidos c  WHERE a.insId = c.primaryKey.instructor.insId AND b.susId = c.primaryKey.sus.susId AND b.susId= :id");
                re.setParameter("id", datos.getId());
                List<Instructor> io = re.getResultList();
                if(io.size()>0){
                    Instructor il = io.get(0);
                    ip.setInstructor(il.getInsNom()+" "+il.getInsApat()+" "+il.getInsAm());
                    
                }
                
                manager.getTransaction().commit();
                manager.close();
            }catch(Exception e){
            
            }
            return ip;
        }
        
        
   }
}
