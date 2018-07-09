/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model;

import java.math.BigDecimal;

/**
 *
 * @author rgonzalez
 */
public interface ComarProduct {

    byte[] getId();

    void setId(byte[] id);

    String getCode();

    void setCode(String code);

    String getDescription();

    void setDescription(String description);

    BigDecimal getBuyPrice();

    void setBuyPrice(BigDecimal buyPrice);

    BigDecimal getSellPrice();

    void setSellPrice(BigDecimal sellPrice);

    BigDecimal getStock();

    void setStock(BigDecimal stock);

    BigDecimal getTax();

    void setTax(BigDecimal tax);

    ComarMetric getMetric();

    void setMetric(ComarMetric metric);

    byte[] getCategoryId();

    void setCategoryId(byte[] categoryId);

}
