package com.mycompany.persistencia;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author elias
 */
@Entity
@Table(name="INSTRUIDOS")
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.instructor",
        joinColumns = @JoinColumn(name = "INS_ID")),
    @AssociationOverride(name = "primaryKey.sus",
        joinColumns = @JoinColumn(name = "SUS_ID")) })
public class Instruidos implements Serializable {
    
    private InstruidosId primaryKey = new InstruidosId();
    private Date fechainicio;
    
    @EmbeddedId
    public InstruidosId getPrimaryKey(){
        return primaryKey;
    }
    
    public void setPrimaryKey (InstruidosId primaryKey){
        this.primaryKey= primaryKey;
    }

    @Column(name="Fecha_Inicio")
    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    } 
    
    public void setInstructor(Instructor ins){
        getPrimaryKey().setInstructor(ins);
    }
    
    @Transient
    public Instructor getInstrucor(){
        return getPrimaryKey().getInstructor();
    }
    
   
    public void setSuscriptor(Suscriptor sus){
        getPrimaryKey().setSus(sus);
   }
    
    @Transient 
    public Suscriptor getSuscriptor(){
        return getPrimaryKey().getSus();
    }
}