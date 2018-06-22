package com.mycompany.controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.gymadmin.Validar;
import com.mycompany.interacciondb.EliminarCosto;
import com.mycompany.interacciondb.InsertarPrecio;
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

    @FXML private Label LSuscripcion;

    @FXML private TableView<TablaPrecios> tablaPrecio;

    @FXML private TableColumn<TablaPrecios, String> ColPTiempo,ColPrecio;

    @FXML private JFXButton BEliminar, BAgregar, BModificar,BActualizar;
    
    @FXML private JFXComboBox<String> ComPTiempo;
   
    @FXML private ProgressIndicator progTran, progTabla;

    @FXML private AnchorPane pane;
    
    @FXML private JFXTextField TCosto;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ObservableList<String> opciones = FXCollections.observableArrayList();
       opciones.add( "1 Semana"); opciones.add("2 Semanas"); opciones.add("1 Mes");
       opciones.add("3 Meses"); opciones.add("6 meses"); opciones.add("Año");
       ComPTiempo.setItems(opciones); LSuscripcion.setText(LSuscripcion.getText()+" "+datos.getNombre());
       llenar(); Validar.TextFieldNumeros(TCosto); Validar.setTextFieldLimit(TCosto, 4);
       //numero();
       seleccionar(); actualizar();  modificar(); insertar(); eliminar();
    }  
    
    //Metodo para insertar
    private void insertar(){
        BAgregar.setOnAction((e)->{
            if(ComPTiempo.getValue()==null||TCosto.getText().equals("")){
                 Alertas.error("Error de captura", "", "Se encuentran campos vacios");
            }else{
                bloquear(true); progTabla.setVisible(false); 
                progTran.setVisible(true);
        
                InsertarPrecio ip = new InsertarPrecio(datos.getId(),
                        ComPTiempo.getSelectionModel().getSelectedItem(),Float.valueOf(TCosto.getText()));
                
                ip.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent el)->{
                int res= ip.getValue();
            
                switch(res){
                    case 0: Alertas.informacion("Registro Exitoso", "El registro se ha ingresodo correctamente");
                            ComPTiempo.setValue(null); TCosto.setText("");llenar(); 
                        break;
                    case 1: Alertas.error("Error de insercion", "Registro existente", "Ya se encuentra un precio para ese periodo de tiempo ");   
                           ComPTiempo.setValue(null); TCosto.setText("");llenar(); 
                        break;
                    case 2: Alertas.error("Error de conexion", "Error de usuario", "Verifique su conexion a internet");  
                        break;
                }
                bloquear(false); progTran.setVisible(false);
            });
            new Thread(ip).start();
            }
        });
       
    }
    
    //Metodo para modificar datos
    private void modificar(){
        BModificar.setOnAction((e)->{
            
            if(ComPTiempo.getValue()==null||TCosto.getText().equals("")){
                Alertas.error("Error de ingreso", "", "Se encuentran campos vacios");
            } else{
                BModificar.setDisable(true); BEliminar.setDisable(true);
                bloquear(true); progTabla.setVisible(false); progTran.setVisible(true); 
                
                ModPrecios mp = new ModPrecios(datos.getId(), tablaPrecio.getSelectionModel().getSelectedItem().getDuracion()
                        ,Float.valueOf(TCosto.getText()));
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
                ComPTiempo.setValue(null); TCosto.setText("");
                tablaPrecio.getSelectionModel().clearSelection();
                });
                new Thread(mp).start();
            }
            
        });
    }
    
    //Metodo para eliminar 
    private void eliminar(){
        BEliminar.setOnAction((e)->{
            BModificar.setDisable(true); BEliminar.setDisable(true);
            bloquear(true); progTabla.setVisible(false); progTran.setVisible(true); 
            EliminarCosto el = new EliminarCosto();
            el.setId(datos.getId());
            el.setDuracion(tablaPrecio.getSelectionModel().getSelectedItem().getDuracion());
            
            el.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent ep)->{
                int res = el.getValue();
                 switch(res){
                        case 0: Alertas.informacion("Eliminacion Exitosa", "El registro se ha eliminado correctamente");
                                llenar(); 
                            break;
                        case 1: Alertas.error("Error de registro", "Registro", "Registro previamente eliminado");  
                            llenar(); 
                            break;
                        case 2: Alertas.error("Error de conexion", "Error de usuario", "Verifique su conexion a internet");  
                            break;
                    }
                  bloquear(false); progTran.setVisible(false);
                  ComPTiempo.setValue(null); TCosto.setText("");
                  tablaPrecio.getSelectionModel().clearSelection();
            });
            new Thread(el).start();
        });
        
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
                TCosto.setText(tablaPrecio.getSelectionModel().getSelectedItem().getPrecio());
                ComPTiempo.setValue(tablaPrecio.getSelectionModel().getSelectedItem().getDuracion());
            }
        });
        
        pane.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e)->{
            BEliminar.setDisable(true); BModificar.setDisable(true);
            BAgregar.setDisable(false); tablaPrecio.getSelectionModel().clearSelection();
        });
    }
}