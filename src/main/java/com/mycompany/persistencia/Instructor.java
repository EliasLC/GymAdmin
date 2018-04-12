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
@Table(name = "INSTRUCTOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instructor.findAll", query = "SELECT i FROM Instructor i")
    , @NamedQuery(name = "Instructor.findByInsId", query = "SELECT i FROM Instructor i WHERE i.insId = :insId")
    , @NamedQuery(name = "Instructor.findByInsContra", query = "SELECT i FROM Instructor i WHERE i.insContra = :insContra")
    , @NamedQuery(name = "Instructor.findByInsEmail", query = "SELECT i FROM Instructor i WHERE i.insEmail = :insEmail")
    , @NamedQuery(name = "Instructor.findByInsNom", query = "SELECT i FROM Instructor i WHERE i.insNom = :insNom")
    , @NamedQuery(name = "Instructor.findByInsApat", query = "SELECT i FROM Instructor i WHERE i.insApat = :insApat")
    , @NamedQuery(name = "Instructor.findByInsAm", query = "SELECT i FROM Instructor i WHERE i.insAm = :insAm")
    , @NamedQuery(name = "Instructor.findByInsFna", query = "SELECT i FROM Instructor i WHERE i.insFna = :insFna")
    , @NamedQuery(name = "Instructor.findByInsTelm", query = "SELECT i FROM Instructor i WHERE i.insTelm = :insTelm")
    , @NamedQuery(name = "Instructor.findByInsTelc", query = "SELECT i FROM Instructor i WHERE i.insTelc = :insTelc")})
public class Instructor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "INS_ID")
    private Integer insId;
    @Basic(optional = false)
    @Column(name = "INS_CONTRA")
    private String insContra;
    @Basic(optional = false)
    @Column(name = "INS_EMAIL")
    private String insEmail;
    @Basic(optional = false)
    @Column(name = "INS_NOM")
    private String insNom;
    @Basic(optional = false)
    @Column(name = "INS_APAT")
    private String insApat;
    @Basic(optional = false)
    @Column(name = "INS_AM")
    private String insAm;
    @Basic(optional = false)
    @Column(name = "INS_FNA")
    @Temporal(TemporalType.DATE)
    private Date insFna;
    @Column(name = "INS_TELM")
    private String insTelm;
    @Column(name = "INS_TELC")
    private String insTelc;
    @OneToMany(mappedBy = "msiInsid")
    private Collection<MensajeSusIns> mensajeSusInsCollection;
    @OneToMany(mappedBy = "reiInsid")
    private Collection<RegimenInstructor> regimenInstructorCollection;
    @OneToMany(mappedBy = "ruiInsid")
    private Collection<RutinaInstructor> rutinaInstructorCollection;

    public Instructor() {
    }

    public Instructor(Integer insId) {
        this.insId = insId;
    }

    public Instructor(Integer insId, String insContra, String insEmail, String insNom, String insApat, String insAm, Date insFna) {
        this.insId = insId;
        this.insContra = insContra;
        this.insEmail = insEmail;
        this.insNom = insNom;
        this.insApat = insApat;
        this.insAm = insAm;
        this.insFna = insFna;
    }

    public Integer getInsId() {
        return insId;
    }

    public void setInsId(Integer insId) {
        this.insId = insId;
    }

    public String getInsContra() {
        return insContra;
    }

    public void setInsContra(String insContra) {
        this.insContra = insContra;
    }

    public String getInsEmail() {
        return insEmail;
    }

    public void setInsEmail(String insEmail) {
        this.insEmail = insEmail;
    }

    public String getInsNom() {
        return insNom;
    }

    public void setInsNom(String insNom) {
        this.insNom = insNom;
    }

    public String getInsApat() {
        return insApat;
    }

    public void setInsApat(String insApat) {
        this.insApat = insApat;
    }

    public String getInsAm() {
        return insAm;
    }

    public void setInsAm(String insAm) {
        this.insAm = insAm;
    }

    public Date getInsFna() {
        return insFna;
    }

    public void setInsFna(Date insFna) {
        this.insFna = insFna;
    }

    public String getInsTelm() {
        return insTelm;
    }

    public void setInsTelm(String insTelm) {
        this.insTelm = insTelm;
    }

    public String getInsTelc() {
        return insTelc;
    }

    public void setInsTelc(String insTelc) {
        this.insTelc = insTelc;
    }

    @XmlTransient
    public Collection<MensajeSusIns> getMensajeSusInsCollection() {
        return mensajeSusInsCollection;
    }

    public void setMensajeSusInsCollection(Collection<MensajeSusIns> mensajeSusInsCollection) {
        this.mensajeSusInsCollection = mensajeSusInsCollection;
    }

    @XmlTransient
    public Collection<RegimenInstructor> getRegimenInstructorCollection() {
        return regimenInstructorCollection;
    }

    public void setRegimenInstructorCollection(Collection<RegimenInstructor> regimenInstructorCollection) {
        this.regimenInstructorCollection = regimenInstructorCollection;
    }

    @XmlTransient
    public Collection<RutinaInstructor> getRutinaInstructorCollection() {
        return rutinaInstructorCollection;
    }

    public void setRutinaInstructorCollection(Collection<RutinaInstructor> rutinaInstructorCollection) {
        this.rutinaInstructorCollection = rutinaInstructorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (insId != null ? insId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Instructor)) {
            return false;
        }
        Instructor other = (Instructor) object;
        if ((this.insId == null && other.insId != null) || (this.insId != null && !this.insId.equals(other.insId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.Instructor[ insId=" + insId + " ]";
    }
    
}
