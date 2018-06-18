package com.mycompany.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.AbrirVentana;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.gymadmin.Datos;
import com.mycompany.interacciondb.DeleteAdm;
import com.mycompany.gymadmin.validarEmail;
import com.mycompany.interacciondb.DeleteIns;
import com.mycompany.interacciondb.InsertarAdministrador;
import com.mycompany.interacciondb.InsertarInstructor;
import com.mycompany.interacciondb.TablaAdministradores;
import com.mycompany.interacciondb.TablaInstructores;
import com.mycompany.interacciondb.datos;
import com.mycompany.interacciondb.llenarTablaAdm;
import com.mycompany.interacciondb.llenarTablaInstructores;
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
import javafx.event.ActionEvent;
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
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

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
   
    
    //Objetos Instructores
    @FXML private TableView<TablaInstructores> tablaInstructores;
    @FXML private TableColumn <TablaInstructores, String> colNombreIns;
    @FXML private TableColumn <TablaInstructores, String> colTelMovilIns;
    @FXML private TableColumn <TablaInstructores, String> colTelFijoIns;
    @FXML private TableColumn <TablaInstructores, String> colCorreoIns;
    @FXML private TableColumn <TablaInstructores, String> colDireccionIns;
            
    @FXML private JFXButton agregarIns,eliminarIns,actualizarTablaIns,insInstruidos;
    @FXML private ProgressIndicator progIns,progTablaIns;
    @FXML private JFXTextField buscarIns, tNomIns,tPatIns,tMatIns,tMovIns,tFijoIns,tEmailIns,tColIns,tLoteIns,tMznIns;
    @FXML private JFXComboBox<Integer> cDiaIns, cMesIns,cAñoIns;
    

//Listas
    private ObservableList<Integer> dias;
    private ObservableList<Integer> meses;
    private ObservableList<Integer> año;
    
    //Suscriptores
    @FXML JFXButton suscriptorVisualizar,suscriptoresEstadisticas;
    @FXML AnchorPane paneSuscriptores,paneIns;
    
    
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
       Date d= new Date();
       Mfecha.setText("Fecha: "+String.valueOf(obtenerDia(d))+"/"+String.valueOf(obtenerMes(d))
               +"/"+String.valueOf(obtenerAño(d)));
       
       seleccionarInstructor();
       eliminarInstructor();
    
        admNombre.setText("Administrador: "+Datos.getDatos().getnombre());
        actualizarTablaIns();
       
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
      cDiaIns.setItems(dias); cMesIns.setItems(meses); cAñoIns.setItems(año);
      diaAdmin.setItems(dias); mesAdmin.setItems(meses); añoAdmin.setItems(año);
      datosModAdm(); quitar(); actTableAdm(); eliminarAdm();
      insertarInstructor(); instruidos();
    }
    
         private int obtenerDia(Date date){
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                return  cal.get(Calendar.DAY_OF_MONTH);
            }

            private int obtenerMes(Date date){
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                return  cal.get(Calendar.MONTH)+1;
            }

            private int obtenerAño(Date date){
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                return  cal.get(Calendar.YEAR);
            }
    
    
      //Acciones cambio tab
    private void cambioTab(){
        tabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
            if(newValue.equals(tabInformacion)){
                cambiarPaneNoTransition("/fxml/CambiarInfoAdmin.fxml",paneinfo);
            }
            
            if(newValue.equals(tabSuscipciones)){
                
            }
            
            if(newValue.equals(tabSuscriptores)){
                cambiarPaneNoTransition("/fxml/VisualizarSuscriptor.fxml",paneSuscriptores);
            }
            
            if(newValue.equals(tabInstructores)){
                llenarTablarInstructores();
            }
            
            if(newValue.equals(tabRecepcionista)){
                
            }
            
            if(newValue.equals(tabAdministradores)){
                llenarAdm();
            }
        });
    }//END
    
    //Metodo para cerrar secion
    private void logOut(){
        ButtonLogout.setOnAction((e)->{
            Alertas.confirmacionPregunta(1);
        });
    }//END
  
     //Metodo para cambiar pane sin animacion
    private void cambiarPaneNoTransition(String archivo, AnchorPane root){
         try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(archivo));
            root.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//END
 
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
    }//END
    
    
