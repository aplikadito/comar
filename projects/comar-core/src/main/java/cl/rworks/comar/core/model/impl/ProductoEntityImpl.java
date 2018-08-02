/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model.impl;

import cl.rworks.comar.core.util.BigDecimalUtils;
import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import cl.rworks.comar.core.model.ProductoEntity;

/**
 *
 * @author aplik
 */
public class ProductoEntityImpl implements ProductoEntity {

    private byte[] id;
    private String code;
    private String description = "";
    private BigDecimal precioVentaActual = BigDecimal.ZERO;
    private BigDecimal stockActual = BigDecimal.ZERO;
    private Metrica metric = Metrica.UNIDADES;
    private byte[] categoryId = null;

    public ProductoEntityImpl() {
    }

    public ProductoEntityImpl(String code) {
        this.code = code;
    }

    public ProductoEntityImpl(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public String getCodigo() {
        return code;
    }

    public void setCodigo(String code) {
        this.code = code;
    }

    public String getDescripcion() {
        return description;
    }

    public void setDescripcion(String description) {
        this.description = description;
    }

    public BigDecimal getPrecioVentaActual() {
        return precioVentaActual;
    }

    public void setPrecioVentaActual(BigDecimal precioActual) {
        this.precioVentaActual = precioActual;
    }

    public BigDecimal getStockActual() {
        return stockActual;
    }

    public void setStockActual(BigDecimal stockActual) {
        this.stockActual = stockActual;
    }

    public Metrica getMetrica() {
        return metric;
    }

    public void setMetrica(Metrica metric) {
        this.metric = metric;
    }

    @Override
    public byte[] getCategoriaId() {
        return categoryId;
    }

    @Override
    public void setCategoriaId(byte[] categoryId) {
        this.categoryId = categoryId;
    }

    public static ProductoEntity create(String code) {
        return new ProductoEntityImpl(code);
    }

    public static ProductoEntity create(String code, String description) {
        return new ProductoEntityImpl(code, description);
    }

    @Override
    public String toString() {
        String idStr = UUIDUtils.toString(id);
        String categoryIdStr = UUIDUtils.toString(categoryId);
        return "ProductoImpl{" + "id=" + idStr + ", code=" + code + ", description=" + description + ", precioVentaActual=" + precioVentaActual + ", stockActual=" + stockActual + ", metric=" + metric + ", categoryId=" + categoryIdStr + '}';
    }

    public static ProductoEntity create(ResultSet rs) throws SQLException {
        int i = 1;
        ProductoEntity product = new ProductoEntityImpl();
        product.setId(rs.getBytes(i++));
        product.setCodigo(rs.getString(i++));
        product.setDescripcion(rs.getString(i++));
        product.setPrecioVentaActual(BigDecimalUtils.toBigDecimal(rs.getLong(i++)));
        product.setStockActual(BigDecimalUtils.toBigDecimal(rs.getLong(i++)));
        product.setMetrica(Metrica.get(rs.getInt(i++)));
        product.setCategoriaId(rs.getBytes(i++));

        return product;
    }

    public static ProductoEntity create(byte[] id, String codigo, byte[] categoriaId) {
        ProductoEntity p = new ProductoEntityImpl();
        p.setId(id);
        p.setCodigo(codigo);
        p.setCategoriaId(categoriaId);
        return p;
    }
}
