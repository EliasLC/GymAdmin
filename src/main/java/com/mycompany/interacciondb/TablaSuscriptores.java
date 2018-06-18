package com.mycompany.interacciondb;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author elias
 */
public class TablaSuscriptores {

    private StringProperty nombre, edad,email,colonia,mza,lote,tipoSus,fechaVen;
    private int id;

    
    public TablaSuscriptores(int id, String nom, String paterno,String materno,Date nacimiento, String email,
            String colonia,String mza,String lote, String tipoSus, Date fechaven){
        this.nombre = new SimpleStringProperty(nom+ " "+ paterno +" "+ materno);
        this.edad = new SimpleStringProperty(age(nacimiento));
        this.email = new SimpleStringProperty(email);
        this.colonia= new SimpleStringProperty(colonia);
        this.mza = new SimpleStringProperty(""+mza);
        this.lote= new SimpleStringProperty(""+lote);
        this.fechaVen= new SimpleStringProperty(String.valueOf(obtenerDia(fechaven))+"/" +
                String.valueOf(obtenerMes(fechaven))+"/"+ String.valueOf(obtenerAño(fechaven)));
        this.tipoSus= new SimpleStringProperty(tipoSus);
        this.id= id;
    }
    
    
     private String age(Date date){
        String age="";
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(obtenerAño(date), Month.of(obtenerMes(date)), obtenerDia(date));
        Period p = Period.between(birthday, today);
        age= String.valueOf(p.getYears());
        return age;
    }
     
       private int obtenerDia(Date date){
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                return  cal.get(Calendar.DAY_OF_MONTH);
            }

            private int obtenerMes(Date date){
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                return  cal.get(Calendar.MONTH)+1;
            }

            private int obtenerAño(Date date){
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                return  cal.get(Calendar.YEAR);
            }
    
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public String getEdad() {
        return edad.get();
    }

    public void setEdad(String edad) {
        this.edad = new SimpleStringProperty(edad);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public String getColonia() {
        return colonia.get();
    }
    
    public void setColonia(String colonia) {
        this.colonia = new SimpleStringProperty(colonia);
    }

    public String getMza() {
        return mza.get();
    }

    public void setMza(String mza) {
        this.mza = new SimpleStringProperty(mza);
    }

    public String getLote() {
        return lote.get();
    }

    public void setLote(String lote) {
        this.lote = new SimpleStringProperty(lote);
    }

    public String getTipoSus() {
        return tipoSus.get();
    }

    public void setTipoSus(String tipoSus) {
        this.tipoSus = new SimpleStringProperty(tipoSus);
    }

    public String getFechaVen() {
        return fechaVen.get();
    }

    public void setFechaVen(String fechaVen) {
        this.fechaVen = new SimpleStringProperty(fechaVen);
    }
  
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
