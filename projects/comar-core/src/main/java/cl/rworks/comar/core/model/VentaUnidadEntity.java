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
public interface VentaUnidadEntity {

    public byte[] getId();

    public void setId(byte[] id);

    public byte[] getIdProducto();

    public void setIdProducto(byte[] idProducto);

    public String getCodigoProducto();

    public void setCodigoProducto(String code);

    public String getDescripcionProducto();

    public void setDescripcionProducto(String descripcion);

    public BigDecimal getPrecioVenta();

    public void setPrecioVenta(BigDecimal precioVenta);

    public BigDecimal getCantidad();

    public void setCantidad(BigDecimal cantidad);

    public byte[] getIdVenta();

    public void setIdVenta(byte[] idVenta);
}
