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
public class TablaInstruidos {
    
    
    private StringProperty nombre, edad, telFijo, telMovil, correo, finicio;
  
    
    public TablaInstruidos(String nombre,String paterno,String materno, Date edad, String telFijo, String telMovil, String correo, 
            Date finicio ){
     
         this.nombre= new SimpleStringProperty(nombre+" "+paterno+" "+materno); 
         this.edad= new SimpleStringProperty(age(edad));
         this.finicio= new SimpleStringProperty(String.valueOf(obtenerDia(finicio))+"-" +String.valueOf(obtenerMes(finicio))+"-"+String.valueOf(obtenerAño(finicio)));
         this.telFijo= new SimpleStringProperty(telFijo); 
         this.telMovil= new SimpleStringProperty(telMovil);
         this.correo= new SimpleStringProperty(correo);
    }
    
    private String age(Date date){
        String age="";
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(obtenerAño(date), Month.of(obtenerMes(date)), obtenerDia(date));
        Period p = Period.between(birthday, today);
        age= String.valueOf(p.getYears());
        System.out.println(age);
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

    public String getTelFijo() {
        return telFijo.get();
    }

    public void setTelFijo(String telFijo) {
        this.telFijo = new SimpleStringProperty(telFijo);
    }

    public String getTelMovil() {
        return telMovil.get();
    }

    public void setTelMovil(String telMovil) {
        this.telMovil = new SimpleStringProperty(telMovil);
    }

    public String getCorreo() {
        return correo.get();
    }

    public void setCorreo(String correo) {
        this.correo = new SimpleStringProperty(correo);
    }

    public String getFinicio() {
        return finicio.get();
    }
    
     public void setFinicio(String finicio) {
        this.finicio = new SimpleStringProperty(finicio);
    }
    
}
