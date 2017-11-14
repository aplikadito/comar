/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model;

/**
 *
 * @author rgonzalez
 */
public interface ComarProduct {

    Long getId();

    void setId(Long id);

    String getCode();

    void setCode(String code);

    String getName();

    void setName(String name);

    ComarUnit getUnit();

    void setUnit(ComarUnit unit);

    ComarDecimalFormat getDecimalFormat();

    void setDecimalFormat(ComarDecimalFormat decimalFormat);

    ComarCategory getCategory();

    void setCategory(ComarCategory category);
}
