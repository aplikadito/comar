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
    private String codigo;
    private String descripcion;
    private BigDecimal precioCompra;
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

    public byte[] getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(byte[] idFactura) {
        this.idFactura = idFactura;
    }

    @Override
    public String toString() {
        return "FacturaUnidadEntityImpl{" + "id=" + UUIDUtils.toString(id) + ", idProducto=" + UUIDUtils.toString(idProducto) + ", codigo=" + codigo + ", descripcion=" + descripcion + ", precioCompra=" + precioCompra + ", cantidad=" + cantidad + ", idFactura=" + UUIDUtils.toString(idFactura) + '}';
    }

    public static FacturaUnidadEntity create(ResultSet rs) throws SQLException {
        byte[] id = rs.getBytes(1);
        byte[] idProducto = rs.getBytes(2);
        String codigo = rs.getString(3);
        String descripcion = rs.getString(4);
        BigDecimal precioCompra = BigDecimalUtils.toBigDecimal(rs.getLong(5));
        BigDecimal cantidad = BigDecimalUtils.toBigDecimal(rs.getLong(6));
        byte[] idFactura = rs.getBytes(7);

        FacturaUnidadEntityImpl e = new FacturaUnidadEntityImpl();
        e.setId(id);
        e.setIdProducto(idProducto);
        e.setCodigoProducto(codigo);
        e.setDescripcionProducto(descripcion);
        e.setPrecioCompra(precioCompra);
        e.setCantidad(cantidad);
        e.setIdFactura(idFactura);
        return e;
    }

    public static FacturaUnidadEntity create(FacturaEntity factura, ProductoEntity producto, BigDecimal precioCompra, BigDecimal cantidad) throws SQLException {
        FacturaUnidadEntityImpl e = new FacturaUnidadEntityImpl();
        e.setIdProducto(producto.getId());
        e.setCodigoProducto(producto.getCodigo());
        e.setDescripcionProducto(producto.getDescripcion());
        e.setPrecioCompra(precioCompra);
        e.setCantidad(cantidad);
        e.setIdFactura(factura.getId());
        return e;
    }
}
