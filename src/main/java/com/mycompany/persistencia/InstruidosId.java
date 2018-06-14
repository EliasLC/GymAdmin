package com.mycompany.persistencia;

import java.io.Serializable;

/**
 *
 * @author elias
 */
public class InstruidosId implements Serializable{
    
    private int insid;
    private int susid;
    
    
    @Override
    public int hashCode() {
    return (int)(insid + susid);
  }

    @Override
  public boolean equals(Object object) {
    if (object instanceof InstruidosId) {
        InstruidosId otherId = (InstruidosId) object;
      return (otherId.insid == this.insid) && (otherId.susid == this.susid);
    }
    return false;
  }
}
