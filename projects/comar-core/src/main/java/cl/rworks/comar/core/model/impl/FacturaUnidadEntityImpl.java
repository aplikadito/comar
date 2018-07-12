/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model.impl;

import java.math.BigDecimal;
import cl.rworks.comar.core.model.FacturaUnidadEntity;

/**
 *
 * @author aplik
 */
public class FacturaUnidadEntityImpl implements FacturaUnidadEntity{

    private byte[] id;
    private byte[] facturaId;
    private String codigo;
    private String descripcion;
    private BigDecimal precioCompra;
    private BigDecimal cantidad;

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public byte[] getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(byte[] idBatch) {
        this.facturaId = idBatch;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String code) {
        this.codigo = code;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String description) {
        this.descripcion = description;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal buyPrice) {
        this.precioCompra = buyPrice;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal count) {
        this.cantidad = count;
    }
    
}
