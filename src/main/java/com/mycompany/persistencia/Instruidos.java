package com.mycompany.persistencia;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author elias
 */
@Entity
@Table(name="Instruidos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instructor.findInstruidos", query = "SELECT i FROM Instruidos i WHERE i.insid = :insid")
})
@IdClass(InstruidosId.class)
public class Instruidos implements Serializable {

  
    @Id
    private int insid;
    @Id
    private int susid;
    @Column(name="Fecha_Inicio")
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    
    @ManyToOne
    @JoinColumn(name = "insid", updatable = false, insertable = false, referencedColumnName = "insId")
    private Instructor instructor;
    
    @ManyToOne
    @JoinColumn(name = "susid", updatable = false, insertable = false, referencedColumnName = "susId")
    private Suscriptor suscriptor;
    
      /**
     * @return the fechainicio
     */
    public Date getFechainicio() {
        return fechainicio;
    }
    
}