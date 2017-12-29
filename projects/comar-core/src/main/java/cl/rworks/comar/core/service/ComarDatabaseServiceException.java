/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

/**
 *
 * @author aplik
 */
public class ComarDatabaseServiceException extends Exception {

    public ComarDatabaseServiceException(String message) {
        super(message);
    }

    public ComarDatabaseServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
