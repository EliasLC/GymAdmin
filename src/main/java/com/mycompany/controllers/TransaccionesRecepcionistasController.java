package com.mycompany.controllers;
import com.jfoenix.controls.JFXButton;
import com.mycompany.interacciondb.TablaTrans;
import com.mycompany.interacciondb.datos;
import com.mycompany.interacciondb.llenarTablaTransaccionesRecep;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


public class TransaccionesRecepcionistasController implements Initializable {

    
    @FXML private TableView<TablaTrans> tablaTransacciones;

    @FXML private TableColumn<TablaTrans, String> colFecha,colTipo, colImport;

    @FXML private JFXButton BSalir,BActualizar;

    @FXML private ProgressIndicator progTabla;
    
    @FXML private AnchorPane pane;

    @FXML private Label LNombre, LCelular;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarTabla(); cerrar(); actualizar();
        LNombre.setText(datos.getNombre());
        LCelular.setText(datos.getEmail());
    }
    
    //Metodo para actualizar la tabla
    private void actualizar(){
        BActualizar.setOnAction((e)->{
            llenarTabla();
        });
    }
    
    //Metodo para cerrar la vetana
    private void cerrar(){
        BSalir.setOnAction((e)->{
             pane.getScene().getWindow().hide();
        });
    }

    //Metodo para llenar la tabla
    private void llenarTabla(){
        BActualizar.setDisable(true); progTabla.setVisible(true);
        llenarTablaTransaccionesRecep t = new llenarTablaTransaccionesRecep();
        
        t.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent e)->{
            tablaTransacciones.setItems(t.getValue());
            colFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
            colTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
            colImport.setCellValueFactory(new PropertyValueFactory("importe"));
            BActualizar.setDisable(false); progTabla.setVisible(false);
        });
        
        new Thread(t).start();
    }   
}