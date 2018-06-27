/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.derby.service;

import cl.rworks.comar.core.controller.ComarControllerException;
import cl.rworks.comar.core.model.ComarMetric;
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
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author aplik
 */
public class GetAllProductsService implements RService {

    private Connection connection;

    public GetAllProductsService(Connection connection) {
        this.connection = connection;
    }

    public JSONObjectResponse execute(JSONObject data) throws RServiceException {
        try {
            Response response = execute(new Request());
            return response.toJson();
        } catch (ComarControllerException ex) {
            throw new RServiceException("Error", ex);
        }
    }

    public Response execute(Request request) throws ComarControllerException {
        List<ComarProduct> list = execute();
        return new Response(list);
    }

    public List<ComarProduct> execute() throws ComarControllerException {
        try {
            List<ComarProduct> list = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM COMAR_PRODUCT");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(ComarProductImpl.create(rs));
            }
            ps.close();

            return list;
        } catch (SQLException ex) {
            throw new ComarControllerException("Error", ex);
        }
    }

    public static class Request {

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

    public static List<ComarProduct> serve(Connection connection) throws ComarControllerException {
        return new GetAllProductsService(connection).execute();
    }

}
