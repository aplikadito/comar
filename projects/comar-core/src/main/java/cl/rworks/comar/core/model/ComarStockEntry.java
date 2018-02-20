/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model;

import java.util.Date;

/**
 *
 * @author rgonzalez
 */
public interface ComarStockEntry {

    Long getId();

    void setId(Long id);

    String getStockCode();

    void setStockCode(String stockCode);
    
    String getProvider();

    void setProvider(String provider);
    
    String getOrder();

    void setOrder(String order);

    Date getDate();

    void setDate(Date date);
}
