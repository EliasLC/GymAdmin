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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author elias
 */
@Entity
@Table(name = "REGIMEN_SUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegimenSus.findAll", query = "SELECT r FROM RegimenSus r")
    , @NamedQuery(name = "RegimenSus.findByResId", query = "SELECT r FROM RegimenSus r WHERE r.resId = :resId")
    , @NamedQuery(name = "RegimenSus.findByResSusId", query = "SELECT r FROM RegimenSus r WHERE r.resSusId = :resSusId")
    , @NamedQuery(name = "RegimenSus.findByResInom", query = "SELECT r FROM RegimenSus r WHERE r.resInom = :resInom")
    , @NamedQuery(name = "RegimenSus.findByResFc", query = "SELECT r FROM RegimenSus r WHERE r.resFc = :resFc")
    , @NamedQuery(name = "RegimenSus.findByResDes", query = "SELECT r FROM RegimenSus r WHERE r.resDes = :resDes")})
public class RegimenSus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RES_ID")
    private Integer resId;
    @Column(name = "RES_SUS_ID")
    private Integer resSusId;
    @Column(name = "RES_INOM")
    private String resInom;
    @Column(name = "RES_FC")
    @Temporal(TemporalType.DATE)
    private Date resFc;
    @Column(name = "RES_DES")
    private String resDes;
    @JoinColumn(name = "RES_ID", referencedColumnName = "SUS_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Suscriptor suscriptor;

    public RegimenSus() {
    }

    public RegimenSus(Integer resId) {
        this.resId = resId;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getResSusId() {
        return resSusId;
    }

    public void setResSusId(Integer resSusId) {
        this.resSusId = resSusId;
    }

    public String getResInom() {
        return resInom;
    }

    public void setResInom(String resInom) {
        this.resInom = resInom;
    }

    public Date getResFc() {
        return resFc;
    }

    public void setResFc(Date resFc) {
        this.resFc = resFc;
    }

    public String getResDes() {
        return resDes;
    }

    public void setResDes(String resDes) {
        this.resDes = resDes;
    }

    public Suscriptor getSuscriptor() {
        return suscriptor;
    }

    public void setSuscriptor(Suscriptor suscriptor) {
        this.suscriptor = suscriptor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resId != null ? resId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegimenSus)) {
            return false;
        }
        RegimenSus other = (RegimenSus) object;
        if ((this.resId == null && other.resId != null) || (this.resId != null && !this.resId.equals(other.resId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.RegimenSus[ resId=" + resId + " ]";
    }
    
}
