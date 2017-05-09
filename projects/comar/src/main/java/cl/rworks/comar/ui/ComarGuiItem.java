/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author rgonzalez
 */
public class ComarGuiItem {

    private StringProperty code;
    private StringProperty name;
    private StringProperty unit;
    private StringProperty decimalFormat;
//    private ComarCategory category;
    private DoubleProperty buyPrice;
    private DoubleProperty profitRate;
    private DoubleProperty sellPrice;

    public StringProperty getCode() {
        return code;
    }

    public void setCode(StringProperty code) {
        this.code = code;
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(StringProperty name) {
        this.name = name;
    }

    public StringProperty getUnit() {
        return unit;
    }

    public void setUnit(StringProperty unit) {
        this.unit = unit;
    }

    public StringProperty getDecimalFormat() {
        return decimalFormat;
    }

    public void setDecimalFormat(StringProperty decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    public DoubleProperty getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(DoubleProperty buyPrice) {
        this.buyPrice = buyPrice;
    }

    public DoubleProperty getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(DoubleProperty profitRate) {
        this.profitRate = profitRate;
    }

    public DoubleProperty getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(DoubleProperty sellPrice) {
        this.sellPrice = sellPrice;
    }
    
}
