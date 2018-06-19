package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.Direccion_REC;
import com.mycompany.persistencia.Recepcionista;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author elias
 */
public class InsertarRecepcionista extends Task<Integer> {

    private String nombre,paterno,materno,movil,fijo,nacimiento, email,colonia,manzana,lote;
    private Date fecha;
    
    public InsertarRecepcionista(String nombre,String paterno,String materno,String movil, String fijo,
            String nacimiento, String email, String colonia,String manzana,String lote){
        this.nombre= nombre; this.paterno= paterno; this.materno=materno; this.movil= movil; this.fijo= fijo;
        this.nacimiento= nacimiento; this.email= email; this.colonia= colonia; this.manzana= manzana; 
        this.lote= lote;
    }
    
    @Override
    protected Integer call() throws Exception {
        return ingresar();
    }
    
    
    private int ingresar(){
        int res =1;
        try{
            EntityManager em = DataBase.getEMF().createEntityManager();
            em.getTransaction().begin();
            Query result= em.createNamedQuery("Recepcionista.findByRecEmail");
            result.setParameter("recEmail", email);
            if(result.getResultList().isEmpty()){
                Recepcionista r = new Recepcionista();
                r.setRecNom(nombre); r.setRecApat(paterno);
                r.setRecAmat(materno); r.setRecEmail(email);
                r.setRecContra("0101010"); r.setRecFna(nacimiento());
                r.setRecTelm(movil); r.setRecTelc(fijo);
                r.setRecStatus(1); r.setRecContra("3edede1");
                Direccion_REC dr = new Direccion_REC();
                 dr.setManzana(manzana); 
                 dr.setRecepcionista(r);   
                dr.setColonia(colonia); dr.setLote(lote);
                r.setDireccionrec(dr);
                em.persist(r); em.persist(dr);
                mandarEmail();
                res=0;
            }
            em.getTransaction().commit();
            em.close();
        }catch(Exception ex){
            Logger.getLogger(InsertarRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
            res=2;
        }
        
    
        return res;
    }
    
    
     //Crear fecha
    private Date nacimiento(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(nacimiento);
        } catch (ParseException ex) {
            Logger.getLogger(InsertarRecepcionista.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }
    
    //Metodo para enviar correo de verificacion
        private boolean mandarEmail(){
        final String Username = "gym.monitor.no.reply@gmail.com";
        final String PassWord = "PanditasRojos";
        Properties props = new Properties(); 
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator()
                {
                     @Override
                     protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Username, PassWord);
                    }
                });
        
        try{
            
            BodyPart mime = new MimeBodyPart();
            mime.setText("******GymMonitor******\n");
            BodyPart mime2 = new MimeBodyPart();
            mime2.setText("Contraseña temporal: 3edede1");
            MimeMultipart message = new MimeMultipart();
            message.addBodyPart(mime);
            message.addBodyPart(mime2);
            
            Message struct = new MimeMessage(session);
            struct.setFrom(new InternetAddress(Username));
            struct.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(email));
            struct.setSubject("Correo de verificacion");
            struct.setContent(message);
            Transport.send(struct);
 
        }catch(MessagingException ex){
            Logger.getLogger(RecuperarContraseña.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
