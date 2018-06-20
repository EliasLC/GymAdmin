/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author elias
 */
@Entity
@Table(name = "PERIODO_TIPOSUC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodoTiposuc.findAll", query = "SELECT p FROM PeriodoTiposuc p")
    , @NamedQuery(name = "PeriodoTiposuc.findByPtDuracion", query = "SELECT p FROM PeriodoTiposuc p WHERE p.ptDuracion = :ptDuracion")
    , @NamedQuery(name = "PeriodoTiposuc.findByPtPrecio", query = "SELECT p FROM PeriodoTiposuc p WHERE p.ptPrecio = :ptPrecio")
    , @NamedQuery(name = "PeriodoTiposuc.findByPtId", query = "SELECT p FROM PeriodoTiposuc p WHERE p.ptId = :ptId")})
public class PeriodoTiposuc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "PT_DURACION")
    private String ptDuracion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PT_PRECIO")
    private Float ptPrecio;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PT_ID")
    private Integer ptId;
    @JoinColumn(name = "TSUC_ID", referencedColumnName = "TSUC_ID")
    @ManyToOne
    private TipoSuc tsucId;

    public PeriodoTiposuc() {
    }

    public PeriodoTiposuc(Integer ptId) {
        this.ptId = ptId;
    }

    public String getPtDuracion() {
        return ptDuracion;
    }

    public void setPtDuracion(String ptDuracion) {
        this.ptDuracion = ptDuracion;
    }

    public Float getPtPrecio() {
        return ptPrecio;
    }

    public void setPtPrecio(Float ptPrecio) {
        this.ptPrecio = ptPrecio;
    }

    public Integer getPtId() {
        return ptId;
    }

    public void setPtId(Integer ptId) {
        this.ptId = ptId;
    }

    public TipoSuc getTsucId() {
        return tsucId;
    }

    public void setTsucId(TipoSuc tsucId) {
        this.tsucId = tsucId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ptId != null ? ptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodoTiposuc)) {
            return false;
        }
        PeriodoTiposuc other = (PeriodoTiposuc) object;
        if ((this.ptId == null && other.ptId != null) || (this.ptId != null && !this.ptId.equals(other.ptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.PeriodoTiposuc[ ptId=" + ptId + " ]";
    }
    
}
