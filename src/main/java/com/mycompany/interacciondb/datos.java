package com.mycompany.interacciondb;
/**
 * @author elias
 */
public class datos {

  
    private static String email,Nombre;
    private static int id;
    
    public static void setEmail(String e){
        email= e;
    } 
    
    public static String getEmail(){
        return email;
    }
    
    public static void setNombre(String nom){
        Nombre= nom;
    }
    
    public static String getNombre(){
        return Nombre;
    }

    public static int getId() {
        return id;
    }
    
    public static void setId(int i) {
        id = i;
    }
}
