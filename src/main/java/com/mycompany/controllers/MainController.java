package com.mycompany.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.AbrirVentana;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.gymadmin.Datos;
import com.mycompany.gymadmin.Validar;
import com.mycompany.gymadmin.validarEmail;
import com.mycompany.interacciondb.DeleteAdm;
import com.mycompany.interacciondb.DeleteIns;
import com.mycompany.interacciondb.EliminarRecepcionista;
import com.mycompany.interacciondb.InsertarAdministrador;
import com.mycompany.interacciondb.InsertarInstructor;
import com.mycompany.interacciondb.InsertarRecepcionista;
import com.mycompany.interacciondb.ModificarRecep;
import com.mycompany.interacciondb.TablaAdministradores;
import com.mycompany.interacciondb.TablaInstructores;
import com.mycompany.interacciondb.TablaRecepcionista;
import com.mycompany.interacciondb.datos;
import com.mycompany.interacciondb.llenarTablaAdm;
import com.mycompany.interacciondb.llenarTablaInstructores;
import com.mycompany.interacciondb.llenarTablaRecepcionista;
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
    
    public void val(){
        
    }
    
    //Objetos recepcionista
    @FXML private TableView<TablaRecepcionista> tablaRecep;
    @FXML private TableColumn <TablaRecepcionista, String> ColRecepNombre,ColRecepTelM,ColRecepTelF,ColRecepEdad,ColRecepDir,ColRecepEmail;
    @FXML private JFXTextField TRecepBuscar,TRecepNombre,TRecepMaterno,TRecepPaterno,TRecepMovil,TRecepFijo,TRecepEmail,TRecepColonia,TRecepManzana,TRecepLote;
    @FXML private JFXButton BRecepModificar,BRecepAgregar,BRecepEliminar,BRecepActualizar,BRecepTran;
    @FXML private ProgressIndicator progRecepTabla,progRecepTran;
    @FXML private JFXComboBox<Integer> CRecepDia,CRecepMes,CRecepAno;

