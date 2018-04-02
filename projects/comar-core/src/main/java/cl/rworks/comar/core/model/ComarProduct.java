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

    String getCode();

    void setCode(String code);

    String getName();

    void setName(String name);

    double getBuyPrice();

    void setBuyPrice(double buyPrice);

    double getSellPrice();

    void setSellPrice(double sellPrice);
    
    double getStock();

    void setStock(double stock);

    ComarMetric getMetric();

    void setMetric(ComarMetric metric);

}
