/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import java.io.IOException;

/**
 *
 * @author aplik
 */
public class ComarServiceException extends IOException {

    public ComarServiceException(String message) {
        super(message);
    }

    public ComarServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