/* ****************************************Metodo Instructores**************************************** */    
    private void insertarInstructor(){
        agregarIns.setOnAction((ActionEvent e)->{
          
            if(tNomIns.getText().equals("")||tPatIns.getText().equals("")||tMatIns.getText().equals("")
              ||tMovIns.getText().equals("")||tFijoIns.getText().equals("")||cDiaIns.getValue()==null||cMesIns.getValue()==null||
              cAñoIns.getValue()==null||tEmailIns.getText().equals("")||tColIns.getText().equals("")||tLoteIns.getText().equals("")||
              tMznIns.getText().equals("")){
                 Alertas.error("Error de ingreso", "", "Se encuentran campos vacios");
            } else if(cDiaIns.getValue()>29&&cMesIns.getValue()==2){
                Alertas.error("Error de fecha", "Fecha de nacimiento Erronea", "Verifique la fecha de nacimiento ingresada"); 
            } else{
                if(valEmail(tEmailIns.getText())){
                     bloquarElementosInsertar(true);
                String fecha = String.valueOf(cAñoIns.getValue())+"-"+String.valueOf(cMesIns.getValue())+"-"
                        +String.valueOf(cDiaIns.getValue());
                InsertarInstructor ins = new InsertarInstructor(tNomIns.getText(),tPatIns.getText(),
                tMatIns.getText(),tMovIns.getText(),tFijoIns.getText(),tEmailIns.getText(),fecha,
                tColIns.getText(),tMznIns.getText(), tLoteIns.getText());
                
                ins.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent ed)-> {
                    switch(ins.getValue()){
                        case 0: 
                            Alertas.informacion("Registro Exitoso", "El registro se ha realizado correctamente");
                            tNomIns.setText(""); tPatIns.setText(""); tMatIns.setText(""); tMovIns.setText("");
                            tFijoIns.setText(""); cDiaIns.setValue(null); cMesIns.setValue(null);
                            tEmailIns.setText(""); cAñoIns.setValue(null); tColIns.setText(""); 
                            tLoteIns.setText(""); tMznIns.setText(""); tLoteIns.setText("");
                            llenarTablarInstructores();
                            break;
                        case 1:
                                EmailAdmin.setText("");
                                Alertas.error("Error de registro", "Correo electronico invalido","El correo electronico se encuentra utilizdo por otro usuario. Introdusca uno nuevo."); 
                                EmailAdmin.requestFocus();
                            break;
                        case 2:
                            Alertas.error("Error de registro", "No fue posible realizar el registor","Verifique su conexion a internet"); 
                            break;
                    }
                    
                   bloquarElementosInsertar(false);
                });
                new Thread(ins).start();
                }
               
            }
        });
    }
    
    private void bloEliminar(boolean input){
        agregarIns.setDisable(input); actualizarTablaIns.setDisable(input);
        tablaInstructores.setDisable(input); progIns.setVisible(input);
    }
    
    private void eliminarInstructor(){
        eliminarIns.setOnAction((e)->{
            eliminarIns.setDisable(true);
            if(tablaInstructores.getSelectionModel().getSelectedItem()!=null){
                bloEliminar(true);
                DeleteIns di = new DeleteIns(tablaInstructores.getSelectionModel().getSelectedItem().getEmail());
                di.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent ev)-> {
                    int res = di.getValue();
                     switch(res){
                        case 0:  Alertas.error("Error", "Erro de administrador","El usuario no se encuentra en la base de datos");   
                            llenarTablarInstructores();    
                            break;
                        case 1: Alertas.informacion("Eliminacion exitosa", "El registro se ha eliminado correctamente");
                                llenarTablarInstructores();
                                break;
                        case 2:  Alertas.error("Error de servidor", "No fue posible realizar la eliminacion","Verifique su conexion a internet"); 
                            break;
                    }
                   bloEliminar(false); 
                });
                new Thread(di).start();
            }
        });//END
    }
    
    private void actualizarTablaIns(){
        actualizarTablaIns.setOnAction((e)->{
               llenarTablarInstructores(); 
        });
    }
    //Validar email
    private boolean valEmail(String email){
         boolean isValid= false;
            try{
                InternetAddress internetAddress = new InternetAddress(email);
                internetAddress.validate();
                isValid= true;
            }catch(AddressException el){
        
            }   
           return isValid;
    }
    
    private void bloquarElementosInsertar(boolean input){
        agregarIns.setDisable(input); buscarIns.setDisable(input);
        actualizarTablaIns.setDisable(input);
        progIns.setVisible(input);
    }
    
    private void llenarTablarInstructores(){
        bloquearElementosTabla(true);
        llenarTablaInstructores ti = new llenarTablaInstructores();
        ti.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent ev)->{
            ObservableList<TablaInstructores> items= ti.getValue();
            tablaInstructores.setItems(items);
            colNombreIns.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
            colTelMovilIns.setCellValueFactory(new PropertyValueFactory<>("TelMovil"));
            colTelFijoIns.setCellValueFactory(new PropertyValueFactory<>("TelFijo"));
            colCorreoIns.setCellValueFactory(new PropertyValueFactory<>("Email"));     
            colDireccionIns.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
            FilteredList<TablaInstructores> datos = new FilteredList <> (items, a -> true);
            buscarIns.setOnKeyReleased(e -> {
                    buscarIns.textProperty().addListener((observable, oldValue, newValue) ->{
                    datos.setPredicate((Predicate <? super TablaInstructores>) ins -> {

                       if(newValue == null || newValue.isEmpty()){
                           return true;
                       }
                       String lower = newValue.toLowerCase();

                       if(ins.getNombre().toLowerCase().contains(lower)){
                           return true;
                       } else if (ins.getEmail().toLowerCase().contains(lower)){
                           return true;
                       }
                       return false;
                   });
                });
                });
            SortedList<TablaInstructores> dataCam = new SortedList<>(datos);
            dataCam.comparatorProperty().bind(tablaInstructores.comparatorProperty());
            tablaInstructores.setItems(dataCam);
            bloquearElementosTabla(false);
        });
        new Thread(ti).start();
    }
    
    private void bloquearElementosTabla(boolean input){
        progTablaIns.setVisible(input); actualizarTablaIns.setDisable(input);
        buscarIns.setDisable(input); agregarIns.setDisable(input);
    }
    
    private void seleccionarInstructor(){
        tablaInstructores.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TablaInstructores> 
                observable, TablaInstructores oldValue, TablaInstructores newValue) -> {
            
            if(newValue != null){
               agregarIns.setDisable(true); actualizarTablaIns.setDisable(true);
               eliminarIns.setDisable(false); insInstruidos.setDisable(false);
            }
        });    
        
        paneIns.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e)->{
            agregarIns.setDisable(false); 
            eliminarIns.setDisable(true); actualizarTablaIns.setDisable(false);
            tablaInstructores.getSelectionModel().clearSelection(); insInstruidos.setDisable(true);
        });
    }
    
    //Metodo para abrir la ventaana de instruidos
    private void instruidos(){
        insInstruidos.setOnAction((e)->{
            insInstruidos.setDisable(true);
            if(tablaInstructores.getSelectionModel().getSelectedItem()!=null){
                datos.setEmail(tablaInstructores.getSelectionModel().getSelectedItem().getEmail());
                datos.setNombre(tablaInstructores.getSelectionModel().getSelectedItem().getNombre());
                AbrirVentana av = new AbrirVentana("/fxml/Instruidos.fxml","Instruidos");
                new Thread(av).start();
            }
          
        });
    }
    
    
