package com.mycompany.interacciondb;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author elias
 */
public class TablaAdministradores {
    
    private StringProperty Nombre,TelMovil,TelFijo,Email;
    
    
    public TablaAdministradores(String Nombre,String paterno, String materno, String TelMovil, String TelFijo, String Email){
        this.Nombre = new SimpleStringProperty(Nombre+" "+paterno+" "+materno); 
        this.TelMovil= new SimpleStringProperty(TelMovil);
        this.TelFijo = new SimpleStringProperty(TelFijo);
        this.Email = new SimpleStringProperty(Email);
        
    }
    
    
    public TablaAdministradores(){
    
    }
    public String getNombre() {
        return Nombre.get();
    }

   
    public void setNombre(String Nombre) {
        this.Nombre = new SimpleStringProperty(Nombre);
    }

    /**
     * @return the TelMovil
     */
    public String getTelMovil() {
        return TelMovil.get();
    }

    /**
     * @param TelMovil the TelMovil to set
     */
    public void setTelMovil(String TelMovil) {
        this.TelMovil = new SimpleStringProperty(TelMovil);
    }

    /**
     * @return the TelFijo
     */
    public String getTelFijo() {
        return TelFijo.get();
    }

    /**
     * @param TelFijo the TelFijo to set
     */
    public void setTelFijo(String TelFijo) {
        this.TelFijo = new SimpleStringProperty(TelFijo);
    }

    /**
     * @return the Email
     */
    public String getEmail() {
        return Email.get();
    }

    /**
     * @param Email the Email to set
     */
    public void setEmail(String Email) {
        this.Email = new SimpleStringProperty(Email) ;
    }
   
}
