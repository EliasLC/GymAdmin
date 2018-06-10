package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import java.util.List;
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
public class RecuperarContraseña extends Task<Integer> {

    private String destino,pass;
    
    public RecuperarContraseña(String destino){
        this.destino= destino; this.pass= "";
    }
    
    /*Metodo para enviar la contraseña recuperada
        Si el que retorna es 0 se ha enviado satisfactoriamente la contraseña
        Si es 1 el usuario ingresado no exite
        Si es 2 el mensaje no pudo enviiarse con exito
    */
    @Override
    protected Integer call() throws Exception {
        
        if(seEncuentra()){
           if(!mandarEmail()){
               return 2;
           }    
        } else{
           return 1; 
        }
        
        return 0;
    }
    
    //Metodo para comprobar que el usuario existe
    public boolean seEncuentra(){
        EntityManager manager = DataBase.getEMF().createEntityManager();
        manager.getTransaction().begin();
        Query result;
        result = manager.createQuery("SELECT a.admContra FROM Administrador a WHERE a.admEmail = :user");
        result.setParameter("user",destino);
        List<String>  re = result.getResultList();
        pass = re.get(0);
        for (int i=0; i<re.size(); i++){
            System.out.println(re.get(i));
        }
        manager.getTransaction().commit();
        manager.close();
        if(re.isEmpty()){
            return false;
        }
       
        return true;      
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
            mime2.setText("Contraseña: "+pass);
            MimeMultipart message = new MimeMultipart();
            message.addBodyPart(mime);
            message.addBodyPart(mime2);
            
            Message struct = new MimeMessage(session);
            struct.setFrom(new InternetAddress(Username));
            struct.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(destino));
            struct.setSubject("Recuperacion de contraseña");
            struct.setContent(message);
            
            System.out.println(destino);
            Transport.send(struct);
 
        }catch(MessagingException ex){
            Logger.getLogger(RecuperarContraseña.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}