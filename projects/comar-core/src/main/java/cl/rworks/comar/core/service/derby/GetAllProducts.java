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
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author aplik
 */
public class GetAllProducts implements RService {

    private Connection connection;

    public GetAllProducts(Connection connection) {
        this.connection = connection;
    }

    public JSONObjectResponse execute(JSONObject data) throws RServiceException {
        try {
            Response response = execute(new Request());
            return response.toJson();
        } catch (ComarServiceException ex) {
            throw new RServiceException("Error", ex);
        }
    }

    public Response execute(Request request) throws ComarServiceException {
        List<ComarProduct> list = execute();
        return new Response(list);
    }

    public List<ComarProduct> execute() throws ComarServiceException {
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
                JSONObject jproduct = ComarProductImpl.toJson(product);
                array.put(jproduct);
            }
            jresponse.put("products", array);
            return jresponse;
        }

    }

    public static List<ComarProduct> serve(Connection connection) throws ComarServiceException {
        return new GetAllProducts(connection).execute();
    }

}
