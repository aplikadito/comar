/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.impl.ComarProductImpl;
import cl.rworks.rservices.JSONObjectResponse;
import cl.rworks.rservices.RService;
import cl.rworks.rservices.RServiceException;
import cl.rworks.rservices.UUIDUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author aplik
 */
public class SearchProductByCodeOrDescription implements RService {

    private Connection connection;

    public SearchProductByCodeOrDescription(Connection connection) {
        this.connection = connection;
    }

    public JSONObjectResponse execute(JSONObject data) throws RServiceException {
        try {
            Request request = new Request(data);
            Response response = execute(request);
            return response.toJson();
        } catch (ComarServiceException ex) {
            throw new RServiceException("Error", ex);
        }
    }

    public Response execute(Request request) throws ComarServiceException {
        String str = request.getStr();
        return new Response(execute(str));
    }

    public List<ComarProduct> execute(String str) throws ComarServiceException {
        if (str == null) {
            return Collections.EMPTY_LIST;
        }

        str = str.trim();
        if (str.isEmpty()) {
            return executeGetAll();
        } else {
            return executeSearch(str);
        }
    }

    private List<ComarProduct> executeGetAll() throws ComarServiceException {
        String sql = "SELECT * FROM COMAR_PRODUCT";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            List<ComarProduct> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(ComarProductImpl.create(rs));
            }
            return list;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    private List<ComarProduct> executeSearch(String str) throws ComarServiceException {
        String sql = "SELECT * FROM COMAR_PRODUCT WHERE CODE LIKE ? OR DESCRIPTION LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + str + "%");
            ps.setString(2, "%" + str + "%");

            List<ComarProduct> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(ComarProductImpl.create(rs));
            }
            return list;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static class Request {

        private String str;

        public Request(String str) {
            this.str = str;
        }

        private Request(JSONObject data) {
            this.str = data.getString("str");
        }

        public String getStr() {
            return str;
        }

    }

    public static class Response {

        private List<ComarProduct> products;

        public Response(List<ComarProduct> products) {
            this.products = products;
        }

        public List<ComarProduct> getProducts() {
            return products;
        }

        private JSONObjectResponse toJson() {
            JSONObjectResponse jresponse = new JSONObjectResponse();
            JSONArray array = new JSONArray();
            for (ComarProduct product : products) {
                JSONObject jproduct = new JSONObject();
                jproduct.put("id", UUIDUtils.toString((byte[]) product.getId()));
                jproduct.put("code", product.getCode());
                jproduct.put("description", product.getDescription());
                jproduct.put("buyprice", product.getBuyPrice());
                jproduct.put("sellprice", product.getSellPrice());
                jproduct.put("stock", product.getStock());
                jproduct.put("metricIndex", product.getMetric().ordinal());
                array.put(jproduct);
            }
            jresponse.put("products", array);
            return jresponse;
        }

    }

    public static List<ComarProduct> serve(Connection connection, String str) throws ComarServiceException {
        return new SearchProductByCodeOrDescription(connection).execute(str);
    }

}
