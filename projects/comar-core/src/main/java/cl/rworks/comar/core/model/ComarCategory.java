/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model;

/**
 *
 * @author aplik
 */
public interface ComarCategory {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    boolean isIncludeInBill();

    void setIncludeInBill(boolean includeInBill);

}
