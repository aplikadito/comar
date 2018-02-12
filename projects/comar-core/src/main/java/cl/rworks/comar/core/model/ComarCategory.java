/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model;

import io.permazen.annotation.OnCreate;

/**
 *
 * @author aplik
 */
public interface ComarCategory {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);
    
    double getTax();

    void setTax(double tax);
    
}
