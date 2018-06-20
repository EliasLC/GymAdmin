package com.mycompany.controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.gymadmin.Alertas;
import com.mycompany.gymadmin.Validar;
import com.mycompany.interacciondb.InstructoresCombo;
import com.mycompany.interacciondb.ModIAdm;
import com.mycompany.interacciondb.PeriodoSusCombo;
import com.mycompany.interacciondb.RegistrarSuscriptor;
import com.mycompany.interacciondb.TipoSuscripcionesCombo;
import com.mycompany.persistencia.DataBase;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * FXML Controller class
 * @author elias
 */
public class IngresarController implements Initializable {

    
    @FXML
    private JFXTextField TNombre,TPaterno,TMaterno;

    @FXML
    private JFXComboBox<Integer> ComDia,ComMes,ComAño;

    @FXML
    private JFXTextField TColonia,TManzana,TLote,TCelular,TFijo,TEmail;

    @FXML
    private JFXComboBox<PeriodoSusCombo> ComDura;
    
    @FXML
    private JFXComboBox<InstructoresCombo> ComIns;
          
    @FXML
    private JFXComboBox<TipoSuscripcionesCombo> ComTipoSus;

    @FXML
    private JFXButton BIngresar;
    
    @FXML private Label LPrecio;
    
    @FXML private ProgressIndicator progTran;
    
    //Listas
    private ObservableList<Integer> dias;
    private ObservableList<Integer> meses;
    private ObservableList<Integer> año;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dias = FXCollections.observableArrayList();
        meses= FXCollections.observableArrayList();
        año = FXCollections.observableArrayList();
        
        Validar.TextFieldLetras(TNombre);
        Validar.TextFieldLetras(TPaterno);
        Validar.TextFieldLetras(TMaterno);
        Validar.TextFieldLetras(TColonia);
        Validar.TextFieldNumeros(TCelular);
        Validar.setTextFieldLimit(TCelular,10);
        Validar.TextFieldNumeros(TFijo);
        Validar.setTextFieldLimit(TFijo, 10);
        
        
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
       
