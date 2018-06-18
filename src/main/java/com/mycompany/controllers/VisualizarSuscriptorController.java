package com.mycompany.controllers;

import com.jfoenix.controls.JFXTextField;
import com.mycompany.interacciondb.TablaInstructores;

import com.mycompany.interacciondb.TablaSuscriptores;
import com.mycompany.interacciondb.llenarTablaSuscriptores;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
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

/**
 * FXML Controller class
 * @author elias
 */
public class VisualizarSuscriptorController implements Initializable {

    @FXML private TableView<TablaSuscriptores> tablaUsuarios;

    @FXML
    private TableColumn<TablaSuscriptores, String> ColNombre, ColEdad, ColEmail,ColColonia, ColM, ColL,ColTipoSus, ColVen;

    @FXML private JFXTextField Tbuscar;

    @FXML
    private ProgressIndicator progTabla;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarTabla();
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
