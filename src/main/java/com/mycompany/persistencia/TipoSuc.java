package com.mycompany.persistencia;
import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author elias
 */
@Entity
@Table(name = "TIPO_SUC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoSuc.findAll", query = "SELECT t FROM TipoSuc t")
    , @NamedQuery(name = "TipoSuc.findByTsucId", query = "SELECT t FROM TipoSuc t WHERE t.tsucId = :tsucId")
    , @NamedQuery(name = "TipoSuc.findByTsucNom", query = "SELECT t FROM TipoSuc t WHERE t.tsucNom = :tsucNom")
    , @NamedQuery(name = "TipoSuc.tablaSusc", query = "SELECT NEW com.mycompany.interacciondb.TablaSuscripciones(t.tsucId,t.tsucNom, t.tsusDesc, a.admNom, a.admApat, a.admAmat) FROM TipoSuc t, Administrador a WHERE t.tsusStatus = :tsusStatus AND t.tsucAdmid = a.admId")
    , @NamedQuery(name = "TipoSuc.findByTsusDesc", query = "SELECT t FROM TipoSuc t WHERE t.tsusDesc = :tsusDesc")
    , @NamedQuery(name = "TipoSuc.findByTsusStatus", query = "SELECT t FROM TipoSuc t WHERE t.tsusStatus = :tsusStatus")})
public class TipoSuc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TSUC_ID")
    private Integer tsucId;
    @Column(name = "TSUC_NOM")
    private String tsucNom;
    @Column(name = "TSUS_DESC")
    private String tsusDesc;
    @Column(name = "TSUS_STATUS")
    private Integer tsusStatus;
    @JoinColumn(name = "TSUC_ADMID", referencedColumnName = "ADM_ID")
    @ManyToOne
    private Administrador tsucAdmid;
    @OneToMany(mappedBy = "tsucId")
    private Collection<PeriodoTiposuc> periodoTiposucCollection;

    public TipoSuc() {
    }

    public TipoSuc(Integer tsucId) {
        this.tsucId = tsucId;
    }

    public Integer getTsucId() {
        return tsucId;
    }

    public void setTsucId(Integer tsucId) {
        this.tsucId = tsucId;
    }

    public String getTsucNom() {
        return tsucNom;
    }

    public void setTsucNom(String tsucNom) {
        this.tsucNom = tsucNom;
    }

    public String getTsusDesc() {
        return tsusDesc;
    }

    public void setTsusDesc(String tsusDesc) {
        this.tsusDesc = tsusDesc;
    }

    public Integer getTsusStatus() {
        return tsusStatus;
    }

    public void setTsusStatus(Integer tsusStatus) {
        this.tsusStatus = tsusStatus;
    }

    public Administrador getTsucAdmid() {
        return tsucAdmid;
    }

    public void setTsucAdmid(Administrador tsucAdmid) {
        this.tsucAdmid = tsucAdmid;
    }

    @XmlTransient
    public Collection<PeriodoTiposuc> getPeriodoTiposucCollection() {
        return periodoTiposucCollection;
    }

    public void setPeriodoTiposucCollection(Collection<PeriodoTiposuc> periodoTiposucCollection) {
        this.periodoTiposucCollection = periodoTiposucCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tsucId != null ? tsucId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoSuc)) {
            return false;
        }
        TipoSuc other = (TipoSuc) object;
        if ((this.tsucId == null && other.tsucId != null) || (this.tsucId != null && !this.tsucId.equals(other.tsucId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.TipoSuc[ tsucId=" + tsucId + " ]";
    }
    
}
