/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.persistencia;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author elias
 */
@Entity
@Table(name = "ADMINISTRADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administrador.findAll", query = "SELECT a FROM Administrador a")
    , @NamedQuery(name = "Administrador.findByAdmId", query = "SELECT a FROM Administrador a WHERE a.admId = :admId")
    , @NamedQuery(name = "Administrador.findByAdmContra", query = "SELECT a FROM Administrador a WHERE a.admContra = :admContra")
    , @NamedQuery(name = "Administrador.findByAdmNom", query = "SELECT a FROM Administrador a WHERE a.admNom = :admNom")
    , @NamedQuery(name = "Administrador.findByAdmApat", query = "SELECT a FROM Administrador a WHERE a.admApat = :admApat")
    , @NamedQuery(name = "Administrador.findByAdmAmat", query = "SELECT a FROM Administrador a WHERE a.admAmat = :admAmat")
    , @NamedQuery(name = "Administrador.findByAdmFna", query = "SELECT a FROM Administrador a WHERE a.admFna = :admFna")
    , @NamedQuery(name = "Administrador.findByAdmTelm", query = "SELECT a FROM Administrador a WHERE a.admTelm = :admTelm")
    , @NamedQuery(name = "Administrador.findByAdmTelc", query = "SELECT a FROM Administrador a WHERE a.admTelc = :admTelc")
    , @NamedQuery(name = "Administrador.findByAdmEmail", query = "SELECT a.admContra FROM Administrador a WHERE a.admEmail = :admEmail")
    , @NamedQuery(name = "Administrador.findByAdmEstado", query = "SELECT a FROM Administrador a WHERE a.admEstado = :admEstado")})
public class Administrador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ADM_ID")
    private Integer admId;
    @Basic(optional = false)
    @Column(name = "ADM_CONTRA")
    private String admContra;
    @Basic(optional = false)
    @Column(name = "ADM_EMAIL")
    private String admEmail;
    @Basic(optional = false)
    @Column(name = "ADM_NOM")
    private String admNom;
    @Basic(optional = false)
    @Column(name = "ADM_APAT")
    private String admApat;
    @Basic(optional = false)
    @Column(name = "ADM_AMAT")
    private String admAmat;
    @Basic(optional = false)
    @Column(name = "ADM_FNA")
    @Temporal(TemporalType.DATE)
    private Date admFna;
    @Column(name = "ADM_TELM")
    private String admTelm;
    @Column(name = "ADM_TELC")
    private String admTelc;
    @Column(name = "ADM_ESTADO")
    private String admEstado;
    @OneToMany(mappedBy = "tsucAdmid")
    private Collection<TipoSuc> tipoSucCollection;

    public Administrador() {
    }

    public Administrador(Integer admId) {
        this.admId = admId;
    }

    public Administrador(Integer admId, String admContra, String admEmail, String admNom, String admApat, String admAmat, Date admFna) {
        this.admId = admId;
        this.admContra = admContra;
        this.admEmail = admEmail;
        this.admNom = admNom;
        this.admApat = admApat;
        this.admAmat = admAmat;
        this.admFna = admFna;
    }

    public Integer getAdmId() {
        return admId;
    }

    public void setAdmId(Integer admId) {
        this.admId = admId;
    }

    public String getAdmContra() {
        return admContra;
    }

    public void setAdmContra(String admContra) {
        this.admContra = admContra;
    }

    public String getAdmEmail() {
        return admEmail;
    }

    public void setAdmEmail(String admEmail) {
        this.admEmail = admEmail;
    }

    public String getAdmNom() {
        return admNom;
    }

    public void setAdmNom(String admNom) {
        this.admNom = admNom;
    }

    public String getAdmApat() {
        return admApat;
    }

    public void setAdmApat(String admApat) {
        this.admApat = admApat;
    }

    public String getAdmAmat() {
        return admAmat;
    }

    public void setAdmAmat(String admAmat) {
        this.admAmat = admAmat;
    }

    public Date getAdmFna() {
        return admFna;
    }

    public void setAdmFna(Date admFna) {
        this.admFna = admFna;
    }

    public String getAdmTelm() {
        return admTelm;
    }

    public void setAdmTelm(String admTelm) {
        this.admTelm = admTelm;
    }

    public String getAdmTelc() {
        return admTelc;
    }

    public void setAdmTelc(String admTelc) {
        this.admTelc = admTelc;
    }

    public String getAdmEstado() {
        return admEstado;
    }

    public void setAdmEstado(String admEstado) {
        this.admEstado = admEstado;
    }

    @XmlTransient
    public Collection<TipoSuc> getTipoSucCollection() {
        return tipoSucCollection;
    }

    public void setTipoSucCollection(Collection<TipoSuc> tipoSucCollection) {
        this.tipoSucCollection = tipoSucCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (admId != null ? admId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrador)) {
            return false;
        }
        Administrador other = (Administrador) object;
        if ((this.admId == null && other.admId != null) || (this.admId != null && !this.admId.equals(other.admId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.Administrador[ admId=" + admId + " ]";
    }
    
}
