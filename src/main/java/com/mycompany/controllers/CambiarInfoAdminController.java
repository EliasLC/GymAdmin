package com.mycompany.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.gymadmin.Cambio;
import com.mycompany.gymadmin.Datos;
import com.mycompany.gymadmin.Validar;
import com.mycompany.interacciondb.ModIAdm;
import com.mycompany.interacciondb.RecuperarContraseña;
import com.mycompany.persistencia.Administrador;
import com.mycompany.persistencia.DataBase;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * FXML Controller class
 * @author elias
 */
public class CambiarInfoAdminController implements Initializable {

    @FXML private JFXTextField nomAdm,apatAdm,amatAdm,tmovAdm,tfijAdm,emailAdm;
    @FXML private JFXButton modAdmin;
    @FXML private ProgressIndicator prog;
    @FXML private JFXComboBox<Integer> diaAdmin,mesAdmin,añoAdmin;

    private ObservableList<Integer> dias;
    private ObservableList<Integer> meses;
    private ObservableList<Integer> año;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Validar.TextFieldLetras(nomAdm); Validar.TextFieldLetras(apatAdm);
        Validar.TextFieldLetras(amatAdm); Validar.TextFieldNumeros(tfijAdm);
        Validar.TextFieldNumeros(tmovAdm);
        componentes(true);
        datosAdmin da = new datosAdmin();
        llenarInfo(da);
        new Thread(da).start();
        modInfo();
        dias = FXCollections.observableArrayList();
        meses= FXCollections.observableArrayList();
        año = FXCollections.observableArrayList();                         
        for(int i=0; i<31; i++){
            dias.add((i+1));
        }
        
        for(int i=0; i<11; i++){
            meses.add((i+1));
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        for(int i=1950; i<=cal.get(Calendar.YEAR); i++){
            año.add(i);
        }
        diaAdmin.setItems(dias); mesAdmin.setItems(meses); añoAdmin.setItems(año);
    }
    
    //Metodo para activar y desactivar componentes
    private void componentes(boolean input){
        prog.setVisible(input); nomAdm.setDisable(input); apatAdm.setDisable(input); 
        amatAdm.setDisable(input);tmovAdm.setDisable(input); tfijAdm.setDisable(input); 
        emailAdm.setDisable(input); modAdmin.setDisable(input); diaAdmin.setDisable(input);
        mesAdmin.setDisable(input); añoAdmin.setDisable(input);
    }
    
    //Clase para obtener los datos del administrador
    private class datosAdmin extends Task<Integer>{

        @Override
        protected Integer call() throws Exception {
            return obtenerDatos();
        }

        /*Metodo para obtener Datos
           Retorno 1: Obtenicion de datos de forma exitosa
           Retorno 3: No fue posible obtener los datos*/
        private int obtenerDatos(){
            int res = 2;
            try{
                EntityManager manager = DataBase.getEMF().createEntityManager();
                manager.getTransaction().begin();
                Query result = manager.createNamedQuery("Administrador.findByAdmId");
                result.setParameter("admId", Datos.getDatos().getId());
                List<Administrador> r = result.getResultList();
                if(r.size()>0){
                    Administrador adm = r.get(0); 
                    nomAdm.setText(adm.getAdmNom()); apatAdm.setText(adm.getAdmApat());
                    amatAdm.setText(adm.getAdmAmat()); tmovAdm.setText(adm.getAdmTelm());
                    tfijAdm.setText(adm.getAdmTelc()); emailAdm.setText(adm.getAdmEmail());
                    Platform.runLater(()->{
                        diaAdmin.setValue(obtenerDia(adm.getAdmFna()));
                        mesAdmin.setValue(obtenerMes(adm.getAdmFna())); 
                        añoAdmin.setValue(obtenerAño(adm.getAdmFna()));
                    });
                    res=1;
                }
                manager.getTransaction().commit();
                manager.close();
            }catch (Exception e){
                Logger.getLogger(RecuperarContraseña.class.getName()).log(Level.SEVERE, null, e);
                return 3;
            }
          return res;
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
    }
    
    private void llenarInfo(datosAdmin da){
        da.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent event) -> {
                int result = da.getValue();
                switch (result) {
                    case 1:  componentes(false);
                        break;
                    
                    case 2: Alertas.error("Error de autenticación", "Error de usuario", "Usuario invalido"); 
                            new Thread(new Cambio()).start();
                            break;
                        
                    case 3:   Alertas.error("Error de conexion", "Error de servidor", "Imposible conectar con el servidor verifique su conexion a Internet");     
                        break;
                    
                    default:
                        break;
                }
                componentes(false);
            });
    }
    
    //Metodo para cambiar la informacion del administrador
    private void modInfo(){
        modAdmin.setOnAction((e)->{
            if(nomAdm.getText().equals("")||apatAdm.getText().equals("")||amatAdm.getText().equals("")
               ||tfijAdm.getText().equals("")||tmovAdm.getText().equals("")||emailAdm.getText().equals("")){
                Alertas.error("Error de ingreso", "", "Se encuentran campos vacios");
            } else{
                    componentes(true);
                    if(mesAdmin.getValue()==2&&diaAdmin.getValue()>29){
                        Alertas.error("Error de fecha", "Fecha de nacimiengto Erronea", "Verifique la fecha de nacimiento ingresada"); 
                        componentes(false);
                        return;
                    }
                    String fecha = String.valueOf(añoAdmin.getValue())+"-"+ 
                            String.valueOf(mesAdmin.getValue())+"-"+String.valueOf(diaAdmin.getValue());
                    ModIAdm in = new ModIAdm(Datos.getDatos().getId(),nomAdm.getText(),apatAdm.getText(),
                            amatAdm.getText(),tfijAdm.getText(),tmovAdm.getText(),emailAdm.getText(),fecha);
                    
                   in.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent event) -> {
                       int res = in.getValue();
                       switch(res){
                           case 1:  Alertas.informacion("Modificacion exitosa", "Se ha modificado correctamente la informacion");
                                    break;
                           case 2:  Alertas.error("Error de autenticación", "Error de usuario", "Usuario invalido"); 
                                    new Thread(new Cambio()).start();
                                    break;
                           case 3: Alertas.error("Error de conexion", "Error de servidor", "Imposible conectar con el servidor verifique su conexion a Internet");
                                   break;
                        }
                       componentes(false);
                   });
                   new Thread(in).start();
            }
         
        });
    }
}