//Listas
    private ObservableList<Integer> dias;
    private ObservableList<Integer> meses;
    private ObservableList<Integer> año;
    
    //Suscriptores
    @FXML JFXButton suscriptorVisualizar,suscriptoresEstadisticas;
    @FXML AnchorPane paneSuscriptores,paneIns,paneRecep;
    
    
    //Modificar informacion
    @FXML JFXButton modInfo,modpass,IngresarSuscriptores;
    @FXML AnchorPane paneinfo;
        
    //Suscripciones
    @FXML JFXButton adminSuscripcion,adminEstadisticas;
    @FXML AnchorPane paneSuscripcion;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Validar.TextFieldLetras(TRecepNombre); Validar.setTextFieldLimit(TRecepLote, 5);
        Validar.TextFieldLetras(TRecepMaterno);
        Validar.TextFieldLetras(TRecepPaterno);
        Validar.TextFieldLetras(TRecepColonia);
        Validar.setTextFieldLimit(TRecepManzana, 5);Validar.TextFieldLetras(tNomIns); Validar.TextFieldNumeros(tMovIns);
        Validar.TextFieldLetras(tPatIns); Validar.TextFieldNumeros(tFijoIns);
        Validar.TextFieldLetras(tMatIns);
        Validar.TextFieldLetras(tColIns);
         Validar.TextFieldLetras(nAdmin);  Validar.TextFieldLetras(ApePatAdmin);
        Validar.TextFieldLetras(ApeMatAdmin); Validar.TextFieldNumeros(TMovAdmin);
        Validar.TextFieldNumeros(TfijoAdmin);
        
        
       if(Datos.getDatos().getStatus()==1){
           tabPane.getTabs().remove(tabAdministradores);
       }
       insertarRecep();
       Date d= new Date();
       Mfecha.setText("Fecha: "+String.valueOf(obtenerDia(d))+"/"+String.valueOf(obtenerMes(d))
               +"/"+String.valueOf(obtenerAño(d)));
       
       seleccionarInstructor();
       eliminarInstructor();
       seleccionarRecep();
        eliminarRecep();
        abrirTranRecep();
        
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
       for(int i= 1910; i<=cal.get(Calendar.YEAR); i++){
        año.add(i);
       }
      CRecepDia.setItems(dias); CRecepMes.setItems(meses); CRecepAno.setItems(año);
      cDiaIns.setItems(dias); cMesIns.setItems(meses); cAñoIns.setItems(año);
      diaAdmin.setItems(dias); mesAdmin.setItems(meses); añoAdmin.setItems(año);
      datosModAdm(); quitar(); actTableAdm(); eliminarAdm();
      insertarInstructor(); instruidos(); actualizarTablaRep();
      modificarRecep();
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
                llenarTablaRecepcionista();
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
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//END
    
    /* ****************************************Metodos Recepcionista**************************************** */
        
    
        //Metodo para insertar un nuevo usuario
        private void insertarRecep(){
            BRecepAgregar.setOnAction((e)->{
                if(TRecepNombre.getText().equals("")||TRecepPaterno.getText().equals("")||
                   TRecepMaterno.getText().equals("")||TRecepMovil.getText().equals("")||
                   TRecepEmail.getText().equals("")||TRecepColonia.getText().equals("")||
                   CRecepDia.getValue()==null||CRecepMes.getValue()==null|| CRecepAno.getValue()==null){
                    Alertas.error("Error de ingreso", "", "Se encuentran campos vacios");
                } else{
                    bloquearRecep(true); progRecepTran.setVisible(true);
                    String fecha = String.valueOf(CRecepAno.getValue())+"-"
                    +String.valueOf(CRecepMes.getValue())+"-"+String.valueOf(CRecepDia.getValue());
                    
                    InsertarRecepcionista ir = new InsertarRecepcionista (TRecepNombre.getText(),TRecepPaterno.getText(),
                    TRecepMaterno.getText(),TRecepMovil.getText(),TRecepFijo.getText(),fecha,TRecepEmail.getText(),
                    TRecepColonia.getText(),TRecepManzana.getText(),TRecepLote.getText());
                    
                    ir.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent el)->{
                        int res = ir.getValue();
                        
                        switch(res){
                            case 0:Alertas.informacion("Registro Exitoso", "El registro se ha realizado correctamente");
                                    TRecepNombre.setText("");  TRecepPaterno.setText(""); TRecepMaterno.setText("");
                                    TRecepMovil.setText(""); TRecepFijo.setText("");TRecepEmail.setText(""); 
                                    TRecepColonia.setText(""); TRecepManzana.setText(""); TRecepLote.setText("");
                                    CRecepAno.setValue(null); CRecepMes.setValue(null); CRecepDia.setValue(null);  
                                    llenarTablaRecepcionista();
                                    break;
                            case 1:  Alertas.error("Error de registro", "Correo electronico invalido","El correo electronico se encuentra utilizdo por otro usuario. Introdusca uno nuevo."); 
                                     TRecepEmail.setText(""); TRecepEmail.requestFocus();
                                break;
                            case 2:  Alertas.error("Error de registro", "No fue posible realizar el registor","Verifique su conexion a internet"); 
                                break;
                        }
                        bloquearRecep(false); progRecepTran.setVisible(false);
                    });
                    new Thread(ir).start();
                }
            });
        }
 
        //Metodo para modificar recep
        private void modificarRecep(){
            BRecepModificar.setOnAction((e)->{
                 if(TRecepNombre.getText().equals("")||TRecepPaterno.getText().equals("")||
                   TRecepMaterno.getText().equals("")||TRecepMovil.getText().equals("")||
                   TRecepEmail.getText().equals("")||TRecepColonia.getText().equals("")||
                   CRecepDia.getValue()==null||CRecepMes.getValue()==null|| CRecepAno.getValue()==null){
                    Alertas.error("Error de ingreso", "", "Se encuentran campos vacios");
                } else{
                      BRecepEliminar.setDisable(true); BRecepModificar.setDisable(true);
                      bloquearRecep(true); progRecepTran.setVisible(true); BRecepTran.setDisable(true);
                      String fecha = String.valueOf(CRecepAno.getValue())+"-"
                      +String.valueOf(CRecepMes.getValue())+"-"+String.valueOf(CRecepDia.getValue());
                      
                      ModificarRecep mr = new ModificarRecep(
                              tablaRecep.getSelectionModel().getSelectedItem().getId(),TRecepNombre.getText(),
                      TRecepPaterno.getText(),TRecepMaterno.getText(),TRecepMovil.getText(),TRecepFijo.getText(),
                      TRecepEmail.getText(),TRecepColonia.getText(),TRecepManzana.getText(),TRecepLote.getText(),
                      fecha);
                      
                      mr.addEventHandler((WorkerStateEvent.WORKER_STATE_SUCCEEDED), (WorkerStateEvent el)->{
                          int result = mr.getValue();
                          
                          switch(result){
                             
                              case 1: Alertas.informacion("Modificacion de datos", "Los datos se han cambiado correctamente");
                                      llenarTablaRecepcionista();
                                      blanRecep();
                                  break;
                              case 2: Alertas.error("Error", "Error de usuario", "El usuario a modificar no se encuentra activo");
                                      blanRecep();
                                      llenarTablaRecepcionista();
                                  break;
                                  
                              case 3: Alertas.error("Error de conexion", "Error de servidor", "Imposible conectar con el servidor verifique su conexion a Internet");
                                      TRecepNombre.requestFocus();
                              break;
                                  
                          }
                           bloquearRecep(false); progRecepTran.setVisible(false);
                      });
                      new Thread(mr).start();
                 }
            });
        }

        //Metodo para eliminar recep
        private void eliminarRecep(){
            BRecepEliminar.setOnAction((e)->{
                 bloquearRecep(true); BRecepEliminar.setDisable(true); BRecepModificar.setDisable(true);
                EliminarRecepcionista er = new EliminarRecepcionista(tablaRecep.getSelectionModel().getSelectedItem().getEmail());
                progRecepTran.setVisible(true); BRecepTran.setDisable(true);
                er.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent el)->{
                    int res = er.getValue();
                    
                    switch(res){
                    
                        case 0: Alertas.informacion("Eliminacion exitosa", "El registro se ha eliminado correctamente");
                                blanRecep();
                                llenarTablaRecepcionista();
                            break;
                        case 1: Alertas.error("Error", "Erro de usuario","El usuario no se encuentra en la base de datos");  
                                blanRecep();
                                llenarTablaRecepcionista();
                            break;
                        case 2:  Alertas.error("Error de servidor", "No fue posible realizar la eliminacion","Verifique su conexion a internet"); 
                            break;
                    }
                     progRecepTran.setVisible(false);
                     bloquearRecep(false);
                });
                new Thread(er).start();
            });
        }
        
        //Metodo para borrar datos en los elementos
        private void blanRecep(){
              TRecepNombre.setText("");
            TRecepPaterno.setText(""); TRecepPaterno.setText(""); TRecepMaterno.setText("");
            TRecepMovil.setText(""); TRecepFijo.setText(""); CRecepDia.setValue(null);
            CRecepMes.setValue(null); CRecepAno.setValue(null); TRecepEmail.setText("");
            TRecepColonia.setText(""); TRecepManzana.setText(""); TRecepLote.setText("");
        }
        
        //Metodo para actualizar la tabla
        private void actualizarTablaRep(){
            BRecepActualizar.setOnAction((e)->{
                llenarTablaRecepcionista();
            });
        }
    
        //Metodo para llenar la tabla
        private void llenarTablaRecepcionista(){
            bloquearRecep(true); progRecepTabla.setVisible(true);
            
            llenarTablaRecepcionista ltr = new llenarTablaRecepcionista();
            ltr.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent e)->{
                ObservableList<TablaRecepcionista> items = ltr.getValue();
                tablaRecep.setItems(items);
                ColRecepNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                ColRecepTelM.setCellValueFactory(new PropertyValueFactory<>("telmovil"));
                ColRecepTelF.setCellValueFactory(new PropertyValueFactory<>("telfijo"));
                ColRecepEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
                ColRecepDir.setCellValueFactory(new PropertyValueFactory<>("direccion"));
                ColRecepEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
                
                FilteredList<TablaRecepcionista> datos = new FilteredList <> (items, a -> true);
                TRecepBuscar.setOnKeyReleased(el->{
                TRecepBuscar.textProperty().addListener((observable, oldValue, newValue) ->{
                datos.setPredicate((Predicate <? super TablaRecepcionista>) ins -> {

                       if(newValue == null || newValue.isEmpty()){
                           return true;
                       }
                       String lower = newValue.toLowerCase();
                       return ins.getNombre().toLowerCase().contains(lower);
                   });
                });
                });
                SortedList<TablaRecepcionista> dataCam = new SortedList<>(datos);
                dataCam.comparatorProperty().bind(tablaRecep.comparatorProperty());
                tablaRecep.setItems(dataCam);
                bloquearRecep(false); progRecepTabla.setVisible(false);
            });
            new Thread(ltr).start();
        }
        
        //Metodo para abrir la nueva ventana
            private void abrirTranRecep(){
                BRecepTran.setOnAction((e)->{
                    BRecepTran.setDisable(true); BRecepActualizar.setDisable(true); BRecepEliminar.setDisable(true);
                    datos.setNombre(tablaRecep.getSelectionModel().getSelectedItem().getNombre());
                    datos.setEmail(tablaRecep.getSelectionModel().getSelectedItem().getTelmovil());
                    datos.setId(tablaRecep.getSelectionModel().getSelectedItem().getId());
                    AbrirVentana av = new AbrirVentana("/fxml/TransaccionesRecepcionistas.fxml","Transacciones");
                    new Thread(av).start();
                    bloquearRecep(false); blanRecep();
                    tablaRecep.getSelectionModel().clearSelection();
                });
            }
        
        //Metodo para bloquear los elementos de la tabla
        private void bloquearRecep(boolean input){
            TRecepBuscar.setDisable(input); BRecepAgregar.setDisable(input);
            BRecepActualizar.setDisable(input);
        }
        
        
        
        private void seleccionarRecep(){
                 tablaRecep.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TablaRecepcionista> 
                observable, TablaRecepcionista oldValue, TablaRecepcionista newValue) -> {
            
            if(newValue != null){
               BRecepAgregar.setDisable(true); BRecepActualizar.setDisable(true); TRecepBuscar.setDisable(true);
               BRecepModificar.setDisable(false);BRecepEliminar.setDisable(false); BRecepTran.setDisable(false);
               TRecepNombre.setText(tablaRecep.getSelectionModel().getSelectedItem().getNom());
               TRecepPaterno.setText(tablaRecep.getSelectionModel().getSelectedItem().getPaterno());
               TRecepMaterno.setText(tablaRecep.getSelectionModel().getSelectedItem().getMaterno());
               TRecepMovil.setText(tablaRecep.getSelectionModel().getSelectedItem().getTelmovil());
               TRecepFijo.setText(tablaRecep.getSelectionModel().getSelectedItem().getTelfijo());
               CRecepDia.setValue(Integer.valueOf(tablaRecep.getSelectionModel().getSelectedItem().getDia()));
               CRecepMes.setValue(Integer.valueOf(tablaRecep.getSelectionModel().getSelectedItem().getMes()));
               CRecepAno.setValue(Integer.valueOf(tablaRecep.getSelectionModel().getSelectedItem().getAño()));
               TRecepEmail.setText(tablaRecep.getSelectionModel().getSelectedItem().getEmail());
               TRecepColonia.setText(tablaRecep.getSelectionModel().getSelectedItem().getColonia());
               TRecepManzana.setText(tablaRecep.getSelectionModel().getSelectedItem().getManzana());
               TRecepLote.setText(tablaRecep.getSelectionModel().getSelectedItem().getLote());
            }
        }); 
                 
        paneRecep.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e)->{
            BRecepAgregar.setDisable(false); BRecepActualizar.setDisable(false); TRecepBuscar.setDisable(false);
            BRecepModificar.setDisable(true);BRecepEliminar.setDisable(true);
            tablaRecep.getSelectionModel().clearSelection(); blanRecep(); BRecepTran.setDisable(true);
        });
        }
    /* ****************************************Fin metodos Recepcionista**************************************** */
    
    
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
        
         IngresarSuscriptores.setOnAction((e)->{
            cambiarPane("/fxml/Ingresar.fxml",paneSuscriptores);
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