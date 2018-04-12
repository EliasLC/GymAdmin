/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.persistencia;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author elias
 */
@Entity
@Table(name = "MENSAJE_SUS_INS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MensajeSusIns.findAll", query = "SELECT m FROM MensajeSusIns m")
    , @NamedQuery(name = "MensajeSusIns.findByMsiId", query = "SELECT m FROM MensajeSusIns m WHERE m.msiId = :msiId")
    , @NamedQuery(name = "MensajeSusIns.findByMsiFenv", query = "SELECT m FROM MensajeSusIns m WHERE m.msiFenv = :msiFenv")
    , @NamedQuery(name = "MensajeSusIns.findByMsiMen", query = "SELECT m FROM MensajeSusIns m WHERE m.msiMen = :msiMen")
    , @NamedQuery(name = "MensajeSusIns.findByMsiTipo", query = "SELECT m FROM MensajeSusIns m WHERE m.msiTipo = :msiTipo")})
public class MensajeSusIns implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MSI_ID")
    private Integer msiId;
    @Basic(optional = false)
    @Column(name = "MSI_FENV")
    @Temporal(TemporalType.DATE)
    private Date msiFenv;
    @Basic(optional = false)
    @Column(name = "MSI_MEN")
    private String msiMen;
    @Basic(optional = false)
    @Column(name = "MSI_TIPO")
    private String msiTipo;
    @JoinColumn(name = "MSI_SUSID", referencedColumnName = "SUS_ID")
    @ManyToOne
    private Suscriptor msiSusid;
    @JoinColumn(name = "MSI_INSID", referencedColumnName = "INS_ID")
    @ManyToOne
    private Instructor msiInsid;

    public MensajeSusIns() {
    }

    public MensajeSusIns(Integer msiId) {
        this.msiId = msiId;
    }

    public MensajeSusIns(Integer msiId, Date msiFenv, String msiMen, String msiTipo) {
        this.msiId = msiId;
        this.msiFenv = msiFenv;
        this.msiMen = msiMen;
        this.msiTipo = msiTipo;
    }

    public Integer getMsiId() {
        return msiId;
    }

    public void setMsiId(Integer msiId) {
        this.msiId = msiId;
    }

    public Date getMsiFenv() {
        return msiFenv;
    }

    public void setMsiFenv(Date msiFenv) {
        this.msiFenv = msiFenv;
    }

    public String getMsiMen() {
        return msiMen;
    }

    public void setMsiMen(String msiMen) {
        this.msiMen = msiMen;
    }

    public String getMsiTipo() {
        return msiTipo;
    }

    public void setMsiTipo(String msiTipo) {
        this.msiTipo = msiTipo;
    }

    public Suscriptor getMsiSusid() {
        return msiSusid;
    }

    public void setMsiSusid(Suscriptor msiSusid) {
        this.msiSusid = msiSusid;
    }

    public Instructor getMsiInsid() {
        return msiInsid;
    }

    public void setMsiInsid(Instructor msiInsid) {
        this.msiInsid = msiInsid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msiId != null ? msiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MensajeSusIns)) {
            return false;
        }
        MensajeSusIns other = (MensajeSusIns) object;
        if ((this.msiId == null && other.msiId != null) || (this.msiId != null && !this.msiId.equals(other.msiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.MensajeSusIns[ msiId=" + msiId + " ]";
    }
    
}
