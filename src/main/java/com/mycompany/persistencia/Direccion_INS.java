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
@Table(name = "DIRECCION_INS")
public class Direccion_INS implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "DINS_INSID")
    private Instructor instructor;
    
    @Basic(optional = false)
    @Column(name="DINS_COLONIA")
    private String colonia;
    
    @Column(name="DINS_MAN")
    private String manzana;
    
    @Column(name="DINS_LOTE")
    private String lote;
    
     /**
     * @return the instructor
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * @param instructor the instructor to set
     */
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    /**
     * @return the colonia
     */
    public String getColonia() {
        return colonia;
    }

    /**
     * @param colonia the colonia to set
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * @return the manzana
     */
    public String getManzana() {
        return manzana;
    }

    /**
     * @param manzana the manzana to set
     */
    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    /**
     * @return the lote
     */
    public String getLote() {
        return lote;
    }

    /**
     * @param lote the lote to set
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

}
