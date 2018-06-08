package com.mycompany.gymadmin;

import java.util.Date;

/**
 *
 * @author elias
 */
public class Datos {
    
    private static String ingreso;
   // private static String fecha;
    private Datos(){}
    
    public static String getIngreso(){
        return ingreso;
    }
    
    public static void setIngreso(String in){
        Date date = new Date();
       // fecha= String.valueOf(date.getDay())+"/"+String.valueOf(date.getMonth()+"/"+String.valueOf(date.getYear()));
        ingreso = in;
    }
    
  
}
