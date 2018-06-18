package com.mycompany.interacciondb;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author elias
 */
public class TablaPrecios {
 
    private StringProperty duracion,  precio;

    
    public TablaPrecios( String duracion, double precio){
        this.duracion= new SimpleStringProperty(duracion);
        this.precio = new SimpleStringProperty(String.valueOf(precio));
    }
   
    public String getDuracion() {
        return duracion.get();
    }

    public void setDuracion(String duracion) {
        this.duracion = new SimpleStringProperty(duracion);
    }
    
    public String getPrecio() {
        return precio.get();
    }

    public void setPrecio(String precio) {
        this.precio = new SimpleStringProperty(precio);
    }
}