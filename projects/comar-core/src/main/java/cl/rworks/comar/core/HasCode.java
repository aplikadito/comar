/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import io.permazen.annotation.JField;

/**
 *
 * @author rgonzalez
 */
public interface HasCode {

    @JField(indexed = true)
    String getCode();
}
