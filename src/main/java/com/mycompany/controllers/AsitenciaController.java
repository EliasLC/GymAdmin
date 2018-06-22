package com.mycompany.controllers;

import com.jfoenix.controls.JFXButton;
import com.mycompany.interacciondb.ModIAdm;
import com.mycompany.interacciondb.datos;
import com.mycompany.persistencia.DataBase;
import com.mycompany.interacciondb.tablaAsistencia;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author elias
 */
public class AsitenciaController implements Initializable {

  @FXML
    private AnchorPane Pane;

    @FXML
    private Label LNombre;

    @FXML
    private JFXButton BCerrar;

    @FXML
    private TableView<tablaAsistencia> TablaAsistencia;

    @FXML
    private TableColumn<tablaAsistencia, String> Fecha;
    
    @FXML private ProgressIndicator progTabla;

    @Override
    public void initialize(URL url,  ResourceBundle rb) {
        llenarTabla(); cerrarVentana(); LNombre.setText(datos.getNombre());
    }    
    
    private void cerrarVentana(){
        BCerrar.setOnAction((e)->{
            Pane.getScene().getWindow().hide();
        });
    }
    
    private void llenarTabla(){
        llenarTabla l = new llenarTabla(datos.getId());
        progTabla.setVisible(true);
        l.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent e)->{
            TablaAsistencia.setItems(l.getValue());
            Fecha.setCellValueFactory(new PropertyValueFactory("fecha"));
            progTabla.setVisible(false);
        });
        new Thread(l).start();
    }
 
    //Clase para obtneer los elementos de la tabla 
    private class llenarTabla extends Task<ObservableList<tablaAsistencia>>{
        private int idM;
        
        public llenarTabla(int id){
            this.idM = id;
        }
        
        @Override
        protected ObservableList<tablaAsistencia> call() throws Exception {
            return lista();
        }
        
        private ObservableList<tablaAsistencia> lista (){
            ObservableList<tablaAsistencia> re = null;
            try{ 
            EntityManager manager = DataBase.getEMF().createEntityManager();
             manager.getTransaction().begin();
             Query query = manager.createQuery("SELECT NEW com.mycompany.interacciondb.tablaAsistencia (a.fecha) FROM Asistencia a WHERE a.susId.susId = :id");
             query.setParameter("id", datos.getId());
             re = FXCollections.observableArrayList(query.getResultList());
             manager.getTransaction().commit();
             manager.close();
            }catch(Exception e){
                Logger.getLogger(AsitenciaController.class.getName()).log(Level.SEVERE, null, e);
            }
            return re;
        }
         /**
         * @return the idM
         */
        public int getIdM() {
            return idM;
        }

        /**
         * @param idM the idM to set
         */
        public void setIdM(int idM) {
            this.idM = idM;
        }
    }
}