/* ****************************************Fin metodos instructores**************************************** */    
    
    
/* ****************************************Metodo administradores**************************************** */
            //Insertar usuario
    private void insertarAdm(){
        agregarAdmin.setOnAction((e)->{
            
          if(nAdmin.getText().equals("")||ApePatAdmin.getText().equals("")||ApeMatAdmin.getText().equals("")
             ||TMovAdmin.getText().equals("")||TfijoAdmin.getText().equals("")||EmailAdmin.getText().equals("")||
             diaAdmin.getValue()==null||mesAdmin.getValue()==null||añoAdmin.getValue()==null  ){
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
                      ,EmailAdmin.getText(),fecha,tColIns.getText(),tMznIns.getText(),tLoteIns.getText()); 
                              
                      ia.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent ev)->{
                          
                          switch(ia.getValue()){
                              case 0: 
                                  Alertas.informacion("Registro Exitoso", "El registro se ha realizado correctamente");
                                  nAdmin.setText(""); ApePatAdmin.setText(""); ApeMatAdmin.setText("");
                                  TMovAdmin.setText(""); TfijoAdmin.setText(""); EmailAdmin.setText("");
                                  diaAdmin.setValue(null); mesAdmin.setValue(null); añoAdmin.setValue(null);
                                  llenarAdm();
                                  break;
                                  
                              case 1: 
                                  EmailAdmin.setText("");
                                  Alertas.error("Error de registro", "Correo electronico invalido","El correo electronico se encuentra utilizdo por otro usuario. Introdusca uno nuevo."); 
                                  EmailAdmin.requestFocus();
                                  break;
                              
                              case 2:   Alertas.error("Error de registro", "No fue posible realizar el registor","Verifique su conexion a internet");  
                                  break;
                          }
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
    
    //Quitar bloqueo Admin
    private void quitar(){
        admPane.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e)->{
            agregarAdmin.setDisable(false); fnomAdm.setDisable(false);
            eliminarAdmin.setDisable(true); actTablaAdmin.setDisable(false);
            TablaAdmin.getSelectionModel().clearSelection();
        });
    }//END
    
    //Metodo para actualizar tabla administrador
    private void actTableAdm(){
       // 
       actTablaAdmin.setOnAction((e)->{
           llenarAdm();
       });   
    }//END
    
      //Pasar Datos a modificar
    private void datosModAdm(){
        TablaAdmin.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TablaAdministradores> 
                observable, TablaAdministradores oldValue, TablaAdministradores newValue) -> {
            
            if(newValue != null){
               agregarAdmin.setDisable(true); fnomAdm.setDisable(true);
               eliminarAdmin.setDisable(false); actTablaAdmin.setDisable(true);
            }
        });
    }//END
    
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
        agregarAdmin.setDisable(input); 
        actTablaAdmin.setDisable(input); TablaAdmin.setDisable(input);
        progressTableAdmin.setVisible(input); fnomAdm.setDisable(input);
    }//END
    
     private void bloquearElementoAdm(boolean input){
        agregarAdmin.setDisable(input); actTablaAdmin.setDisable(input); 
        TablaAdmin.setDisable(input); progTranAdmin.setVisible(input);
        fnomAdm.setDisable(input);
    }// END

  
    private void eliminarAdm(){
        eliminarAdmin.setOnAction((e)->{
            eliminarAdmin.setDisable(true);
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
    } //END
/* ****************************************Fin Metodos administradores**************************************** */


/* ****************************************Metodos Informacion**************************************** */
 //Modificar Informacion
    private void cambiarPaneInfo(){
        modInfo.setOnAction((e)->{
            cambiarPane("/fxml/CambiarInfoAdmin.fxml",paneinfo);
        });
        
        modpass.setOnAction((e)->{
          cambiarPane("/fxml/CambiarPass.fxml",paneinfo);
        });
    }
/* ****************************************Fin Metodos Informacion**************************************** */
    
    
/* ****************************************Metodos Suscriptores**************************************** */
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
 /* ****************************************Fin Metodos Suscriptores**************************************** */   
      
}