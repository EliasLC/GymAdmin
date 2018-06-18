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
 *
 * @author elias
 */
@Entity
@Table(name = "DIRECCION_REC")
public class Direccion_REC implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "DREC_RECID")
    private Recepcionista recepcionista;
    
    @Basic(optional = false)
    @Column(name="DREC_COLONIA")
    private String colonia;
    
    @Column(name="DREC_MAN")
    private String manzana;
    
    @Column(name="DREC_LOTE")
    private String lote;

  public Recepcionista getRecepcionista() {
        return recepcionista;
    }

    public void setRecepcionista(Recepcionista recepcionista) {
        this.recepcionista = recepcionista;
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
