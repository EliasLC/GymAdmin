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
@Table(name = "RUTINA_INSTRUCTOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RutinaInstructor.findAll", query = "SELECT r FROM RutinaInstructor r")
    , @NamedQuery(name = "RutinaInstructor.findByRuiId", query = "SELECT r FROM RutinaInstructor r WHERE r.ruiId = :ruiId")
    , @NamedQuery(name = "RutinaInstructor.findByRuiInom", query = "SELECT r FROM RutinaInstructor r WHERE r.ruiInom = :ruiInom")
    , @NamedQuery(name = "RutinaInstructor.findByRuiFc", query = "SELECT r FROM RutinaInstructor r WHERE r.ruiFc = :ruiFc")
    , @NamedQuery(name = "RutinaInstructor.findByRuiDes", query = "SELECT r FROM RutinaInstructor r WHERE r.ruiDes = :ruiDes")
    , @NamedQuery(name = "RutinaInstructor.findByRuiStatus", query = "SELECT r FROM RutinaInstructor r WHERE r.ruiStatus = :ruiStatus")})
public class RutinaInstructor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RUI_ID")
    private Integer ruiId;
    @Basic(optional = false)
    @Column(name = "RUI_INOM")
    private String ruiInom;
    @Basic(optional = false)
    @Column(name = "RUI_FC")
    @Temporal(TemporalType.DATE)
    private Date ruiFc;
    @Basic(optional = false)
    @Column(name = "RUI_DES")
    private String ruiDes;
    @Column(name = "RUI_STATUS")
    private Integer ruiStatus;
    @JoinColumn(name = "RUI_INSID", referencedColumnName = "INS_ID")
    @ManyToOne
    private Instructor ruiInsid;

    public RutinaInstructor() {
    }

    public RutinaInstructor(Integer ruiId) {
        this.ruiId = ruiId;
    }

    public RutinaInstructor(Integer ruiId, String ruiInom, Date ruiFc, String ruiDes) {
        this.ruiId = ruiId;
        this.ruiInom = ruiInom;
        this.ruiFc = ruiFc;
        this.ruiDes = ruiDes;
    }

    public Integer getRuiId() {
        return ruiId;
    }

    public void setRuiId(Integer ruiId) {
        this.ruiId = ruiId;
    }

    public String getRuiInom() {
        return ruiInom;
    }

    public void setRuiInom(String ruiInom) {
        this.ruiInom = ruiInom;
    }

    public Date getRuiFc() {
        return ruiFc;
    }

    public void setRuiFc(Date ruiFc) {
        this.ruiFc = ruiFc;
    }

    public String getRuiDes() {
        return ruiDes;
    }

    public void setRuiDes(String ruiDes) {
        this.ruiDes = ruiDes;
    }

    public Integer getRuiStatus() {
        return ruiStatus;
    }

    public void setRuiStatus(Integer ruiStatus) {
        this.ruiStatus = ruiStatus;
    }

    public Instructor getRuiInsid() {
        return ruiInsid;
    }

    public void setRuiInsid(Instructor ruiInsid) {
        this.ruiInsid = ruiInsid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ruiId != null ? ruiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RutinaInstructor)) {
            return false;
        }
        RutinaInstructor other = (RutinaInstructor) object;
        if ((this.ruiId == null && other.ruiId != null) || (this.ruiId != null && !this.ruiId.equals(other.ruiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.RutinaInstructor[ ruiId=" + ruiId + " ]";
    }
    
}
