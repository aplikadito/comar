/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui;

/**
 *
 * @author aplik
 */
public class ComarValidationException extends Exception {

    public ComarValidationException(String message) {
        super(message);
    }

    public ComarValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
