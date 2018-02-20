/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.model.ComarMetric;

/**
 *
 * @author aplik
 */
public class ComarPanelProductRow {

    private String code = "";
    private String name = "";
    private String categoryName = "";
    private ComarMetric metric = ComarMetric.UNIDAD;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ComarMetric getMetric() {
        return metric;
    }

    public void setMetric(ComarMetric metric) {
        this.metric = metric;
    }

}
