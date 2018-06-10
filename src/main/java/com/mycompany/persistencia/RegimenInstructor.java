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
@Table(name = "REGIMEN_INSTRUCTOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegimenInstructor.findAll", query = "SELECT r FROM RegimenInstructor r")
    , @NamedQuery(name = "RegimenInstructor.findByReiId", query = "SELECT r FROM RegimenInstructor r WHERE r.reiId = :reiId")
    , @NamedQuery(name = "RegimenInstructor.findByReiInom", query = "SELECT r FROM RegimenInstructor r WHERE r.reiInom = :reiInom")
    , @NamedQuery(name = "RegimenInstructor.findByReiFc", query = "SELECT r FROM RegimenInstructor r WHERE r.reiFc = :reiFc")
    , @NamedQuery(name = "RegimenInstructor.findByReiDes", query = "SELECT r FROM RegimenInstructor r WHERE r.reiDes = :reiDes")
    , @NamedQuery(name = "RegimenInstructor.findByReiStatus", query = "SELECT r FROM RegimenInstructor r WHERE r.reiStatus = :reiStatus")})
public class RegimenInstructor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "REI_ID")
    private Integer reiId;
    @Basic(optional = false)
    @Column(name = "REI_INOM")
    private String reiInom;
    @Basic(optional = false)
    @Column(name = "REI_FC")
    @Temporal(TemporalType.DATE)
    private Date reiFc;
    @Basic(optional = false)
    @Column(name = "REI_DES")
    private String reiDes;
    @Column(name = "REI_STATUS")
    private Integer reiStatus;
    @JoinColumn(name = "REI_INSID", referencedColumnName = "INS_ID")
    @ManyToOne
    private Instructor reiInsid;

    public RegimenInstructor() {
    }

    public RegimenInstructor(Integer reiId) {
        this.reiId = reiId;
    }

    public RegimenInstructor(Integer reiId, String reiInom, Date reiFc, String reiDes) {
        this.reiId = reiId;
        this.reiInom = reiInom;
        this.reiFc = reiFc;
        this.reiDes = reiDes;
    }

    public Integer getReiId() {
        return reiId;
    }

    public void setReiId(Integer reiId) {
        this.reiId = reiId;
    }

    public String getReiInom() {
        return reiInom;
    }

    public void setReiInom(String reiInom) {
        this.reiInom = reiInom;
    }

    public Date getReiFc() {
        return reiFc;
    }

    public void setReiFc(Date reiFc) {
        this.reiFc = reiFc;
    }

    public String getReiDes() {
        return reiDes;
    }

    public void setReiDes(String reiDes) {
        this.reiDes = reiDes;
    }

    public Integer getReiStatus() {
        return reiStatus;
    }

    public void setReiStatus(Integer reiStatus) {
        this.reiStatus = reiStatus;
    }

    public Instructor getReiInsid() {
        return reiInsid;
    }

    public void setReiInsid(Instructor reiInsid) {
        this.reiInsid = reiInsid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reiId != null ? reiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegimenInstructor)) {
            return false;
        }
        RegimenInstructor other = (RegimenInstructor) object;
        if ((this.reiId == null && other.reiId != null) || (this.reiId != null && !this.reiId.equals(other.reiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.RegimenInstructor[ reiId=" + reiId + " ]";
    }
    
}
