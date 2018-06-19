package com.mycompany.controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.AbrirVentana;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.gymadmin.Datos;
import com.mycompany.interacciondb.EliminarTipoSus;
import com.mycompany.interacciondb.InsertarSuscripcion;
import com.mycompany.interacciondb.ModificarSuscripciones;
import com.mycompany.interacciondb.TablaSuscripciones;
import com.mycompany.interacciondb.datos;
import com.mycompany.interacciondb.llenarTablaSuscripciones;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 * @author elias
 */
public class AdminsuscripcionesController implements Initializable {

    @FXML TableView<TablaSuscripciones> tablaSuscripcion;
    
    @FXML private TableColumn<TablaSuscripciones,String> ColNombre,ColDes, ColAdm;

    @FXML private JFXButton BActualizar, BEliminar,BAgregar,BModificar,BPrecios,BHab;
    
    @FXML private JFXTextField TBuscar, TNombre;
    
    @FXML private JFXTextArea TADesc;

    @FXML private ProgressIndicator progTabla, progTrans;
    
    @FXML private AnchorPane pane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarTabla(); seleccionar(); modificar();
        actualizarTabla(); eliminar(); insertar();
        recuperarSuc(); costos();   
    }    
    
   
    //Metodo para llenar la tabla
    private void llenarTabla(){
        llenarTablaSuscripciones lp = new llenarTablaSuscripciones(1);
        progTabla.setVisible(true);
        bloquear(true);
        lp.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent el)->{
            ObservableList<TablaSuscripciones> items= lp.getValue();
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
            bloquear(false);
            progTabla.setVisible(false);
        });
        new Thread(lp).start();
        TNombre.requestFocus();
    }
    
    //Metodo para bloquear y desbloquar elementos
    private void bloquear(boolean input){
        BAgregar.setDisable(input); 
        BActualizar.setDisable(input); TBuscar.setDisable(input);
        TADesc.setDisable(input); TNombre.setDisable(input);
    }
    
    //Metodo para insertar en la tabla
    private void insertar(){
        BAgregar.setOnAction((ActionEvent e)->{
            if(TNombre.getText().equals("")||TADesc.getText().equals("")){
                Alertas.error("Error de ingreso", "", "Se encuentran campos vacios");
            } else{
                bloquear(true); progTrans.setVisible(true);
                InsertarSuscripcion is = new InsertarSuscripcion(Datos.getDatos().getId(),TNombre.getText()
                ,TADesc.getText());
                
                is.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent event) -> {
                    int result = is.getValue();
                    
                    switch(result){
                        
                        case 0: Alertas.informacion("Inserción", "El registro se ha realizado correctamente");
                                TNombre.setText(""); TADesc.setText("");
                                llenarTabla();
                            break;
                        
                        case 1: Alertas.error("Error de conexion", "Error de servidor", "Imposible conectar con el servidor verifique su conexion a Internet");
                            break;
                    }
                    bloquear(false); progTrans.setVisible(false);
                    TBuscar.requestFocus();
                });
                new Thread(is).start();
            }
        });
    }
    
    //Metodo para seleccionar un elemento de la tabla
    private void seleccionar(){
         tablaSuscripcion.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TablaSuscripciones> 
                observable, TablaSuscripciones oldValue, TablaSuscripciones newValue) -> {
            
            if(newValue != null){
               BAgregar.setDisable(true); BActualizar.setDisable(true); TBuscar.setDisable(true);
               BEliminar.setDisable(false); BPrecios.setDisable(false); BModificar.setDisable(false);
               BHab.setDisable(true);
               TNombre.setText(tablaSuscripcion.getSelectionModel().getSelectedItem().getNombre());
               TADesc.setText(tablaSuscripcion.getSelectionModel().getSelectedItem().getDescipcion());
            }
        });    
         
        pane.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e)->{
            BAgregar.setDisable(false); BActualizar.setDisable(false); TBuscar.setDisable(false);
            BEliminar.setDisable(true); BPrecios.setDisable(true); TNombre.setText("");
            BModificar.setDisable(true); BHab.setDisable(false);
            TADesc.setText(""); tablaSuscripcion.getSelectionModel().clearSelection();
        });
    }//END
    
    //Metodo para modificar datos de un tipo de suscripcion
    private void modificar(){
        BModificar.setOnAction((e)->{
           
            if(TNombre.getText().equals("")||TADesc.getText().equals("")){
                 Alertas.error("Error de ingreso", "", "Se encuentran campos vacios");
            } else{
                 bloquear(true); BEliminar.setDisable(true); BModificar.setDisable(true);
                 progTrans.setVisible(true);
                 ModificarSuscripciones ms = new ModificarSuscripciones(tablaSuscripcion.getSelectionModel().getSelectedItem().getId(),
                         TNombre.getText(),TADesc.getText());
                
                        
               ms.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent event) -> {
                   int result = ms.getValue();
                   switch (result) {
                       case 0:
                           Alertas.informacion("Modificacion de datos", "Los datos se han cambiado correctamente");
                           TNombre.setText(""); TADesc.setText("");
                           llenarTabla();
                           break;
                       case 1:
                           System.out.println("No se encontro");
                           Alertas.error("Error modificacion", "Registro no encontrado", "Ell tipo de suscripcion seleccionado no se encuentra en la base de datos.");
                           llenarTabla();
                           break;
                       case 2:
                           System.out.println("No funco");
                           Alertas.error("Error de conexion", "Error de servidor", "Imposible conectar con el servidor verifique su conexion a Internet");
                           break;
                       default:
                           break;
                   }
                bloquear(false); progTrans.setVisible(false); BPrecios.setDisable(true);
                BHab.setDisable(false); TNombre.requestFocus();
               });
               new Thread(ms).start();
               tablaSuscripcion.getSelectionModel().clearSelection();
            }
        });
    }
    
    //Metodo para eliminar
    private void eliminar(){
        BEliminar.setOnAction((e)->{
            if(tablaSuscripcion.getSelectionModel().getSelectedItem()!=null){
                Optional<ButtonType> result = pregunta("Confirmacion de eliminacion",
                        "¿Desea eliminar el elemnto seleccionado","").showAndWait();
                if(result.get() == ButtonType.OK){
                 bloquear(true); BEliminar.setDisable(true); BModificar.setDisable(true);
                 progTrans.setVisible(true);
                    EliminarTipoSus eli = new EliminarTipoSus(tablaSuscripcion.getSelectionModel().getSelectedItem().getId());
                    eli.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent ev)->{
                        int res = eli.getValue();
                        
                        switch(res){
                            case 0: Alertas.informacion("Eliminacio", "El registro se ha eliminado correctamente");
                                    TNombre.setText(""); TADesc.setText("");
                                    llenarTabla();
                                    break;
                            
                            case 1: Alertas.error("Error de eliminacion", "Registro no encontrado", "Ell tipo de suscripcion seleccionado no se encuentra en la base de datos.");
                                    TNombre.setText(""); TADesc.setText("");
                                    llenarTabla();
                                    break;
                            
                            case 2: Alertas.error("Error de conexion", "Error de servidor", "Imposible conectar con el servidor verifique su conexion a Internet");
                                    TNombre.setText(""); TADesc.setText("");
                                    break;            
                                    
                        }
                        bloquear(false); progTrans.setVisible(false); BPrecios.setDisable(true);
                        tablaSuscripcion.getSelectionModel().clearSelection();
                        TNombre.requestFocus();  BHab.setDisable(false);
                    });
                    new Thread(eli).start();
                } 
            }
            
        });
    }
    
    //Metodo para actualizar
    private void actualizarTabla(){
        BActualizar.setOnAction((e)->{
            llenarTabla();
            TBuscar.requestFocus();
        });
    }
    
     private Alert pregunta(String titulo, String enca, String texto){
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setTitle(titulo);
           alert.setHeaderText(enca);
           alert.setContentText(texto);
           
           return alert;
       }
     
     //Metodo para activar suscripciones
     private void recuperarSuc(){
         BHab.setOnAction((e)->{
             AbrirVentana av = new AbrirVentana("/fxml/recuperarSuscripcion.fxml","Instruidos");
             new Thread(av).start();       
         });
     }
     
     //Metodo para asignar y eliminar costos
     private void costos(){
         BPrecios.setOnAction((e)->{
             if(tablaSuscripcion.getSelectionModel().getSelectedItem()!=null){ 
                datos.setNombre(tablaSuscripcion.getSelectionModel().getSelectedItem().getNombre());
                datos.setId(tablaSuscripcion.getSelectionModel().getSelectedItem().getId());
                AbrirVentana av = new AbrirVentana("/fxml/PreciosSuscripciones.fxml","Precios");
                new Thread(av).start();  
                tablaSuscripcion.getSelectionModel().clearSelection();
                TBuscar.requestFocus();
             }
         });
     }
     
}