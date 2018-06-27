/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.util;

/**
 *
 * @author aplik
 */
public class RunSqlScriptException extends Exception {

    public RunSqlScriptException(String message) {
        super(message);
    }

    public RunSqlScriptException(String message, Throwable cause) {
        super(message, cause);
    }

}
