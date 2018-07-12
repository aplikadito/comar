/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model;

import java.math.BigDecimal;

/**
 *
 * @author aplik
 */
public interface FacturaUnidadEntity {

    public byte[] getId();

    public void setId(byte[] id);

    public byte[] getFacturaId();

    public void setFacturaId(byte[] facturaId);

    public String getCodigo();

    public void setCodigo(String code);

    public String getDescripcion();

    public void setDescripcion(String descripcion);

    public BigDecimal getPrecioCompra();

    public void setPrecioCompra(BigDecimal precioCompra);

    public BigDecimal getCantidad();

    public void setCantidad(BigDecimal cantidad);
}
