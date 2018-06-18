package com.mycompany.interacciondb;

/**
 *
 * @author elias
 */
public class InfoSuscriptores {
    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    
    private String calle,celular,fijo, instructor;
    
    public InfoSuscriptores(String calle, String celular,
            String fijo){
   
        this.calle=" "+calle; this.celular=" "+ celular; this.fijo= " "+fijo;
    }
    
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }


    public String getCelular() {
        return celular;
    }


    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFijo() {
        return fijo;
    }

    public void setFijo(String fijo) {
        this.fijo = fijo;
    }
    
    

}
