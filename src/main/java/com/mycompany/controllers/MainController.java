package com.mycompany.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.gymadmin.Datos;
import com.mycompany.gymadmin.DeleteAdm;
import com.mycompany.gymadmin.validarEmail;
import com.mycompany.interacciondb.InsertarAdministrador;
import com.mycompany.interacciondb.TablaAdministradores;
import com.mycompany.interacciondb.llenarTablaAdm;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MainController implements Initializable {
    @FXML private JFXButton ButtonLogout;
   
    @FXML private Label admNombre,Mfecha;
    @FXML private JFXTabPane tabPane;
    @FXML private Tab tabSuscipciones,tabSuscriptores,tabRecepcionista,tabInstructores,tabAdministradores,tabInformacion;
    
    //Objetos administradores
    @FXML private TableView<TablaAdministradores> TablaAdmin;
    @FXML private TableColumn <TablaAdministradores, String> nomAdmin;
    @FXML private TableColumn <TablaAdministradores, String>telfijoAdmin;
    @FXML private TableColumn <TablaAdministradores, String> correoAdmin;
    @FXML private TableColumn <TablaAdministradores, String> telmovAdmin;
    @FXML private JFXButton agregarAdmin,eliminarAdmin,actTablaAdmin;
    @FXML private ProgressIndicator progressTableAdmin,progTranAdmin;
    @FXML private JFXTextField nAdmin,ApePatAdmin,ApeMatAdmin,TMovAdmin,TfijoAdmin,EmailAdmin,fnomAdm;
    @FXML private JFXComboBox<Integer> diaAdmin, mesAdmin, añoAdmin;
    @FXML private AnchorPane admPane; 
   
    
    //Listas
    private ObservableList<Integer> dias;
    private ObservableList<Integer> meses;
    private ObservableList<Integer> año;
    
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
       if(Datos.getDatos().getStatus()==1){
           tabPane.getTabs().remove(tabAdministradores);
       }
        admNombre.setText("Administrador: "+Datos.getDatos().getnombre());
        
       
        cambiarPaneInfo();
        cambiarSuscriptores();
        insertarAdm();
        logOut();
        
        dias = FXCollections.observableArrayList();
        meses= FXCollections.observableArrayList();
        año = FXCollections.observableArrayList();
                
                 cambiarPaneNoTransition("/fxml/adminsuscripciones.fxml",paneSuscripcion);
      
       cambiarPaneSuscripcion();
       cambioTab();
       
       Calendar cal = Calendar.getInstance();
       cal.setTime(new Date());
       for(int i=0; i<31; i++){
        dias.add(i+1);
       }
       for(int i=1; i<13; i++){
        meses.add(i);
       }
       for(int i= 1950; i<=cal.get(Calendar.YEAR); i++){
        año.add(i);
       }
       
      diaAdmin.setItems(dias); mesAdmin.setItems(meses); añoAdmin.setItems(año);
      datosModAdm(); quitar(); actTableAdm(); eliminarAdm();
    }
    
    
    //Pasar Datos a modificar
    private void datosModAdm(){
        TablaAdmin.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TablaAdministradores> 
                observable, TablaAdministradores oldValue, TablaAdministradores newValue) -> {
            
            if(newValue != null){
               agregarAdmin.setDisable(true); fnomAdm.setDisable(true);
               eliminarAdmin.setDisable(false);
            }
        });
    }

    //Quitar bloqueo Admin
    private void quitar(){
        admPane.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e)->{
            agregarAdmin.setDisable(false); fnomAdm.setDisable(false);
            eliminarAdmin.setDisable(true);
            TablaAdmin.getSelectionModel().clearSelection();
        });
    }
    
    //Metodo para actualizar tabla administrador
    private void actTableAdm(){
       // 
       actTablaAdmin.setOnAction((e)->{
           llenarAdm();
       });   
    }
    
    //Metodo para cerrar secion
    private void logOut(){
        ButtonLogout.setOnAction((e)->{
            Alertas.confirmacionPregunta(1);
        });
    }
    
    //Acciones cambio tab
    private void cambioTab(){
        tabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
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
                llenarAdm();
                
            }
        });
    }
    
    //llenarTabla
    private void llenarAdm(){
        bloquearElementosAdm(true);
        llenarTablaAdm adm = new llenarTablaAdm();
        adm.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent event)->{
                ObservableList<TablaAdministradores>  administradores= adm.getValue();
                TablaAdmin.setItems(administradores);
                nomAdmin.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
                telfijoAdmin.setCellValueFactory(new PropertyValueFactory<>("TelFijo"));
                telmovAdmin.setCellValueFactory(new PropertyValueFactory<>("TelMovil"));
                correoAdmin.setCellValueFactory(new PropertyValueFactory<>("Email"));
                
                FilteredList<TablaAdministradores> datos = new FilteredList <> (administradores, a -> true);
                
                fnomAdm.setOnKeyReleased(e -> {
                    fnomAdm.textProperty().addListener((observable, oldValue, newValue) ->{
                    datos.setPredicate((Predicate <? super TablaAdministradores>) admin -> {

                       if(newValue == null || newValue.isEmpty()){
                           return true;
                       }
                       String lower = newValue.toLowerCase();

                       if(admin.getNombre().toLowerCase().contains(lower)){
                           return true;
                       } else if (admin.getEmail().toLowerCase().contains(lower)){
                           return true;
                       }
                       return false;
                   });
                });
                });
                SortedList<TablaAdministradores> dataCam = new SortedList<>(datos);
                dataCam.comparatorProperty().bind(TablaAdmin.comparatorProperty());
                TablaAdmin.setItems(dataCam);
                bloquearElementosAdm(false);
        });
        new Thread(adm).start();
    }//END
    
    private void bloquearElementosAdm(boolean input){
        agregarAdmin.setDisable(input); eliminarAdmin.setDisable(input);
        actTablaAdmin.setDisable(input); TablaAdmin.setDisable(input);
        progressTableAdmin.setVisible(input); fnomAdm.setDisable(input);
    }
    
     private void bloquearElementoAdm(boolean input){
        agregarAdmin.setDisable(input); eliminarAdmin.setDisable(input);
        actTablaAdmin.setDisable(input); TablaAdmin.setDisable(input);
        progTranAdmin.setVisible(input); fnomAdm.setDisable(input);
    }
        //Insertar usuario
    private void insertarAdm(){
        agregarAdmin.setOnAction((e)->{
            
          if(nAdmin.getText().equals("")||ApePatAdmin.getText().equals("")||ApeMatAdmin.getText().equals("")
             ||TMovAdmin.getText().equals("")||TfijoAdmin.getText().equals("")||EmailAdmin.getText().equals("")||
             diaAdmin.getValue()==null||mesAdmin.getValue()==null||añoAdmin.getValue()==null   ){
                Alertas.error("Error de ingreso", "", "Se encuentran campos vacios");
                
          }else if(mesAdmin.getValue()==2&&diaAdmin.getValue()>29){
            Alertas.error("Error de fecha", "Fecha de nacimiengto Erronea", "Verifique la fecha de nacimiento ingresada"); 
          } else{
              bloquearElementoAdm(true);
              
              validarEmail ve = new validarEmail(EmailAdmin.getText());
              ve.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent event) ->{
                  
                  if(ve.getValue()){
                      
                      String fecha = String.valueOf(añoAdmin.getValue())+"-"+ 
                      String.valueOf(mesAdmin.getValue())+"-"+String.valueOf(diaAdmin.getValue());
                      
                      InsertarAdministrador ia = new InsertarAdministrador(nAdmin.getText(),
                      ApePatAdmin.getText(),ApeMatAdmin.getText(),TMovAdmin.getText(),TfijoAdmin.getText()
                      ,EmailAdmin.getText(),fecha); 
                              
                      ia.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent ev)->{
                          if(ia.getValue()){
                               Alertas.informacion("Registro Exitoso", "El registro se ha realizado correctamente");
                          } else{
                              Alertas.error("Error de registro", "No fue posible realizar el registor","Verifique su conexion a internet"); 
                          }
                          nAdmin.setText(""); ApePatAdmin.setText(""); ApeMatAdmin.setText("");
                          TMovAdmin.setText(""); TfijoAdmin.setText(""); EmailAdmin.setText("");
                          diaAdmin.setValue(null); mesAdmin.setValue(null); añoAdmin.setValue(null);
                          llenarAdm();
                           bloquearElementoAdm(false);
                      });
                      new Thread(ia).start();
                  } else{
                      Alertas.error("Error de ingreso", "Correo Invalido", "Ingrese Un correo valido");
                      System.out.println("Correo Invalido");
                  }
                 
              } );
              new Thread(ve).start();
          }
        });
    }//END
  
    private void eliminarAdm(){
        eliminarAdmin.setOnAction((e)->{
            if(TablaAdmin.getSelectionModel().getSelectedItem()!=null){
                bloquearElementoAdm(true);
                
                DeleteAdm da = new DeleteAdm(TablaAdmin.getSelectionModel().getSelectedItem().getEmail());
                da.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent ev)-> {
                   
                    int res = da.getValue();
                    switch(res){
                        case 0:  Alertas.error("Error", "Erro de administrador","El usuario no se encuentra en la base de datos"); 
                                 llenarAdm();   
                            break;
                        case 1: Alertas.informacion("Eliminacion exitosa", "El registro se ha eliminado correctamente");
                                llenarAdm();
                            break;
                        case 2:  Alertas.error("Error de servidor", "No fue posible realizar la eliminacion","Verifique su conexion a internet"); 
                            break;
                    }
                     bloquearElementoAdm(false);
                });
                new Thread(da).start();
            }
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