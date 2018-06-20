/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.interacciondb;

/**
 *
 * @author elias
 */
public class InstructoresCombo {

    private int id; 
    private String nombre;
    
    public InstructoresCombo(int id, String nom, String paterno, String materno){
        this.id= id;
        this.nombre = nom+" "+ paterno+" "+ materno;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
  

      @Override
    public String toString() {
         return nombre;
    }

}
