package com.mycompany.gymadmin;

import com.mycompany.interacciondb.Ingreso;
import java.util.Date;

/**
 *
 * @author elias
 */
public class Datos {
    private static Ingreso ingreso;
    
    private Datos(){}
    
    public static Ingreso getDatos(){
       return ingreso;
    }
    
    public static void setIngreso(Ingreso in){
        ingreso = in;
    }
}
