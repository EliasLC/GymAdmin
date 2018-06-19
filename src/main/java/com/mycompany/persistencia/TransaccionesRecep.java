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
@Table(name = "TRANSACCIONES_RECEP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransaccionesRecep.findAll", query = "SELECT t FROM TransaccionesRecep t")
    , @NamedQuery(name = "TransaccionesRecep.findByTrId", query = "SELECT t FROM TransaccionesRecep t WHERE t.trId = :trId")
    , @NamedQuery(name = "TransaccionesRecep.findByTrFecha", query = "SELECT t FROM TransaccionesRecep t WHERE t.trFecha = :trFecha")
    , @NamedQuery(name = "TransaccionesRecep.findByTrImp", query = "SELECT t FROM TransaccionesRecep t WHERE t.trImp = :trImp")
    , @NamedQuery(name = "TransaccionesRecep.findByTrTipo", query = "SELECT t FROM TransaccionesRecep t WHERE t.trTipo = :trTipo")})
public class TransaccionesRecep implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TR_ID")
    private Integer trId;
    @Basic(optional = false)
    @Column(name = "TR_FECHA")
    @Temporal(TemporalType.DATE)
    private Date trFecha;
    @Basic(optional = false)
    @Column(name = "TR_IMP")
    private float trImp;
    @Basic(optional = false)
    @Column(name = "TR_TIPO")
    private String trTipo;
    @JoinColumn(name = "TR_RECID", referencedColumnName = "REC_ID")
    @ManyToOne
    private Recepcionista trRecid;

    public TransaccionesRecep() {
    }

    public TransaccionesRecep(Integer trId) {
        this.trId = trId;
    }

    public TransaccionesRecep(Integer trId, Date trFecha, float trImp, String trTipo) {
        this.trId = trId;
        this.trFecha = trFecha;
        this.trImp = trImp;
        this.trTipo = trTipo;
    }

    public Integer getTrId() {
        return trId;
    }

    public void setTrId(Integer trId) {
        this.trId = trId;
    }

    public Date getTrFecha() {
        return trFecha;
    }

    public void setTrFecha(Date trFecha) {
        this.trFecha = trFecha;
    }

    public float getTrImp() {
        return trImp;
    }

    public void setTrImp(float trImp) {
        this.trImp = trImp;
    }

    public String getTrTipo() {
        return trTipo;
    }

    public void setTrTipo(String trTipo) {
        this.trTipo = trTipo;
    }

    public Recepcionista getTrRecid() {
        return trRecid;
    }

    public void setTrRecid(Recepcionista trRecid) {
        this.trRecid = trRecid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trId != null ? trId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaccionesRecep)) {
            return false;
        }
        TransaccionesRecep other = (TransaccionesRecep) object;
        if ((this.trId == null && other.trId != null) || (this.trId != null && !this.trId.equals(other.trId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.TransaccionesRecep[ trId=" + trId + " ]";
    }
    
}
