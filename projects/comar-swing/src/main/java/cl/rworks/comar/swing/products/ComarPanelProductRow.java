/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.products;

import cl.rworks.comar.core.model.ComarMetric;

/**
 *
 * @author aplik
 */
public class ComarPanelProductRow {

    private String code = "";
    private String name = "";
    private ComarMetric metric = ComarMetric.UNIDAD;
    private double buyPrice = 0;
    private double sellPrice = 0;
    private double stock = 0;

    public ComarPanelProductRow() {
        this("");
    }

    public ComarPanelProductRow(String code) {
        this.code = code;
        this.name = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComarMetric getMetric() {
        return metric;
    }

    public void setMetric(ComarMetric metric) {
        this.metric = metric;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

}
