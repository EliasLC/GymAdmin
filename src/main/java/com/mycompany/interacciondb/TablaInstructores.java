
package com.mycompany.interacciondb;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author elias
 */
public class TablaInstructores {

    
    private StringProperty Nombre,TelMovil,TelFijo, Email,Direccion;
    
    public TablaInstructores(String nombre,String paterno, String materno,String telmovil, String telfijo, String email, 
            String colonia, String manzana, String lote  ){
        this.Nombre= new SimpleStringProperty(nombre+" "+paterno+" "+materno);
        this.TelMovil = new SimpleStringProperty(telmovil);
        this.TelFijo= new SimpleStringProperty(telfijo);
        this.Email= new SimpleStringProperty(email);
        this.Direccion= new SimpleStringProperty(colonia+" M#"+manzana+" L#");
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
   
    /**
     * @return the Direccion
     */
    public String getDireccion() {
        return Direccion.get();
    }

    /**
     * @param Direccion the Direccion to set
     */
    public void setDireccion(String Direccion) {
        this.Direccion = new SimpleStringProperty(Direccion);
    }
}
