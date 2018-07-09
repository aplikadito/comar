/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model.impl;

import cl.rworks.comar.core.model.ComarMetricObject;

/**
 *
 * @author aplik
 */
public class ComarMetricObjectImpl implements ComarMetricObject {

    private int id;
    private String name;
    private String symbol;

    public ComarMetricObjectImpl(int id, String name, String symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public static ComarMetricObjectImpl create(int id, String name, String symbol) {
        return new ComarMetricObjectImpl(id, name, symbol);
    }

    public String toString() {
        return String.format("%s=%s, %s=%s, %s=%s", "id", id, "name", name, "symbol", symbol);
    }
}
