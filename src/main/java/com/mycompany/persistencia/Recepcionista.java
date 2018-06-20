package com.mycompany.persistencia;
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author elias
 */
@Entity
@Table(name = "RECEPCIONISTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recepcionista.findAll", query = "SELECT r FROM Recepcionista r")
    , @NamedQuery(name = "Recepcionista.findByRecId", query = "SELECT r FROM Recepcionista r WHERE r.recId = :recId")
    , @NamedQuery(name = "Recepcionista.findByRecContra", query = "SELECT r FROM Recepcionista r WHERE r.recContra = :recContra")
    , @NamedQuery(name = "Recepcionista.findByRecEmail", query = "SELECT r FROM Recepcionista r WHERE r.recEmail = :recEmail")
    , @NamedQuery(name = "Recepcionista.findByRecNom", query = "SELECT r FROM Recepcionista r WHERE r.recNom = :recNom")
    , @NamedQuery(name = "Recepcionista.findByRecApat", query = "SELECT r FROM Recepcionista r WHERE r.recApat = :recApat")
    , @NamedQuery(name = "Recepcionista.findByRecAmat", query = "SELECT r FROM Recepcionista r WHERE r.recAmat = :recAmat")
    , @NamedQuery(name = "Recepcionista.findByRecFna", query = "SELECT r FROM Recepcionista r WHERE r.recFna = :recFna")
    , @NamedQuery(name = "Recepcionista.findByRecTelm", query = "SELECT r FROM Recepcionista r WHERE r.recTelm = :recTelm")
    , @NamedQuery(name = "Recepcionista.findByRecTelc", query = "SELECT r FROM Recepcionista r WHERE r.recTelc = :recTelc")
    , @NamedQuery(name = "Recepcionista.findByRecStatus", query = "SELECT r FROM Recepcionista r WHERE r.recStatus = :recStatus")})
public class Recepcionista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "REC_ID")
    private Integer recId;
    @Basic(optional = false)
    @Column(name = "REC_CONTRA")
    private String recContra;
    @Basic(optional = false)
    @Column(name = "REC_EMAIL")
    private String recEmail;
    @Basic(optional = false)
    @Column(name = "REC_NOM")
    private String recNom;
    @Basic(optional = false)
    @Column(name = "REC_APAT")
    private String recApat;
    @Basic(optional = false)
    @Column(name = "REC_AMAT")
    private String recAmat;
    @Basic(optional = false)
    @Column(name = "REC_FNA")
    @Temporal(TemporalType.DATE)
    private Date recFna;
    @Basic(optional = false)
    @Column(name = "REC_TELM")
    private String recTelm;
    @Basic(optional = false)
    @Column(name = "REC_TELC")
    private String recTelc;
    @Column(name = "REC_STATUS")
    private Integer recStatus;
    @OneToMany(mappedBy = "trRecid")
    private List<TransaccionesRecep> transaccionesRecepCollection;

    public Recepcionista() {
    }

    public Recepcionista(Integer recId) {
        this.recId = recId;
    }

    public Recepcionista(Integer recId, String recContra, String recEmail, String recNom, String recApat, String recAmat, Date recFna, String recTelm, String recTelc) {
        this.recId = recId;
        this.recContra = recContra;
        this.recEmail = recEmail;
        this.recNom = recNom;
        this.recApat = recApat;
        this.recAmat = recAmat;
        this.recFna = recFna;
        this.recTelm = recTelm;
        this.recTelc = recTelc;
    }

    public Integer getRecId() {
        return recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public String getRecContra() {
        return recContra;
    }

    public void setRecContra(String recContra) {
        this.recContra = recContra;
    }

    public String getRecEmail() {
        return recEmail;
    }

    public void setRecEmail(String recEmail) {
        this.recEmail = recEmail;
    }

    public String getRecNom() {
        return recNom;
    }

    public void setRecNom(String recNom) {
        this.recNom = recNom;
    }

    public String getRecApat() {
        return recApat;
    }

    public void setRecApat(String recApat) {
        this.recApat = recApat;
    }

    public String getRecAmat() {
        return recAmat;
    }

    public void setRecAmat(String recAmat) {
        this.recAmat = recAmat;
    }

    public Date getRecFna() {
        return recFna;
    }

    public void setRecFna(Date recFna) {
        this.recFna = recFna;
    }

    public String getRecTelm() {
        return recTelm;
    }

    public void setRecTelm(String recTelm) {
        this.recTelm = recTelm;
    }

    public String getRecTelc() {
        return recTelc;
    }

    public void setRecTelc(String recTelc) {
        this.recTelc = recTelc;
    }

    public Integer getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(Integer recStatus) {
        this.recStatus = recStatus;
    }
    
    
    @OneToOne(mappedBy = "recepcionista",  
              fetch = FetchType.LAZY, optional = false)
    private Direccion_REC direccionrec;

  
    public Direccion_REC getDireccionrec() {
        return direccionrec;
    }

    public void setDireccionrec(Direccion_REC direccionrec) {
        this.direccionrec = direccionrec;
    }




    @XmlTransient
    public List<TransaccionesRecep> getTransaccionesRecepCollection() {
        return transaccionesRecepCollection;
    }

    public void setTransaccionesRecepCollection(List<TransaccionesRecep> transaccionesRecepCollection) {
        this.transaccionesRecepCollection = transaccionesRecepCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recId != null ? recId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recepcionista)) {
            return false;
        }
        Recepcionista other = (Recepcionista) object;
        if ((this.recId == null && other.recId != null) || (this.recId != null && !this.recId.equals(other.recId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.persistencia.Recepcionista[ recId=" + recId + " ]";
    }
    
}
