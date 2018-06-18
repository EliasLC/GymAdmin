package com.mycompany.persistencia;

import java.io.Serializable;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author elias
 */
@Entity
@Table(name="PERIODO_TIPOSUC")
/*@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.tipoSuc",
        joinColumns = @JoinColumn(name = "PS_TSCID"))})*/
public class PeriodoSuc implements Serializable {

  

    
    @EmbeddedId
    private PeriodoSucId primaryKey;
    
    @JoinColumn(name="PS_TSCID",referencedColumnName="TSUC_ID",insertable = false, updatable = false)
    @ManyToOne
    private TipoSuc tipoSuc;
    @Column(name="PS_DURACION")
    private String psDuracion;
    
    @Column(name="PS_COSTO")
    private double psCosto;
    
   
    public PeriodoSucId getPrimaryKey(){
        return primaryKey;
    }
    
    public void setPrimaryKey(PeriodoSucId primaryKey){
        this.primaryKey= primaryKey;
    }
    
   
    public String getPsDuracion() {
        return psDuracion;
    }


    public void setPsDuracion(String psDuracion) {
        this.psDuracion = psDuracion;
    }


    public double getPsCosto() {
        return psCosto;
    }

    public void setPsCosto(double psCosto) {
        this.psCosto = psCosto;
    }
    
    public TipoSuc getTipoSuc() {
        return tipoSuc;
    }
    
    public void setTipoSuc(TipoSuc tipoSuc) {
        this.tipoSuc = tipoSuc;
    }  
    
}