       ComDia.setItems(dias); ComMes.setItems(meses);
       ComAño.setItems(año);
        ingresar(); llenar(); llenarperiodo(); cambiarPrecio();
    }    
    
    //Metodo para llenar ombo de instructores
    private void llenar(){
        obtenerInsCombo in = new obtenerInsCombo();
        in.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent e)->{
            ComIns.setItems(in.getValue());
        });
        
        obtenerTipoSuc pb = new obtenerTipoSuc();
        pb.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent e)->{
            ComTipoSus.setItems(pb.getValue());
        });
        new Thread(in).start(); new Thread(pb).start();
    }
    
    //Metodo para llenar los periodos
    private void llenarperiodo(){
        ComTipoSus.setOnAction((e)->{
             periodosTiempo pt = new periodosTiempo(ComTipoSus.getSelectionModel().getSelectedItem().getId());
             pt.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent em)->{
                 ComDura.setItems(pt.getValue());
             });
             new Thread(pt).start();
        });
    }
    
    //Cambiar label precio con elemento seleccionado 
    private void cambiarPrecio(){
        ComDura.setOnAction((e)->{
            LPrecio.setText(String.valueOf(ComDura.getSelectionModel().getSelectedItem().getCosto()));
        });
    }
    
    //Metodo ingresar
    private void ingresar(){
        BIngresar.setOnAction((e)->{
           if(TNombre.getText().equals("")||TPaterno.getText().equals("")||TMaterno.getText().equals("")||
                   ComDia.getValue()==null||ComMes.getValue()==null||ComAño.getValue()==null
                   ||TColonia.getText().equals("")||TCelular.getText().equals("")||TEmail.getText().equals("")
                   ||ComTipoSus.getSelectionModel().getSelectedItem()==null||
                   ComDura.getSelectionModel().getSelectedItem()==null||
                   ComIns.getSelectionModel().getSelectedItem()==null){
                Alertas.error("Error de ingreso", "", "Se encuentran campos vacios");
           } else{
               BIngresar.setDisable(true); progTran.setVisible(true);
               String bday =String.valueOf(ComAño.getValue())+"-"+ String.valueOf(ComMes.getValue())+
                       "-"+String.valueOf(ComDia.getValue());
               
               RegistrarSuscriptor rs = new RegistrarSuscriptor();
               rs.setNom(TNombre.getText()); rs.setPaterno(TPaterno.getText());
               rs.setMaterno(TMaterno.getText()); rs.setNacimiento(nacimiento(bday));
               rs.setColonia(TColonia.getText()); rs.setManzana(TManzana.getText());
               rs.setLote(TLote.getText()); rs.setCelular(TLote.getText());
               rs.setFijo(TFijo.getText()); rs.setEmail(TEmail.getText());
               rs.setIdInstructor(ComIns.getSelectionModel().getSelectedItem().getId());
               rs.setIdTipoSus(ComTipoSus.getSelectionModel().getSelectedItem().getId());
               rs.setCosto(Float.valueOf(LPrecio.getText()));
               rs.setDuracion(fin(ComDura.getSelectionModel().getSelectedItem().toString()));
               
               //Creacion
               rs.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (WorkerStateEvent et)->{
                   int op = rs.getValue();
                   
                   switch(op){
                       case 0: Alertas.informacion("Registro Exitoso", "El registro se ha realizado correctamente");
                                TNombre.setText(""); TPaterno.setText(""); TMaterno.setText("");
                                TColonia.setText(""); TManzana.setText(""); 
                                TLote.setText(""); TFijo.setText(""); TEmail.setText(""); TCelular.setText("");
                                //ComIns.setValue(null); ComTipoSus.setValue(null); ComDura.setValue(null);
                                ComAño.setValue(null); ComMes.setValue(null); ComDia.setValue(null);
                                break;
                                
                       case 1: Alertas.error("Error de registro", "Correo electronico invalido","El correo electronico se encuentra utilizdo por otro usuario. Introdusca uno nuevo."); 
                                TEmail.setText(""); TEmail.requestFocus();
                                break;
                                
                       case 2:  Alertas.error("Error de registro", "No fue posible realizar el registor","Verifique su conexion a internet"); 
                                break;
                   }
                   BIngresar.setDisable(false); progTran.setVisible(false);
               });
               new Thread(rs).start();
           }
        });
    }
    
    //Metodo para obtener el fin de la suscripcion
    private Date fin(String periodo){
        Date fin = null ;
        switch(periodo){
            
            case "1 Semana": fin=  agregarDias(7);  
               
                break;
            case "2 Semanas": fin = agregarDias(14);
                break;
                
            case "1 Mes": fin=  agregarDias(30);
                break;
                
            case "3 Meses": fin=  agregarDias(90);
                break;
                
            case "6 Meses": fin= agregarDias(180);
                break;
                
            case "Año": fin =agregarDias(365);
                break;
                
            //default: fin = agregarDias(1);
            //break;
        }
        return fin;
    }
    
    private Date agregarDias(int dias){
        Date re = new Date();
        LocalDateTime local = re.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        local = local.plusDays(dias);
        re= Date.from(local.atZone(ZoneId.systemDefault()).toInstant());
        return re;
    }
    
     //Crear fecha
    private Date nacimiento(String fecha){
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }
    
    //Clase para obtener los tipos de intrus
    private class obtenerTipoSuc extends Task<ObservableList<TipoSuscripcionesCombo>>{

        @Override
        protected ObservableList<TipoSuscripcionesCombo> call() throws Exception {
           return list();
        }
        
        private ObservableList<TipoSuscripcionesCombo> list(){
            ObservableList<TipoSuscripcionesCombo> res = null;
            try{
                EntityManager em = DataBase.getEMF().createEntityManager();
                em.getTransaction().begin();
                Query query = em.createQuery("SELECT NEW com.mycompany.interacciondb.TipoSuscripcionesCombo(t.tsucId,t.tsucNom) FROM TipoSuc t WHERE t.tsusStatus = :status");
                query.setParameter("status", 1);
                res = FXCollections.observableArrayList(query.getResultList());
                em.getTransaction().commit();
                em.close();
            }catch(Exception e){
                System.out.println("ERRORR COMBO SUSCRIPCIONES");
            }
            return res;
        }
        
    }
    
    
    //Clase para obtener a los instructores del gym
    private class obtenerInsCombo extends Task<ObservableList<InstructoresCombo>>{

        @Override
        protected ObservableList<InstructoresCombo> call() throws Exception {
            return obtener();
        }
    
        private ObservableList<InstructoresCombo> obtener(){
            ObservableList<InstructoresCombo> res = null;
            try{
                EntityManager em = DataBase.getEMF().createEntityManager();
                em.getTransaction().begin();
                Query query = em.createQuery("SELECT NEW com.mycompany.interacciondb.InstructoresCombo(i.insId,i.insNom,i.insApat,i.insAm) FROM Instructor i WHERE i.insStatus = :status");
                query.setParameter("status", 1);
                res = FXCollections.observableArrayList(query.getResultList());
                em.getTransaction().commit();
                em.close();

            }catch(Exception e){
                System.out.println("ERRORR COMBO INSTRUCTORES");
            }
            return res;
        }
        
    }
    
    //Clase para obteener los periodos de tiempo 
    private class periodosTiempo extends Task<ObservableList<PeriodoSusCombo>>{

        private int idTipoSuc;
        
        public periodosTiempo(int id){
            this.idTipoSuc= id;
        }
        
        @Override
        protected ObservableList<PeriodoSusCombo> call() throws Exception {
            return lista();
        }
    
        private ObservableList<PeriodoSusCombo> lista(){
            ObservableList<PeriodoSusCombo> res = null;
            try{
                 EntityManager em = DataBase.getEMF().createEntityManager();
                em.getTransaction().begin();
                Query query = em.createQuery("SELECT NEW com.mycompany.interacciondb.PeriodoSusCombo(p.ptId,p.ptDuracion,p.ptPrecio) FROM PeriodoTiposuc p WHERE p.tsucId.tsucId = :status");
                query.setParameter("status", idTipoSuc);
                res = FXCollections.observableArrayList(query.getResultList());
                em.getTransaction().commit();
                em.close();
            }catch(Exception e){
                System.out.println("ERRORR COMBO INSTRUCTORES");
            }
            return res;
        }
    
    }
    
}
