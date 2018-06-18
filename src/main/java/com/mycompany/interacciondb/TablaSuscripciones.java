package com.mycompany.interacciondb;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author elias
 */
public class TablaSuscripciones {

    private StringProperty nombre,descipcion,administrador; 
    private int id;

    public TablaSuscripciones(int id,String nombre, String descripcion, String nomAd,String paterno,String materno){
        this.nombre = new SimpleStringProperty(nombre); this.id=id;
        this.descipcion = new SimpleStringProperty(descripcion);
        this.administrador= new SimpleStringProperty(nomAd+" "+paterno+" "+materno);
    }
    
    public int getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public String getDescipcion() {
        return descipcion.get();
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = new SimpleStringProperty(descipcion);
    }

    public String getAdministrador() {
        return administrador.get();
    }
    
    public void setAdministrador(String administrador) {
        this.administrador = new SimpleStringProperty(administrador);
    }
}
