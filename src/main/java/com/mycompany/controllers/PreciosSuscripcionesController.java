package com.mycompany.controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.interacciondb.ModIAdm;
import com.mycompany.interacciondb.ModPrecios;
import com.mycompany.interacciondb.TablaPrecios;
import com.mycompany.interacciondb.datos;
import com.mycompany.interacciondb.llenarTablaCostos;
import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.Suscriptor;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.persistence.EntityManager;
import javax.persistence.Query;
/**
 * FXML Controller class
 * @author elias
 */
public class PreciosSuscripcionesController implements Initializable {

    @FXML private Label LSuscripcion,LNumero;

    @FXML private TableView<TablaPrecios> tablaPrecio;

    @FXML private TableColumn<TablaPrecios, String> ColPTiempo,ColPrecio;

    @FXML private JFXButton BEliminar, BAgregar, BModificar,BActualizar;
    
    @FXML private JFXComboBox<String> ComPTiempo;
   
    @FXML private ProgressIndicator progTran, progTabla;

    @FXML private AnchorPane pane;
    
    @FXML private JFXTextField TCosto;
    
    private double viejo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ObservableList<String> opciones = FXCollections.observableArrayList();
       opciones.add("Semana"); opciones.add("2 Semanas"); opciones.add("Mes");
       opciones.add("3 Meses"); opciones.add("6 meses"); opciones.add("Año");
       ComPTiempo.setItems(opciones); LSuscripcion.setText(LSuscripcion.getText()+" "+datos.getNombre());
       llenar(); numero(); seleccionar(); actualizar(); modificar();
    }  
    
    //Metodo para modificar datos
    private void modificar(){
        BModificar.setOnAction((e)->{
            
            if(ComPTiempo.getValue()==null||TCosto.getText().equals("")){
                Alertas.error("Error de ingreso", "", "Se encuentran campos vacios");
            } else{
                BModificar.setDisable(true); BEliminar.setDisable(true);
                bloquear(true); progTabla.setVisible(false); progTran.setVisible(true); 
                
                ModPrecios mp = new ModPrecios(datos.getId(), ComPTiempo.getSelectionModel().getSelectedItem(), viejo,Double.valueOf(TCosto.getText()));
                mp.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent ea)->{
                    int res = mp.getValue();
                    
                    switch(res){
                        case 0: Alertas.informacion("Modificacion Exitoso", "El registro se ha modificado correctamente");
                                llenar(); 
                            break;
                        case 1: Alertas.error("Error de registro", "Registro", "Registro previamente eliminado");  
                            llenar(); 
                            break;
                        case 2: Alertas.error("Error de conexion", "Error de usuario", "Verifique su conexion a internet");  
                            break;
                    }
                    
                bloquear(false); progTran.setVisible(false);
                });
                new Thread(mp).start();
            }
            
        });
    }
    
    
    //Metodo para conocer el numero
    private void numero(){
        Numero n = new Numero();
        n.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent e)->{
            LNumero.setText(LNumero.getText()+" "+String.valueOf(n.getValue()));
        });
        new Thread(n).start();
    }
    
    //Metodo para llenar la tabla
    private void llenar(){
        bloquear(true);
        llenarTablaCostos lc = new llenarTablaCostos(datos.getId());
        
        lc.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent e)->{
            tablaPrecio.setItems(lc.getValue());
            ColPTiempo.setCellValueFactory(new PropertyValueFactory<>("duracion"));
            ColPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            bloquear(false);
        });
        new Thread(lc).start();
    }
    
    
    //Metodo para actualizar la tabla 
    private void actualizar(){
        BActualizar.setOnAction((e)->{
            llenar();
        });
    }
    
    //Metodo para bloquear
    private void bloquear(boolean input){
        ComPTiempo.setDisable(input);  BAgregar.setDisable(input);
       BActualizar.setDisable(input); tablaPrecio.setDisable(input);
        progTabla.setVisible(input);
    }
    
    //Metodo para seleccionar en la tabla
    private void seleccionar(){
        tablaPrecio.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TablaPrecios> 
                observable, TablaPrecios oldValue, TablaPrecios newValue) -> {
            
            if(newValue!=null){
                BEliminar.setDisable(false); BModificar.setDisable(false);
                BAgregar.setDisable(true);
                viejo= Double.valueOf(tablaPrecio.getSelectionModel().getSelectedItem().getPrecio());
                TCosto.setText(tablaPrecio.getSelectionModel().getSelectedItem().getPrecio());
                ComPTiempo.setValue(tablaPrecio.getSelectionModel().getSelectedItem().getDuracion());
            }
        });
        
        pane.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e)->{
            BEliminar.setDisable(true); BModificar.setDisable(true);
            BAgregar.setDisable(false);
        });
    }
    
    
    //Clase para buscar el numero de suscriptores
    private class Numero extends Task<Integer>{

        @Override
        protected Integer call() throws Exception {
            return num();
        }
        
        //Metodo para obtener el numero de suscripciones que tiene un tippo de suscipciones
        private int num(){
            int res =0;
            try{
                EntityManager manager = DataBase.getEMF().createEntityManager();
                manager.getTransaction().begin();
                Query result = manager.createQuery("SELECT s FROM  Suscripcion s WHERE s.sucFfp >= :fecha AND s.sucTsuc.tsucId = :id ");
                result.setParameter("fecha", new Date());
                result.setParameter("id", datos.getId());
                List<Suscriptor> sus =  result.getResultList();
                res = sus.size();
                manager.getTransaction().commit();
                manager.close();
            }catch(Exception e){
                Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, e);
            }
            return res;
        }
        
    }
}
