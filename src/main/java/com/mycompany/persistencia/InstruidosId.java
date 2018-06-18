package com.mycompany.persistencia;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
/**
 * @author elias
 */
public class InstruidosId implements Serializable{
    
    private Instructor instructor;
    private Suscriptor sus;
    
    @ManyToOne(cascade = CascadeType.ALL)
    public Instructor getInstructor() {
        return instructor;
    }
    
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Suscriptor getSus() {
        return sus;
    }

    public void setSus(Suscriptor sus) {
        this.sus = sus;
    }
    
}