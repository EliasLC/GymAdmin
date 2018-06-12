/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gymadmin;

import javafx.concurrent.Task;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author elias
 */
public class validarEmail extends Task<Boolean> {

    private String email;
    
    public validarEmail(String email){
        this.email= email;
    }
    
    @Override
    protected Boolean call() throws Exception {
        return valido();
    }
    
    private boolean valido(){
        boolean isValid= false;
        try{
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            isValid= true;
        }catch(AddressException e){
        
        }
        return isValid;        
    }
}
