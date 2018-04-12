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
@Table(name = "RUTINA_SUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RutinaSus.findAll", query = "SELECT r FROM RutinaSus r")
    , @NamedQuery(name = "RutinaSus.findByRusId", query = "SELECT r FROM RutinaSus r WHERE r.rusId = :rusId")
    , @NamedQuery(name = "RutinaSus.findByRusSusid", query = "SELECT r FROM RutinaSus r WHERE r.rusSusid = :rusSusid")
    , @NamedQuery(name = "RutinaSus.findByRusNom", query = "SELECT r FROM RutinaSus r WHERE r.rusNom = :rusNom")
    , @NamedQuery(name = "RutinaSus.findByRUSFCreacion", query = "SELECT r FROM RutinaSus r WHERE r.rUSFCreacion = :rUSFCreacion")
    , @NamedQuery(name = "RutinaSus.findByRusDesc", query = "SELECT r FROM RutinaSus r WHERE r.rusDesc = :rusDesc")})
public class RutinaSus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RUS_ID")
    private Integer rusId;
    @Column(name = "RUS_SUSID")
    private Integer rusSusid;
    @Basic(optional = false)
    @Column(name = "RUS_NOM")
    private String rusNom;
    @Basic(optional = false)
    @Column(name = "RUS_FCreacion")
    @Temporal(TemporalType.DATE)
    private Date rUSFCreacion;
    @Basic(optional = false)
    @Column(name = "RUS_DESC")
    private String rusDesc;

    public RutinaSus() {
    }

    public RutinaSus(Integer rusId) {
        this.rusId = rusId;
    }

    public RutinaSus(Integer rusId, String rusNom, Date rUSFCreacion, String rusDesc) {
        this.rusId = rusId;
        this.rusNom = rusNom;
        this.rUSFCreacion = rUSFCreacion;
        this.rusDesc = rusDesc;
    }

    public Integer getRusId() {
        return rusId;
    }

    public void setRusId(Integer rusId) {
        this.rusId = rusId;
    }

    public Integer getRusSusid() {
        return rusSusid;
    }

    public void setRusSusid(Integer rusSusid) {
        this.rusSusid = rusSusid;
    }

    public String getRusNom() {
        return rusNom;
    }

    public void setRusNom(String rusNom) {
        this.rusNom = rusNom;
    }

    public Date getRUSFCreacion() {
        return rUSFCreacion;
    }

    public void setRUSFCreacion(Date rUSFCreacion) {
        this.rUSFCreacion = rUSFCreacion;
    }

    public String getRusDesc() {
        return rusDesc;
    }

    public void setRusDesc(String rusDesc) {
        this.rusDesc = rusDesc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rusId != null ? rusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RutinaSus)) {
            return false;
        }
        RutinaSus other = (RutinaSus) object;
        if ((this.rusId == null && other.rusId != null) || (this.rusId != null && !this.rusId.equals(other.rusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.RutinaSus[ rusId=" + rusId + " ]";
    }
    
}
