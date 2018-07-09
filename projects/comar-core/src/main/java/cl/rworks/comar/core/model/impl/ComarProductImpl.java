/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model.impl;

import cl.rworks.comar.core.ComarCoreUtils;
import cl.rworks.comar.core.model.ComarCategory;
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
    private String description = "";
    private BigDecimal buyPrice = BigDecimal.ZERO;
    private BigDecimal tax = ComarCoreUtils.IVA;
    private BigDecimal sellPrice = BigDecimal.ZERO;
    private BigDecimal stock = BigDecimal.ZERO;
    private ComarMetric metric = ComarMetric.UNIDADES;
    private byte[] categoryId = null;
    //
    private ComarCategory category = null;

    public ComarProductImpl() {
    }

    public ComarProductImpl(String code) {
        this.code = code;
    }

    public ComarProductImpl(String code, String description) {
        this.code = code;
        this.description = description;
    }

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

    @Override
    public byte[] getCategoryId() {
        return categoryId;
    }

    @Override
    public void setCategoryId(byte[] categoryId) {
        this.categoryId = categoryId;
    }

    public static ComarProduct create(String code) {
        return new ComarProductImpl(code);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id").append("=").append(UUIDUtils.toString(id)).append("; ");
        sb.append("code").append("=").append(code).append("; ");
        sb.append("description").append("=").append(description).append("; ");
        sb.append("buyprice").append("=").append(buyPrice).append("; ");
        sb.append("tax").append("=").append(tax).append("; ");
        sb.append("sellprice").append("=").append(sellPrice).append("; ");
        sb.append("stock").append("=").append(stock).append("; ");
        sb.append("metric").append("=").append(metric).append("; ");
        sb.append("idCategory").append("=").append(UUIDUtils.toString(categoryId));
        return sb.toString();
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
        byte[] categoryId = rs.getBytes("ID_CATEGORY");

        ComarProduct product = new ComarProductImpl();
        product.setId(id);
        product.setCode(code);
        product.setDescription(description);
        product.setBuyPrice(ComarCoreUtils.toModel(buyprice));
        product.setSellPrice(ComarCoreUtils.toModel(sellprice));
        product.setStock(ComarCoreUtils.toModel(stock));
        product.setMetric(ComarMetric.get(metricId));
        product.setTax(ComarCoreUtils.toModel(tax));
        product.setCategoryId(categoryId);

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
        String idCategory = JSONUtils.getString(jrequest, "idCategory", null);

        ComarProduct product = new ComarProductImpl();
        product.setId(UUIDUtils.toBytes(id));
        product.setCode(code);
        product.setDescription(description);
        product.setBuyPrice(ComarCoreUtils.create(buyprice));
        product.setTax(ComarCoreUtils.create(tax));
        product.setSellPrice(ComarCoreUtils.create(sellprice));
        product.setStock(ComarCoreUtils.create(stock));
        product.setMetric(ComarMetric.get(metricId));
        product.setCategoryId(UUIDUtils.toBytes(idCategory));
        return product;
    }

    public static JSONObject toJson(ComarProduct product) {
        JSONObject jproduct = new JSONObject();
        jproduct.put("id", UUIDUtils.toString((byte[]) product.getId()));
        jproduct.put("code", product.getCode());
        jproduct.put("description", product.getDescription());
        jproduct.put("buyprice", product.getBuyPrice());
        jproduct.put("sellprice", product.getSellPrice());
        jproduct.put("stock", product.getStock());
        jproduct.put("metricIndex", product.getMetric().ordinal());
        jproduct.put("categoryId", UUIDUtils.toString(product.getCategoryId()));
        return jproduct;
    }

    public static ComarProduct create(byte[] idProduct, String code, String description, int idMetric) {
        ComarProductImpl p = new ComarProductImpl();
        p.setId(idProduct);
        p.setCode(code);
        p.setDescription(description);
        p.setMetric(ComarMetric.get(idMetric));
        return p;
    }
}
