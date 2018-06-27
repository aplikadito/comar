/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model.impl;

import cl.rworks.comar.core.ComarCoreUtils;
import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.rservices.JSONUtils;
import cl.rworks.rservices.UUIDUtils;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;

/**
 *
 * @author aplik
 */
public class ComarProductImpl implements ComarProduct {

    private byte[] id;
    private String code;
    private String description;
    private BigDecimal buyPrice = BigDecimal.ZERO;
    private BigDecimal tax = ComarCoreUtils.IVA;
    private BigDecimal sellPrice = BigDecimal.ZERO;
    private BigDecimal stock = BigDecimal.ZERO;
    private ComarMetric metric = ComarMetric.UNIDADES;

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public ComarMetric getMetric() {
        return metric;
    }

    public void setMetric(ComarMetric metric) {
        this.metric = metric;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public static ComarProduct create(ResultSet rs) throws SQLException {
        byte[] id = rs.getBytes("ID");
        String code = rs.getString("CODE");
        String description = rs.getString("DESCRIPTION");
        long buyprice = rs.getInt("BUYPRICE");
        long tax = rs.getInt("TAX");
        long sellprice = rs.getInt("SELLPRICE");
        long stock = rs.getInt("STOCK");
        int metricId = rs.getInt("ID_METRIC");

        ComarProduct product = new ComarProductImpl();
        product.setId(id);
        product.setCode(code);
        product.setDescription(description);
        product.setBuyPrice(ComarCoreUtils.toModel(buyprice));
        product.setSellPrice(ComarCoreUtils.toModel(sellprice));
        product.setStock(ComarCoreUtils.toModel(stock));
        product.setMetric(ComarMetric.get(metricId));
        product.setTax(ComarCoreUtils.toModel(tax));

        return product;
    }

    public static ComarProduct create(JSONObject jrequest) {
        String id = JSONUtils.getString(jrequest, "id", "");
        String code = JSONUtils.getString(jrequest, "code", "");
        String description = JSONUtils.getString(jrequest, "description", "");
        double buyprice = JSONUtils.getDouble(jrequest, "buyprice", 0);
        double tax = JSONUtils.getDouble(jrequest, "tax", 0.19);
        double sellprice = JSONUtils.getDouble(jrequest, "sellprice", 0);
        double stock = JSONUtils.getDouble(jrequest, "stock", 0);
        int metricId = JSONUtils.getInt(jrequest, "metric", 0);

        ComarProduct product = new ComarProductImpl();
        product.setId(UUIDUtils.toBytes(id));
        product.setCode(code);
        product.setDescription(description);
        product.setBuyPrice(ComarCoreUtils.create(buyprice));
        product.setTax(ComarCoreUtils.create(tax));
        product.setSellPrice(ComarCoreUtils.create(sellprice));
        product.setStock(ComarCoreUtils.create(stock));
        product.setMetric(ComarMetric.get(metricId));

        return product;
    }

}
