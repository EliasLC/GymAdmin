package com.mycompany.interacciondb;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author elias
 */
public class TablaRecepcionista {

  
    private int id; 
    private String email,nombre,nom,materno, paterno,edad,telmovil,telfijo,colonia,manzana,lote,
            direccion,mes,dia,año;
    
    
    public TablaRecepcionista(String nom,String paterno,String materno,String email,Date nacimiento,
            String telfijo,String telmovil,String colonia,String mza, String lote){
        
        this.nom=nom; this.materno=materno; this.paterno= paterno; this.nombre= nom+" "+ paterno+" "+ materno;
        this.email=email; this.telfijo= telfijo; this.telmovil= telmovil; this.colonia= colonia; this.manzana= mza;
        this.lote= lote; this.direccion= this.colonia+" M#"+mza+" L#"+lote;
        this.dia = String.valueOf(obtenerDia(nacimiento)); this.mes= String.valueOf(obtenerMes(nacimiento));
        this.año= String.valueOf(obtenerAño(nacimiento)); this.edad= age(nacimiento);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }


    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getTelmovil() {
        return telmovil;
    }

    public void setTelmovil(String telmovil) {
        this.telmovil = telmovil;
    }

    public String getTelfijo() {
        return telfijo;
    }

    public void setTelfijo(String telfijo) {
        this.telfijo = telfijo;
    }


    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
    
     public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
    
  public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
     public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }
}
