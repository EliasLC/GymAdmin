/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "SUSCRIPTOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Suscriptor.findAll", query = "SELECT s FROM Suscriptor s")
    , @NamedQuery(name = "Suscriptor.findBySusId", query = "SELECT s FROM Suscriptor s WHERE s.susId = :susId")
    , @NamedQuery(name = "Suscriptor.findBySusContra", query = "SELECT s FROM Suscriptor s WHERE s.susContra = :susContra")
    , @NamedQuery(name = "Suscriptor.findBySusEmail", query = "SELECT s FROM Suscriptor s WHERE s.susEmail = :susEmail")
    , @NamedQuery(name = "Suscriptor.findBySusNom", query = "SELECT s FROM Suscriptor s WHERE s.susNom = :susNom")
    , @NamedQuery(name = "Suscriptor.findBySUSAPat", query = "SELECT s FROM Suscriptor s WHERE s.sUSAPat = :sUSAPat")
    , @NamedQuery(name = "Suscriptor.findBySUSAMat", query = "SELECT s FROM Suscriptor s WHERE s.sUSAMat = :sUSAMat")
    , @NamedQuery(name = "Suscriptor.findBySusAlt", query = "SELECT s FROM Suscriptor s WHERE s.susAlt = :susAlt")
    , @NamedQuery(name = "Suscriptor.findBySusPeso", query = "SELECT s FROM Suscriptor s WHERE s.susPeso = :susPeso")
    , @NamedQuery(name = "Suscriptor.findBySUSFechaNA", query = "SELECT s FROM Suscriptor s WHERE s.sUSFechaNA = :sUSFechaNA")
    , @NamedQuery(name = "Suscriptor.findBySusImc", query = "SELECT s FROM Suscriptor s WHERE s.susImc = :susImc")
    , @NamedQuery(name = "Suscriptor.findBySusTelm", query = "SELECT s FROM Suscriptor s WHERE s.susTelm = :susTelm")
    , @NamedQuery(name = "Suscriptor.findBySUSTELCelular", query = "SELECT s FROM Suscriptor s WHERE s.sUSTELCelular = :sUSTELCelular")})
public class Suscriptor implements Serializable {

    /**
     * @return the instructor
     */
    public List<Instruidos> getInstructor() {
        return instructor;
    }

    /**
     * @param instructor the instructor to set
     */
    public void setInstructor(List<Instruidos> instructor) {
        this.instructor = instructor;
    }

    /**
     * @return the direccionsus
     */
    public Direccion_SUS getDireccionsus() {
        return direccionsus;
    }

    /**
     * @param direccionsus the direccionsus to set
     */
    public void setDireccionsus(Direccion_SUS direccionsus) {
        this.direccionsus = direccionsus;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SUS_ID")
    private Integer susId;
    @Basic(optional = false)
    @Column(name = "SUS_CONTRA")
    private String susContra;
    @Basic(optional = false)
    @Column(name = "SUS_EMAIL")
    private String susEmail;
    @Basic(optional = false)
    @Column(name = "SUS_NOM")
    private String susNom;
    @Basic(optional = false)
    @Column(name = "SUS_APat")
    private String sUSAPat;
    @Basic(optional = false)
    @Column(name = "SUS_AMat")
    private String sUSAMat;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SUS_ALT")
    private Double susAlt;
    @Column(name = "SUS_PESO")
    private Double susPeso;
    @Basic(optional = false)
    @Column(name = "SUS_FechaNA")
    @Temporal(TemporalType.DATE)
    private Date sUSFechaNA;
    @Column(name = "SUS_IMC")
    private Double susImc;
    @Column(name = "SUS_TELM")
    private String susTelm;
    @Column(name = "SUS_TELCelular")
    private String sUSTELCelular;

    public Suscriptor() {
    }

    public Suscriptor(Integer susId) {
        this.susId = susId;
    }
    
     @OneToMany(mappedBy = "primaryKey.sus", fetch = FetchType.LAZY)
     private List<Instruidos> instructor = new ArrayList<Instruidos>();

     @OneToOne(mappedBy = "suscriptor",  
              fetch = FetchType.LAZY, optional = false)
        private Direccion_SUS direccionsus;


    public Suscriptor(Integer susId, String susContra, String susEmail, String susNom, String sUSAPat, String sUSAMat, Date sUSFechaNA) {
        this.susId = susId;
        this.susContra = susContra;
        this.susEmail = susEmail;
        this.susNom = susNom;
        this.sUSAPat = sUSAPat;
        this.sUSAMat = sUSAMat;
        this.sUSFechaNA = sUSFechaNA;
    }

    public Integer getSusId() {
        return susId;
    }

    public void setSusId(Integer susId) {
        this.susId = susId;
    }

    public String getSusContra() {
        return susContra;
    }

    public void setSusContra(String susContra) {
        this.susContra = susContra;
    }

    public String getSusEmail() {
        return susEmail;
    }

    public void setSusEmail(String susEmail) {
        this.susEmail = susEmail;
    }

    public String getSusNom() {
        return susNom;
    }

    public void setSusNom(String susNom) {
        this.susNom = susNom;
    }

    public String getSUSAPat() {
        return sUSAPat;
    }

    public void setSUSAPat(String sUSAPat) {
        this.sUSAPat = sUSAPat;
    }

    public String getSUSAMat() {
        return sUSAMat;
    }

    public void setSUSAMat(String sUSAMat) {
        this.sUSAMat = sUSAMat;
    }

    public Double getSusAlt() {
        return susAlt;
    }

    public void setSusAlt(Double susAlt) {
        this.susAlt = susAlt;
    }

    public Double getSusPeso() {
        return susPeso;
    }

    public void setSusPeso(Double susPeso) {
        this.susPeso = susPeso;
    }

    public Date getSUSFechaNA() {
        return sUSFechaNA;
    }

    public void setSUSFechaNA(Date sUSFechaNA) {
        this.sUSFechaNA = sUSFechaNA;
    }

    public Double getSusImc() {
        return susImc;
    }

    public void setSusImc(Double susImc) {
        this.susImc = susImc;
    }

    public String getSusTelm() {
        return susTelm;
    }

    public void setSusTelm(String susTelm) {
        this.susTelm = susTelm;
    }

    public String getSUSTELCelular() {
        return sUSTELCelular;
    }

    public void setSUSTELCelular(String sUSTELCelular) {
        this.sUSTELCelular = sUSTELCelular;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (susId != null ? susId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Suscriptor)) {
            return false;
        }
        Suscriptor other = (Suscriptor) object;
        if ((this.susId == null && other.susId != null) || (this.susId != null && !this.susId.equals(other.susId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.Suscriptor[ susId=" + susId + " ]";
    }
    
}
