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

    public byte[] getIdProducto();

    public void setIdProducto(byte[] idProducto);

    public BigDecimal getPrecioNetoCompra();

    public void setPrecioNetoCompra(BigDecimal precioNetoCompra);

    public BigDecimal getCantidad();

    public void setCantidad(BigDecimal cantidad);

    public byte[] getIdFactura();

    public void setIdFactura(byte[] idFactura);
}
