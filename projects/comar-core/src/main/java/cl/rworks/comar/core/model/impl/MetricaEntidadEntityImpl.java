/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model.impl;

import cl.rworks.comar.core.model.MetricaEntity;

/**
 *
 * @author aplik
 */
public class MetricaEntidadEntityImpl implements MetricaEntity {

    private int id;
    private String nombre;
    private String simbolo;

    public MetricaEntidadEntityImpl(int id, String nombre, String simbolo) {
        this.id = id;
        this.nombre = nombre;
        this.simbolo = simbolo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String symbol) {
        this.simbolo = symbol;
    }

    public static MetricaEntidadEntityImpl create(int id, String name, String symbol) {
        return new MetricaEntidadEntityImpl(id, name, symbol);
    }

    public String toString() {
        return String.format("%s=%s, %s=%s, %s=%s", "id", id, "name", nombre, "symbol", simbolo);
    }
}
