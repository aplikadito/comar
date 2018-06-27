/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller;

/**
 *
 * @author aplik
 */
public class ComarControllerException extends Exception {

    public ComarControllerException(String message) {
        super(message);
    }

    public ComarControllerException(String message, Throwable cause) {
        super(message, cause);
    }

}
