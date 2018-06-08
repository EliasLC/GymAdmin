package com.mycompany.interacciondb;

/**
 *
 * @author elias
 */
public class Ingreso {
    
    private String admNom,admApat,admAmat,admContra;

    public Ingreso(){
    
    }
    public Ingreso(String admNom, String admApat, String admAmat, String admContra){
        this.admAmat= admAmat; this.admApat= admApat; this.admContra= admContra; this.admNom= admNom;
    }
    
    /**
     * @return the admNom
     */
    public String getAdmNom() {
        return admNom;
    }

    /**
     * @param admNom the admNom to set
     */
    public void setAdmNom(String admNom) {
        this.admNom = admNom;
    }

    /**
     * @return the admApat
     */
    public String getAdmApat() {
        return admApat;
    }

    /**
     * @param admApat the admApat to set
     */
    public void setAdmApat(String admApat) {
        this.admApat = admApat;
    }

    /**
     * @return the admAmat
     */
    public String getAdmAmat() {
        return admAmat;
    }

    /**
     * @param admAmat the admAmat to set
     */
    public void setAdmAmat(String admAmat) {
        this.admAmat = admAmat;
    }

    /**
     * @return the admContra
     */
    public String getAdmContra() {
        return admContra;
    }

    /**
     * @param admContra the admContra to set
     */
    public void setAdmContra(String admContra) {
        this.admContra = admContra;
    }
   
    //Metodo para obtener el nombre del usuario
    public String getnombre(){
        return admNom+" "+ admApat+ " "+ admAmat;
    }
}