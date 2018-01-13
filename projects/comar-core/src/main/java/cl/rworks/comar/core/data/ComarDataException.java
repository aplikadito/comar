/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.data;

/**
 *
 * @author rgonzalez
 */
public class ComarDataException extends Exception{ 

    public ComarDataException(String message) {
        super(message);
    }

    public ComarDataException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
