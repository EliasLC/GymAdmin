package com.mycompany.persistencia;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author elias
     */
@Embeddable
public class PeriodoSucId implements Serializable{

  
    @Column(name="PS_TSCID")
    private int psTscid;
    
  
    public int getPsTscid() {
        return psTscid;
    }

  
    public void setPsTscid(int psTscid) {
        this.psTscid = psTscid;
    }
       
    
    /*// @JoinColumn(name="TSUC_ID",referencedColumnName="")
    private TipoSuc tipoSuc;

    @ManyToOne()
    public TipoSuc getTipoSuc() {
        return tipoSuc;
    }

    public void setTipoSuc(TipoSuc tipoSuc) {
        this.tipoSuc = tipoSuc;
    }*/
}
