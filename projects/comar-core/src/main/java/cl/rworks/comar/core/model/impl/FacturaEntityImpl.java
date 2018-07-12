/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model.impl;

import java.time.LocalDate;
import cl.rworks.comar.core.model.FacturaEntity;

/**
 *
 * @author aplik
 */
public class FacturaEntityImpl implements FacturaEntity {
    
    private byte[] id;
    private String codigo;
    private LocalDate fecha;

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String code) {
        this.codigo = code;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate batchDate) {
        this.fecha = batchDate;
    }

}
