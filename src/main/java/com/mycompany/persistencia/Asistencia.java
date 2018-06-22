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
@Table(name = "ASISTENCIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asistencia.findAll", query = "SELECT a FROM Asistencia a")
    , @NamedQuery(name = "Asistencia.findByIdAsis", query = "SELECT a FROM Asistencia a WHERE a.idAsis = :idAsis")
    , @NamedQuery(name = "Asistencia.findByFecha", query = "SELECT a FROM Asistencia a WHERE a.fecha = :fecha")})
public class Asistencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ASIS")
    private Integer idAsis;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "SUS_ID", referencedColumnName = "SUS_ID")
    @ManyToOne
    private Suscriptor susId;

    public Asistencia() {
    }

    public Asistencia(Integer idAsis) {
        this.idAsis = idAsis;
    }

    public Integer getIdAsis() {
        return idAsis;
    }

    public void setIdAsis(Integer idAsis) {
        this.idAsis = idAsis;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Suscriptor getSusId() {
        return susId;
    }

    public void setSusId(Suscriptor susId) {
        this.susId = susId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsis != null ? idAsis.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asistencia)) {
            return false;
        }
        Asistencia other = (Asistencia) object;
        if ((this.idAsis == null && other.idAsis != null) || (this.idAsis != null && !this.idAsis.equals(other.idAsis))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.Asistencia[ idAsis=" + idAsis + " ]";
    }
    
}
