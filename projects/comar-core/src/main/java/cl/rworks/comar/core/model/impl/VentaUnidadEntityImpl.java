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
    private String codigo;
    private String descripcion;
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

    public String getCodigoProducto() {
        return codigo;
    }

    public void setCodigoProducto(String code) {
        this.codigo = code;
    }

    public String getDescripcionProducto() {
        return descripcion;
    }

    public void setDescripcionProducto(String description) {
        this.descripcion = description;
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
        return "VentaUnidadEntityImpl{" + "id=" + UUIDUtils.toString(id) + ", idProducto=" + UUIDUtils.toString(idProducto) + ", codigo=" + codigo + ", descripcion=" + descripcion + ", precioVenta=" + precioVenta + ", cantidad=" + cantidad + ", idVenta=" + UUIDUtils.toString(idVenta) + '}';
    }

    public static VentaUnidadEntity create(ResultSet rs) throws SQLException {
        byte[] id = rs.getBytes(1);
        byte[] idProducto = rs.getBytes(2);
        String codigo = rs.getString(3);
        String descripcion = rs.getString(4);
        BigDecimal precioVenta = BigDecimalUtils.toBigDecimal(rs.getLong(5));
        BigDecimal cantidad = BigDecimalUtils.toBigDecimal(rs.getLong(6));
        byte[] idVenta = rs.getBytes(7);

        VentaUnidadEntityImpl e = new VentaUnidadEntityImpl();
        e.setId(id);
        e.setIdProducto(idProducto);
        e.setCodigoProducto(codigo);
        e.setDescripcionProducto(descripcion);
        e.setPrecioVenta(precioVenta);
        e.setCantidad(cantidad);
        e.setIdVenta(idVenta);
        return e;
    }

    public static VentaUnidadEntity create(VentaEntity venta, ProductoEntity producto, BigDecimal precioVenta, BigDecimal cantidad) throws SQLException {
        VentaUnidadEntityImpl e = new VentaUnidadEntityImpl();
        e.setIdProducto(producto.getId());
        e.setCodigoProducto(producto.getCodigo());
        e.setDescripcionProducto(producto.getDescripcion());
        e.setPrecioVenta(precioVenta);
        e.setCantidad(cantidad);
        e.setIdVenta(venta.getId());
        return e;
    }
}
