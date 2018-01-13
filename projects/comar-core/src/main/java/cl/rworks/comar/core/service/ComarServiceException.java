/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

/**
 *
 * @author rgonzalez
 */
public class ComarServiceException extends Exception{ 

    public ComarServiceException(String message) {
        super(message);
    }

    public ComarServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
