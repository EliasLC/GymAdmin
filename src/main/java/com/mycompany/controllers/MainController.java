package com.mycompany.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.gymadmin.Cambio;
import com.mycompany.gymadmin.Datos;
import com.mycompany.gymadmin.Stages;
import com.mycompany.interacciondb.InsertarAdministrador;
import com.mycompany.interacciondb.TablaAdministradores;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MainController implements Initializable {
    @FXML private JFXButton ButtonLogout;
    @FXML private Label admNombre,Mfecha;
    @FXML private JFXTabPane tabPane;
    @FXML private Tab tabSuscipciones,tabSuscriptores,tabRecepcionista,tabInstructores,tabAdministradores,tabInformacion;
    @FXML private TableView TablaAdmin;
    @FXML private TableColumn <TablaAdministradores, String> nomAdmin;
    @FXML private TableColumn <TablaAdministradores, String>telfijoAdmin;
    @FXML private TableColumn <TablaAdministradores, String> correoAdmin;
    @FXML private TableColumn <TablaAdministradores, String> telmovAdmin;
    @FXML private JFXButton agregarAdmin,eliminarAdmin;
    @FXML private ProgressIndicator progressAdmin;
    @FXML private JFXTextField nAdmin,ApePatAdmin,ApeMatAdmin,TMovAdmin,TfijoAdmin,EmailAdmin;
    @FXML private JFXComboBox diaAdmin, mesAdmin, añoAdmin;
    
    //Listas
    private ObservableList<TablaAdministradores> administradores;
    private ObservableList<String> dias;
    private ObservableList<String> meses;
    private ObservableList<String> año;
    
    //Suscriptores
    @FXML JFXButton suscriptorVisualizar,suscriptoresEstadisticas;
    @FXML AnchorPane paneSuscriptores;
    
    
    //Modificar informacion
    @FXML JFXButton modInfo,modpass;
    @FXML AnchorPane paneinfo;
        
    //Suscripciones
    @FXML JFXButton adminSuscripcion,adminEstadisticas;
    @FXML AnchorPane paneSuscripcion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        admNombre.setText("Administrador: "+Datos.getDatos().getnombre());
        
        cambiarPaneInfo();
        cambiarSuscriptores();
        insertarAdm();
        logOut();
        
        dias = FXCollections.observableArrayList();
        meses= FXCollections.observableArrayList();
        año = FXCollections.observableArrayList();
        
        //Enlazar Columnas con atributos administrador
        administradores = FXCollections.observableArrayList();
                 TablaAdmin.setItems(administradores);
                 nomAdmin.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
                 telfijoAdmin.setCellValueFactory(new PropertyValueFactory<>("TelFijo"));
                 telmovAdmin.setCellValueFactory(new PropertyValueFactory<>("TelMovil"));
                 correoAdmin.setCellValueFactory(new PropertyValueFactory<>("Email"));
                 
                 cambiarPaneNoTransition("/fxml/adminsuscripciones.fxml",paneSuscripcion);
       for(int i=1; i<8; i++){
           dias.add(String.valueOf(i));
       }
         
       for(int i=1; i<13; i++){
           meses.add(String.valueOf(i));
       }
       
       for(int i= 1930; i<2019; i++){
           año.add(String.valueOf(i));
       }
       
       diaAdmin.setItems(dias); mesAdmin.setItems(meses); añoAdmin.setItems(año);
       cambiarPaneSuscripcion();
       cambioTab();
    }

    //Metodo para cerrar secion
    private void logOut(){
        ButtonLogout.setOnAction((e)->{
            new Thread(new Cambio()).start();
        });
    }
    
    //Acciones cambio tab
    private void cambioTab(){
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if(newValue.equals(tabInformacion)){
                    System.out.println("Nellprro");
                    cambiarPaneNoTransition("/fxml/CambiarInfoAdmin.fxml",paneinfo);
                }
                
                if(newValue.equals(tabSuscipciones)){
                    
                }
                
                if(newValue.equals(tabSuscriptores)){
                    cambiarPaneNoTransition("/fxml/VisualizarSuscriptor.fxml",paneSuscriptores);
                }
                
                if(newValue.equals(tabSuscriptores)){
                
                }
                
                if(newValue.equals(tabRecepcionista)){
                
                }
               
                if(newValue.equals(tabAdministradores)){
                
                }
                
                
            }
        });
    }
        //Insertar usuario
    private void insertarAdm(){
        agregarAdmin.setOnAction((e)->{
            agregarAdmin.setDisable(true);
            progressAdmin.setVisible(true);
            TablaAdministradores ta= new TablaAdministradores();
            ta.setNombre(nAdmin.getText()+" "+ApePatAdmin.getText()+" "+ApeMatAdmin.getText());
            ta.setTelFijo(TfijoAdmin.getText()); ta.setTelMovil(TMovAdmin.getText()); ta.setEmail(EmailAdmin.getText());
            InsertarAdministrador in =  new InsertarAdministrador(nAdmin.getText(),
            ApePatAdmin.getText(),ApeMatAdmin.getText(),TMovAdmin.getText(),TfijoAdmin.getText(),EmailAdmin.getText());
            
             in.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent event) -> {
                 if(!in.getValue()){
                     Alertas.error("Error de conexion", "", "Insercion fallida");
                 } else{
                     Alertas.informacion("Insecion exitosa", "Se ha realizado correcgttamente la insercion c:");
                     administradores.add(ta);
                     nAdmin.setText(""); ApePatAdmin.setText(""); ApeMatAdmin.setText("");
                     TMovAdmin.setText(""); TfijoAdmin.setText(""); EmailAdmin.setText("");
                     diaAdmin.setValue(null); mesAdmin.setValue(null); añoAdmin.setValue(null);
                 }
                  progressAdmin.setVisible(false);
                 agregarAdmin.setDisable(false);
                 
             });
               new Thread(in).start();
        });
    
    }
    
    
    //Modificar suscriptores
    private void cambiarSuscriptores(){
        suscriptorVisualizar.setOnAction((e)->{
            cambiarPane("/fxml/VisualizarSuscriptor.fxml",paneSuscriptores);
        });
        
        suscriptoresEstadisticas.setOnAction((e)->{
            cambiarPane("/fxml/SuscriptoresEstadisticas.fxml",paneSuscriptores);
        });
    }
    
    //Modificar suscripcion
    private void cambiarPaneSuscripcion(){
        adminSuscripcion.setOnAction((e)->{
            cambiarPane("/fxml/adminsuscripciones.fxml",paneSuscripcion);
        });
        
        adminEstadisticas.setOnAction((e)->{
            cambiarPane("/fxml/EstadisticasAdmin.fxml",paneSuscripcion);
        });
    }
    
    //Modificar Informacion
    private void cambiarPaneInfo(){
        modInfo.setOnAction((e)->{
            cambiarPane("/fxml/CambiarInfoAdmin.fxml",paneinfo);
        });
        
        modpass.setOnAction((e)->{
          cambiarPane("/fxml/CambiarPass.fxml",paneinfo);
        });
    }
    
    //Cambiar pane
    private void cambiarPane(String archivo, AnchorPane root){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(archivo));
            FadeTransition fd = new FadeTransition(Duration.seconds(1),pane);
            root.getChildren().setAll(pane);
            fd.setFromValue(0); fd.setToValue(1);
            fd.setCycleCount(1);
            fd.play();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Metodo para cambiar pane sin animacion
    private void cambiarPaneNoTransition(String archivo, AnchorPane root){
         try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(archivo));
            root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}