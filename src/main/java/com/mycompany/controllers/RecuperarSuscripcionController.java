package com.mycompany.controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.interacciondb.TablaSuscripciones;
import com.mycompany.interacciondb.habilitarSuscripcion;
import com.mycompany.interacciondb.llenarTablaSuscripciones;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author elias
 */
public class RecuperarSuscripcionController implements Initializable {

    @FXML private JFXTextField TBuscar;

    @FXML private TableView<TablaSuscripciones> tablaSuscripcion;

    @FXML private TableColumn<TablaSuscripciones, String> ColNombre,ColDes,ColAdm;

    @FXML private JFXButton BHabilitar;

    @FXML private ProgressIndicator progHab,progTabla;
    
    @FXML private AnchorPane pane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarTabla(); seleccionar(); habilitar();
    }    
    
    //Metodo para habilitar suscriptor 
    private void habilitar(){
        BHabilitar.setOnAction((e)->{
            BHabilitar.setDisable(true); TBuscar.setDisable(true); 
            if(tablaSuscripcion.getSelectionModel().getSelectedItem()!=null){
                progHab.setVisible(true);
                habilitarSuscripcion hs = new habilitarSuscripcion(tablaSuscripcion.getSelectionModel().getSelectedItem().getId());
                hs.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent er)->{
                    progHab.setVisible(true);
                    int res = hs.getValue();
                    
                    switch (res){
                        
                        case 0: Alertas.informacion("Reactivacion Exitosa", "El registro se ha realizado correctamente");
                                llenarTabla();
                                break;
                        case 1:
                            Alertas.error("Error de Activacion", "No fue posible realizar la reactivacion","Verifique su conexion a internet");
                                break;
                    }
                    progHab.setVisible(false);
                    TBuscar.setDisable(false); TBuscar.requestFocus();
                    tablaSuscripcion.getSelectionModel().clearSelection();
                });
                new Thread(hs).start();
            }
        });
    }
    
    //Metodo para llenarTabla
    private void llenarTabla(){
        llenarTablaSuscripciones lp = new llenarTablaSuscripciones(0);
        progTabla.setVisible(true);
        TBuscar.setDisable(true);
        lp.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent el)->{
            ObservableList<TablaSuscripciones> items= lp.getValue();
            System.out.println(items.size());
            tablaSuscripcion.setItems(items);
            ColNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            ColDes.setCellValueFactory(new PropertyValueFactory<>("descipcion"));
            ColAdm.setCellValueFactory(new PropertyValueFactory<>("administrador"));
            
            FilteredList<TablaSuscripciones> datos = new FilteredList <> (items, a -> true);
            TBuscar.setOnKeyReleased(e -> {
                TBuscar.textProperty().addListener((observable, oldValue, newValue) ->{
                    datos.setPredicate((Predicate <? super TablaSuscripciones>) ins -> {

                       if(newValue == null || newValue.isEmpty()){
                           return true;
                       }
                       String lower = newValue.toLowerCase();
                       
                       return ins.getNombre().toLowerCase().contains(lower);
                   });
                });
            
        });  
            SortedList<TablaSuscripciones> dataCam = new SortedList<>(datos);
            dataCam.comparatorProperty().bind(tablaSuscripcion.comparatorProperty());
            tablaSuscripcion.setItems(dataCam);
            TBuscar.setDisable(false); progTabla.setVisible(false);
            TBuscar.requestFocus();
        });
        new Thread(lp).start();
    }
    
    //Metodo para seleccionar
      private void seleccionar(){
         tablaSuscripcion.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TablaSuscripciones> 
                observable, TablaSuscripciones oldValue, TablaSuscripciones newValue) -> {
            
            if(newValue != null){
               TBuscar.setDisable(true); BHabilitar.setDisable(false);
            }
        });    
         
        pane.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e)->{
            tablaSuscripcion.getSelectionModel().clearSelection(); BHabilitar.setDisable(true);
            TBuscar.setDisable(false);
        });
    }//END
    
}
