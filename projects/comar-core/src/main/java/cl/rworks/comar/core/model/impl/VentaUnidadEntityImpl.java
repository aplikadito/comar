/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model.impl;

import java.math.BigDecimal;
import cl.rworks.comar.core.model.ProductoEntity;
import cl.rworks.comar.core.model.VentaEntity;
import cl.rworks.comar.core.model.VentaUnidadEntity;
import cl.rworks.comar.core.util.BigDecimalUtils;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class VentaUnidadEntityImpl implements VentaUnidadEntity {

    private byte[] id;
    private byte[] idProducto;
    private BigDecimal precioVenta;
    private BigDecimal cantidad;
    private byte[] idVenta;

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

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal count) {
        this.cantidad = count;
    }

    public byte[] getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(byte[] idVenta) {
        this.idVenta = idVenta;
    }

    @Override
    public String toString() {
        return "VentaUnidadEntityImpl{" + "id=" + UUIDUtils.toString(id) + ", idProducto=" + UUIDUtils.toString(idProducto) + ", precioVenta=" + precioVenta + ", cantidad=" + cantidad + ", idVenta=" + UUIDUtils.toString(idVenta) + '}';
    }

    public static VentaUnidadEntity create(ResultSet rs) throws SQLException {
        int i = 1;

        byte[] id = rs.getBytes(i++);
        byte[] idProducto = rs.getBytes(i++);
        BigDecimal precioVenta = BigDecimalUtils.toBigDecimal(rs.getLong(i++));
        BigDecimal cantidad = BigDecimalUtils.toBigDecimal(rs.getLong(i++));
        byte[] idVenta = rs.getBytes(i++);

        VentaUnidadEntityImpl e = new VentaUnidadEntityImpl();
        e.setId(id);
        e.setIdProducto(idProducto);
        e.setPrecioVenta(precioVenta);
        e.setCantidad(cantidad);
        e.setIdVenta(idVenta);
        return e;
    }

    public static VentaUnidadEntity create(VentaEntity venta, ProductoEntity producto, BigDecimal precioVenta, BigDecimal cantidad) {
        VentaUnidadEntityImpl e = new VentaUnidadEntityImpl();
        e.setIdProducto(producto.getId());
        e.setPrecioVenta(precioVenta);
        e.setCantidad(cantidad);
        e.setIdVenta(venta.getId());
        return e;
    }
}
