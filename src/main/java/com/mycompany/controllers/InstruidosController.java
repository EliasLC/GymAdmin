package com.mycompany.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.interacciondb.TablaInstruidos;
import com.mycompany.interacciondb.buscarInstruidos;
import com.mycompany.interacciondb.datos;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 * @author elias
 */
public class InstruidosController implements Initializable {

    @FXML private AnchorPane Pane;
    
    @FXML private JFXButton cancelInstruidos;
    
    @FXML private JFXTextField buscarInstruido;

    @FXML private ProgressIndicator progTablaInstruidos;
    
    @FXML private Label LInstructor, LNumero;

    @FXML private TableView<TablaInstruidos> tablaInstruidos;

    @FXML private TableColumn<TablaInstruidos,String> nomInstruidos, edadInstruidos,movilInstruido,
            fijoInstruido, correoInstruido, fInicio;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LInstructor.setText(LInstructor.getText()+" "+datos.getNombre());
        llenarTabla(); cerrarVentana();
     }   
    
    //Metodo para llenar la tabla 
    private void llenarTabla(){
        progTablaInstruidos.setVisible(true);
        buscarInstruidos bi = new buscarInstruidos(datos.getEmail());
        bi.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent event)->{
            ObservableList<TablaInstruidos> items = bi.getValue();
            LNumero.setText(LNumero.getText()+" "+String.valueOf(items.size()));
            tablaInstruidos.setItems(items);
            nomInstruidos.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            edadInstruidos.setCellValueFactory(new PropertyValueFactory<>("edad"));
            movilInstruido.setCellValueFactory(new PropertyValueFactory<>("telMovil"));
            fijoInstruido.setCellValueFactory(new PropertyValueFactory<>("telFijo"));
            correoInstruido.setCellValueFactory(new PropertyValueFactory<>("correo"));
            fInicio.setCellValueFactory(new PropertyValueFactory<>("finicio"));
            progTablaInstruidos.setVisible(false);
            
            FilteredList<TablaInstruidos> datos = new FilteredList <> (items, a -> true);
            buscarInstruido.setOnKeyReleased(e -> {
            buscarInstruido.textProperty().addListener((observable, oldValue, newValue) ->{
            datos.setPredicate((Predicate <? super TablaInstruidos>) ins -> {

                       if(newValue == null || newValue.isEmpty()){
                           return true;
                       }
                       String lower = newValue.toLowerCase();
                       return ins.getNombre().toLowerCase().contains(lower);
                   });
                });
                });
            SortedList<TablaInstruidos> dataCam = new SortedList<>(datos);
            dataCam.comparatorProperty().bind(tablaInstruidos.comparatorProperty());
            tablaInstruidos.setItems(dataCam);
        });
        new Thread(bi).start();
    }
    
    //Metodo para cerrar la ventana
    private void cerrarVentana(){
        cancelInstruidos.setOnAction((e)->{
            Pane.getScene().getWindow().hide();
        });
        
    }
    
}