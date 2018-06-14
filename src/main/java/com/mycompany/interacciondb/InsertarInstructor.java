package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.Direccion_INS;
import com.mycompany.persistencia.Instructor;
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
public class InsertarInstructor extends Task<Integer> {

     private String nombre, appat,apmat,movil,fijo,email, fecha,colonia,mzn,lote;
    
    public InsertarInstructor(String nombre, String appat,String apmat, 
        String movil,String fijo, String email,String fecha,String colonia,String mzn, String lote){
        this.nombre= nombre; this.appat=appat; this.apmat=apmat; this.movil= movil; this.fijo= fijo; this.email= email;
        this.fecha=fecha; this.colonia=colonia; this.mzn=mzn; this.lote=lote;
    }
    
    
    @Override
    protected Integer call() throws Exception {
        return insertar();
    }
    
    /*Metodo por el cual se ingresa un nuevo usuario a la base de datos
        Retorno 0: se ha ingresado correctamente el usuario
        Retorno 1: El email ingresado ya se encuentra utilizado por otro usuario 
        Retorno 2: No se ha podido realisar el INSERT
    */
    private int insertar(){
        int re = 0;
        try{
            
            EntityManager em = DataBase.getEMF().createEntityManager();
            em.getTransaction().begin();
            Query result= em.createNamedQuery("Instructor.findByInsEmail");
            result.setParameter("insEmail", email);
            if(result.getResultList().isEmpty()){
                Instructor in = new Instructor();
                Direccion_INS direccion = new Direccion_INS();
                in.setInsNom(nombre); in.setInsApat(appat); in.setInsAm(apmat);
                in.setInsContra("12345"); in.setInsEmail(email); in.setInsTelm(movil);
                in.setInsTelc(fijo);  in.setInsFna(nacimiento()); in.setInsStatus(1);
                direccion.setColonia(colonia); direccion.setLote(lote); direccion.setManzana(mzn);
                em.persist(in); em.persist(direccion); direccion.setInstructor(in);
                em.getTransaction().commit();
                em.close();
                mandarEmail();
            } else{
                re=1;
            }
            
        }catch(Exception e ){
            Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, e);
            re=2;
        }
        return re;
    }
    
    
       //Crear fecha
    private Date nacimiento(){
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(ModIAdm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }
    
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
            mime2.setText("Contraseña temporal: 0987");
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
