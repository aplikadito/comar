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
import org.json.JSONObject;

/**
 *
 * @author aplik
 */
public class GetProductByCodeService implements RService {

    private Connection connection;

    public GetProductByCodeService(Connection connection) {
        this.connection = connection;
    }

    public JSONObjectResponse execute(JSONObject data) throws RServiceException {
        try {
            Request request = new Request(data);
            Response response = execute(request);
            return response.toJson();
        } catch (ComarControllerException ex) {
            throw new RServiceException("Error", ex);
        }
    }

    public Response execute(Request request) throws ComarControllerException {
        String code = request.getCode();
        return new Response(execute(code));
    }

    public ComarProduct execute(String code) throws ComarControllerException {
        try {
            ComarProduct product = null;

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM COMAR_PRODUCT WHERE CODE = ? FETCH FIRST 1 ROWS ONLY");
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                product = ComarProductImpl.create(rs);
            }

            ps.close();

            return product;
        } catch (SQLException ex) {
            throw new ComarControllerException("Error", ex);
        }
    }

    public static class Request {

        private String code;

        public Request(String code) {
            this.code = code;
        }

        private Request(JSONObject data) {
            this.code = data.getString("code");
        }

        public String getCode() {
            return code;
        }

    }

    public static class Response {

        private ComarProduct product;

        public Response(ComarProduct product) {
            this.product = product;
        }

        public ComarProduct getProduct() {
            return product;
        }

        private JSONObjectResponse toJson() {
            JSONObjectResponse jresponse = new JSONObjectResponse();
            JSONObject jproduct = new JSONObject();
            jproduct.put("id", UUIDUtils.toString((byte[]) product.getId()));
            jproduct.put("code", product.getCode());
            jproduct.put("description", product.getDescription());
            jproduct.put("buyprice", product.getBuyPrice());
            jproduct.put("sellprice", product.getSellPrice());
            jproduct.put("stock", product.getStock());
            jproduct.put("metricIndex", product.getMetric().ordinal());
            jresponse.put("product", jproduct);
            return jresponse;
        }

    }

    public static ComarProduct serve(Connection connnection, String code) throws ComarControllerException {
        return new GetProductByCodeService(connnection).execute(code);
    }
}
