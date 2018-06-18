package com.mycompany.persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author elias
 */
@Entity
@Table(name = "DIRECCION_SUS")
public class Direccion_SUS implements Serializable {
    
    @Id
    @OneToOne
    @JoinColumn(name = "DSUS_SUSID")
    private Suscriptor suscriptor;
    
    @Basic(optional = false)
    @Column(name="DSUS_COLONIA")
    private String colonia;
    
    @Column(name="DSUS_MAN")
    private String manzana;
    
    @Column(name="DSUS_LOTE")
    private String lote;

    public Suscriptor getSuscriptor() {
        return suscriptor;
    }

    public void setSuscriptor(Suscriptor suscriptor) {
        this.suscriptor = suscriptor;
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
}
