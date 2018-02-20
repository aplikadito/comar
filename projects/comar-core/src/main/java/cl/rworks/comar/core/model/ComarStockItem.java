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
public interface ComarStockItem {

    Long getId();

    void setId(Long id);

    ComarStockEntry getStockEntry();

    void setStockEntry(ComarStockEntry stockEntry);

    String getProductCode();

    void setProductCode(String productCode);

    double getQuantity();

    void setQuantity(double quantity);
    
    double getBuyPrice();

    void setBuyPrice(double buyPrice);
}
