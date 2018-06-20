package com.mycompany.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.AbrirVentana;
import com.mycompany.interacciondb.TablaSuscriptores;
import com.mycompany.interacciondb.datos;
import com.mycompany.interacciondb.extra;
import com.mycompany.interacciondb.llenarTablaSuscriptores;
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
 * @author elias
 */
public class VisualizarSuscriptorController implements Initializable {

    @FXML private TableView<TablaSuscriptores> tablaUsuarios;

    @FXML
    private TableColumn<TablaSuscriptores, String> ColNombre, ColEdad, ColEmail,ColColonia, ColM, ColL,ColTipoSus, ColVen;

    @FXML private JFXTextField Tbuscar;
    
    @FXML private JFXButton BActualizar,BMas;

    @FXML private ProgressIndicator progTabla;
    
    @FXML  private AnchorPane Pane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarTabla(); actualizar(); seleccionar();
        mas();
    }  
    
    //Metodo para abrir la ventana de mas
    private void mas(){
        BMas.setOnAction((e)->{
            
             datos.setId(tablaUsuarios.getSelectionModel().getSelectedItem().getId());
             AbrirVentana av = new AbrirVentana("/fxml/Asitencia.fxml","Suscriptor");
             new Thread(av).start();
             tablaUsuarios.getSelectionModel().clearSelection();
             BMas.setDisable(true);
        });
    }
    
    //Metodo para actualizar la tabla
    private void actualizar(){
        BActualizar.setOnAction((e)->{
            llenarTabla(); 
        });
    }
    
    
    //Metodo para seleccionar elementos en la tabla
    private void seleccionar(){
         tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TablaSuscriptores> 
                observable, TablaSuscriptores oldValue, TablaSuscriptores newValue) -> {
            
            if(newValue != null){
                Tbuscar.setDisable(true); BMas.setDisable(false); BActualizar.setDisable(true);
            }
        }); 
         
          Pane.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e)->{
              Tbuscar.setDisable(false); BMas.setDisable(true); BActualizar.setDisable(false);
              tablaUsuarios.getSelectionModel().clearSelection();
        });
    }
    
    //Metodo para llenar la tabla de suscriptores
    private void llenarTabla(){
        Tbuscar.setDisable(true); progTabla.setVisible(true);
        llenarTablaSuscriptores lts = new llenarTablaSuscriptores();
        lts.addEventHandler((WorkerStateEvent.WORKER_STATE_SUCCEEDED), (WorkerStateEvent e)->{
            ObservableList<TablaSuscriptores> items = lts.getValue();
            tablaUsuarios.setItems(items);
            ColNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
            ColEdad.setCellValueFactory(new PropertyValueFactory("edad"));
            ColEmail.setCellValueFactory(new PropertyValueFactory("email"));
            ColColonia.setCellValueFactory(new PropertyValueFactory("colonia"));
            ColM.setCellValueFactory(new PropertyValueFactory("mza"));
            ColL.setCellValueFactory(new PropertyValueFactory("lote"));
            ColTipoSus.setCellValueFactory(new PropertyValueFactory("tipoSus"));
            ColVen.setCellValueFactory(new PropertyValueFactory("fechaVen"));
            
            FilteredList<TablaSuscriptores> datos = new FilteredList <> (items, a -> true);
            Tbuscar.setOnKeyReleased((el)->{
                    Tbuscar.textProperty().addListener((observable, oldValue, newValue) ->{
                    datos.setPredicate((Predicate <? super TablaSuscriptores>) ins -> {

                       if(newValue == null || newValue.isEmpty()){
                           return true;
                       }
                       String lower = newValue.toLowerCase();

                       return ins.getNombre().toLowerCase().contains(lower);
                   });
                });
            });
            SortedList<TablaSuscriptores> dataCam = new SortedList<>(datos);
            dataCam.comparatorProperty().bind(tablaUsuarios.comparatorProperty());
            tablaUsuarios.setItems(dataCam);
            Tbuscar.setDisable(false); progTabla.setVisible(false);
        });
        new Thread(lts).start();
    }
    
    
    
}
