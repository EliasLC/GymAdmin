package com.mycompany.interacciondb;

import com.mycompany.gymadmin.Datos;
import com.mycompany.persistencia.Administrador;
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
public class CambiaPass extends Task<Integer> {
    
    private String PassVieja,PassNueva;
    
    public CambiaPass(String PassVieja, String PassNueva){
        this.PassNueva= PassNueva; this.PassVieja= PassVieja;
    }
    
    /*Metodo utilizado para cambiar la contraseña. 
       Resultado 1: La contraseña se ha caambiado correctamente.
       Resultado 2: No se ha encontrado el usuario con dicha contraseña
       Resultado 3: No se ha podido conectar con el sevidor */
    @Override
    protected Integer call() throws Exception {
        return  exito();
    }
    
    private int exito(){
        int estado=2;
        try{
            EntityManager manager = DataBase.getEMF().createEntityManager();
            manager.getTransaction().begin();
            Query result= manager.createNamedQuery("Administrador.findByAdmContra");
            result.setParameter("admContra", PassVieja);
            result.setParameter("admId",Datos.getDatos().getId());
            List<Administrador> r = result.getResultList();
            if(r.size()>0){
                Administrador adm = r.get(0);
                adm.setAdmContra(PassNueva);
                manager.getTransaction().commit();
                mandarEmail();
                System.out.println("Hilooo");
                estado=1;
            }
            manager.close();
        }catch(Exception e){
            Logger.getLogger(RecuperarContraseña.class.getName()).log(Level.SEVERE, null, e);
            return 3;
        }
        return estado;
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
            mime2.setText("Nueva Contraseña: "+PassNueva);
            MimeMultipart message = new MimeMultipart();
            message.addBodyPart(mime);
            message.addBodyPart(mime2);
            
            Message struct = new MimeMessage(session);
            struct.setFrom(new InternetAddress(Username));
            struct.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(Datos.getDatos().getEmail()));
            struct.setSubject("Cambio contraseña");
            struct.setContent(message);
            Transport.send(struct);
 
        }catch(MessagingException ex){
            Logger.getLogger(RecuperarContraseña.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}