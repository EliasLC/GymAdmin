package com.mycompany.persistencia;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author elias
 */
public class DataBase {
  
  private static EntityManagerFactory emf;  
  
  private DataBase(){}

  public static EntityManagerFactory getEMF(){
      if(emf==null){
          emf= Persistence.createEntityManagerFactory("admin");
      }
      return emf;
  }
}