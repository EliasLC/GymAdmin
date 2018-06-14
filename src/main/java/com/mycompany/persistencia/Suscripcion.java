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
@Table(name = "SUSCRIPCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Suscripcion.findAll", query = "SELECT s FROM Suscripcion s")
    , @NamedQuery(name = "Suscripcion.findBySucId", query = "SELECT s FROM Suscripcion s WHERE s.sucId = :sucId")
    , @NamedQuery(name = "Suscripcion.findBySucFip", query = "SELECT s FROM Suscripcion s WHERE s.sucFip = :sucFip")
    , @NamedQuery(name = "Suscripcion.findBySucFfp", query = "SELECT s FROM Suscripcion s WHERE s.sucFfp = :sucFfp")})
public class Suscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SUC_ID")
    private Integer sucId;
    @Basic(optional = false)
    @Column(name = "SUC_FIP")
    @Temporal(TemporalType.DATE)
    private Date sucFip;
    @Basic(optional = false)
    @Column(name = "SUC_FFP")
    @Temporal(TemporalType.DATE)
    private Date sucFfp;
    @JoinColumn(name = "SUC_SUSID", referencedColumnName = "SUS_ID")
    @ManyToOne
    private Suscriptor sucSusid;
    @JoinColumn(name = "SUC_TSUC", referencedColumnName = "TSUC_ID")
    @ManyToOne
    private TipoSuc sucTsuc;

    public Suscripcion() {
    }

    public Suscripcion(Integer sucId) {
        this.sucId = sucId;
    }

    public Suscripcion(Integer sucId, Date sucFip, Date sucFfp) {
        this.sucId = sucId;
        this.sucFip = sucFip;
        this.sucFfp = sucFfp;
    }

    public Integer getSucId() {
        return sucId;
    }

    public void setSucId(Integer sucId) {
        this.sucId = sucId;
    }

    public Date getSucFip() {
        return sucFip;
    }

    public void setSucFip(Date sucFip) {
        this.sucFip = sucFip;
    }

    public Date getSucFfp() {
        return sucFfp;
    }

    public void setSucFfp(Date sucFfp) {
        this.sucFfp = sucFfp;
    }

    public Suscriptor getSucSusid() {
        return sucSusid;
    }

    public void setSucSusid(Suscriptor sucSusid) {
        this.sucSusid = sucSusid;
    }

    public TipoSuc getSucTsuc() {
        return sucTsuc;
    }

    public void setSucTsuc(TipoSuc sucTsuc) {
        this.sucTsuc = sucTsuc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sucId != null ? sucId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Suscripcion)) {
            return false;
        }
        Suscripcion other = (Suscripcion) object;
        if ((this.sucId == null && other.sucId != null) || (this.sucId != null && !this.sucId.equals(other.sucId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.Suscripcion[ sucId=" + sucId + " ]";
    }
    
}
