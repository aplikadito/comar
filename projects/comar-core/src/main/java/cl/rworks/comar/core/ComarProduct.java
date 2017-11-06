/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

/**
 *
 * @author rgonzalez
 */
public interface ComarProduct {

    Integer getId();

    void setId(Integer id);

    String getCode();

    void setCode(String code);

    String getName();

    void setName(String name);

    ComarUnit getUnit();

    void setUnit(ComarUnit unit);

    ComarDecimalFormat getDecimalFormat();

    void setDecimalFormat(ComarDecimalFormat decimalFormat);

}
