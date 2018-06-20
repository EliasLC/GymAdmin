package com.mycompany.interacciondb;
/**
 * @author elias
 */
public class PeriodoSusCombo {
    
    private int id;
    private String duracion;
    private float costo;


    public PeriodoSusCombo(int id, String duracion, float costo){
        this.id= id; this.duracion= duracion; this.costo=costo;
    }
    
    public float getCosto() {
        return costo;
    }

  
    public void setCosto(float costo) {
        this.costo = costo;
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

    /**
     * @return the duracion
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    
    @Override
    public String toString(){
        return duracion;
    }
}
