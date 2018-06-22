package com.mycompany.interacciondb;

import com.mycompany.persistencia.DataBase;
import com.mycompany.persistencia.Direccion_SUS;
import com.mycompany.persistencia.Instructor;
import com.mycompany.persistencia.Instruidos;
import com.mycompany.persistencia.Recepcionista;
import com.mycompany.persistencia.Suscripcion;
import com.mycompany.persistencia.Suscriptor;
import com.mycompany.persistencia.TipoSuc;
import com.mycompany.persistencia.TransaccionesRecep;
import java.util.Date;
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
public class RegistrarSuscriptor extends Task<Integer> {
    private String nom, paterno, materno,colonia,manzana,lote,celular,fijo,email;
    private Date nacimiento,duracion;
    private int idTipoSus,idInstructor;
    private float costo;
    
    @Override
    protected Integer call() throws Exception {
         return resultado();
    }
    
    private int resultado(){
        
        int res =1;
        try{ 
        EntityManager em = DataBase.getEMF().createEntityManager();
        em.getTransaction().begin();
        Query query = em.createNamedQuery("Suscriptor.findBySusEmail");
        query.setParameter("susEmail", email);
        List<Suscriptor> list = query.getResultList();
        if(list.isEmpty()){
            Suscriptor s= new Suscriptor();
            s.setSusNom(nom); s.setSUSAPat(paterno);
            s.setSUSAMat(materno); s.setSUSFechaNA(nacimiento);
            s.setSusEmail(email); s.setSusContra("LOLLY");
            s.setSusTelm(fijo); s.setSUSTELCelular(celular);
            Direccion_SUS sd = new Direccion_SUS();
            sd.setSuscriptor(s); sd.setColonia(colonia);
            sd.setManzana(manzana); sd.setLote(lote);
            s.setDireccionsus(sd);
            Instructor i = em.find(Instructor.class, idInstructor);
            Instruidos in = new Instruidos();
            in.setInstructor(i); in.setSuscriptor(s);
             em.persist(s); em.persist(sd); em.persist(i);
             em.persist(in);
            Query rep = em.createQuery("SELECT s FROM Suscripcion s WHERE s.sucSusid.susId = :id");
            rep.setParameter("id", s.getSusId());
            List<Suscripcion> lista = rep.getResultList();
            Suscripcion sx = lista.get(0);
            sx.setSucTsuc(em.find(TipoSuc.class, idTipoSus));
            sx.setSucFfp(duracion); 
            Query fin = em.createQuery("SELECT s FROM Recepcionista s WHERE s.recStatus = :status");
            fin.setParameter("status", 2);
            List<Recepcionista> rcep = fin.getResultList();
            Recepcionista w = rcep.get(0);
            TransaccionesRecep tr = new TransaccionesRecep();
            tr.setTrFecha(new Date()); tr.setTrRecid(w); tr.setTrTipo("Ingreso_Adm");
            tr.setTrImp(costo);
            em.persist(tr);
            mandarEmail();
            res=0;
        }
        em.getTransaction().commit();
        em.close();
         
        }catch(Exception e){
            Logger.getLogger(RegistrarSuscriptor.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("neñ");
            res = 2;
        }
        
        return res;
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
            mime2.setText("Contraseña de suscriptor temporal: LOLLY");
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
    
     /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the paterno
     */
    public String getPaterno() {
        return paterno;
    }

    /**
     * @param paterno the paterno to set
     */
    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    /**
     * @return the materno
     */
    public String getMaterno() {
        return materno;
    }

    /**
     * @param materno the materno to set
     */
    public void setMaterno(String materno) {
        this.materno = materno;
    }

    /**
     * @return the colonia
     */
    public String getColonia() {
        return colonia;
    }

    /**
     * @param colonia the colonia to set
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * @return the manzana
     */
    public String getManzana() {
        return manzana;
    }

    /**
     * @param manzana the manzana to set
     */
    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    /**
     * @return the lote
     */
    public String getLote() {
        return lote;
    }

    /**
     * @param lote the lote to set
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     * @return the celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * @return the fijo
     */
    public String getFijo() {
        return fijo;
    }

    /**
     * @param fijo the fijo to set
     */
    public void setFijo(String fijo) {
        this.fijo = fijo;
    }

    /**
     * @return the nacimiento
     */
    public Date getNacimiento() {
        return nacimiento;
    }

    /**
     * @param nacimiento the nacimiento to set
     */
    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    /**
     * @return the idTipoSus
     */
    public int getIdTipoSus() {
        return idTipoSus;
    }

    /**
     * @param idTipoSus the idTipoSus to set
     */
    public void setIdTipoSus(int idTipoSus) {
        this.idTipoSus = idTipoSus;
    }

    /**
     * @return the idInstructor
     */
    public int getIdInstructor() {
        return idInstructor;
    }

    /**
     * @param idInstructor the idInstructor to set
     */
    public void setIdInstructor(int idInstructor) {
        this.idInstructor = idInstructor;
    }

    /**
     * @return the duracion
     */
    public Date getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(Date duracion) {
        this.duracion = duracion;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
        /**
     * @return the costo
     */
    public float getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(float costo) {
        this.costo = costo;
    }
}
