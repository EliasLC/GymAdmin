package com.mycompany.interacciondb;

import com.mycompany.persistencia.PeriodoTiposuc;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author elias
 */
public class TipoSuscripcionesCombo {

    private int id;
    private String nombre;
    private List<PeriodoTiposuc> periodoTiposucList;
    
    public TipoSuscripcionesCombo(int id, String nombre){
        this.id= id; this.nombre= nombre; 
    }
    
    /**
     * @return the periodoTiposucList
     */
    public List<PeriodoTiposuc> getPeriodoTiposucList() {
        return periodoTiposucList;
    }

    /**
     * @param periodoTiposucList the periodoTiposucList to set
     */
    public void setPeriodoTiposucList(List<PeriodoTiposuc> periodoTiposucList) {
        this.periodoTiposucList = periodoTiposucList;
    }

    public int getId() {
        return id;
    }

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
    public String toString(){
        return nombre;
    }

}
