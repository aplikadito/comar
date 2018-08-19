/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model.impl;

import cl.rworks.comar.core.model.FacturaEntity;
import java.math.BigDecimal;
import cl.rworks.comar.core.model.FacturaUnidadEntity;
import cl.rworks.comar.core.model.ProductoEntity;
import cl.rworks.comar.core.util.BigDecimalUtils;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class FacturaUnidadEntityImpl implements FacturaUnidadEntity {

    private byte[] id;
    private byte[] idProducto;
    private BigDecimal precioNetoCompra;
    private BigDecimal cantidad;
    private byte[] idFactura;

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public byte[] getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(byte[] idProducto) {
        this.idProducto = idProducto;
    }

    public BigDecimal getPrecioNetoCompra() {
        return precioNetoCompra;
    }

    public void setPrecioNetoCompra(BigDecimal precioNetoCompra) {
        this.precioNetoCompra = precioNetoCompra;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal count) {
        this.cantidad = count;
    }

    public byte[] getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(byte[] idFactura) {
        this.idFactura = idFactura;
    }

    @Override
    public String toString() {
        return "FacturaUnidadEntityImpl{" + "id=" + UUIDUtils.toString(id) + ", idProducto=" + UUIDUtils.toString(idProducto) + ", precioNetoCompra=" + precioNetoCompra + ", cantidad=" + cantidad + ", idFactura=" + UUIDUtils.toString(idFactura) + '}';
    }

    public static FacturaUnidadEntity create(ResultSet rs) throws SQLException {
        int i=1;
        
        byte[] id = rs.getBytes(i++);
        byte[] idProducto = rs.getBytes(i++);
        BigDecimal precioCompra = BigDecimalUtils.toBigDecimal(rs.getLong(i++));
        BigDecimal cantidad = BigDecimalUtils.toBigDecimal(rs.getLong(i++));
        byte[] idFactura = rs.getBytes(i++);

        FacturaUnidadEntityImpl e = new FacturaUnidadEntityImpl();
        e.setId(id);
        e.setIdProducto(idProducto);
        e.setPrecioNetoCompra(precioCompra);
        e.setCantidad(cantidad);
        e.setIdFactura(idFactura);
        return e;
    }
    
    public static FacturaUnidadEntity create(BigDecimal precioCompra, BigDecimal cantidad) throws SQLException {
        FacturaUnidadEntityImpl e = new FacturaUnidadEntityImpl();
        e.setPrecioNetoCompra(precioCompra);
        e.setCantidad(cantidad);
        return e;
    }

    public static FacturaUnidadEntity create(FacturaEntity factura, ProductoEntity producto, BigDecimal precioCompra, BigDecimal cantidad) throws SQLException {
        FacturaUnidadEntityImpl e = new FacturaUnidadEntityImpl();
        e.setIdProducto(producto.getId());
        e.setPrecioNetoCompra(precioCompra);
        e.setCantidad(cantidad);
        e.setIdFactura(factura.getId());
        return e;
    }
}
