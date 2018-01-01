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
public class ComarDaoException extends Exception {

    public ComarDaoException(String message) {
        super(message);
    }

    public ComarDaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
