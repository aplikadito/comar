/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.impl.ComarCategoryImpl;
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
public class GetCategoryByName implements RService {

    private Connection connection;

    public GetCategoryByName(Connection connection) {
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
        String code = request.getName();
        return new Response(execute(code));
    }

    public ComarCategory execute(String name) throws ComarServiceException {

        String sql = "SELECT * FROM COMAR_CATEGORY WHERE NAME = ? FETCH FIRST 1 ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);

            ComarCategory category = null;
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    category = ComarCategoryImpl.create(rs);
                }
            } catch (SQLException e) {
                throw e;
            }
            return category;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static class Request {

        private String name;

        public Request(String code) {
            this.name = code;
        }

        private Request(JSONObject data) {
            this.name = data.getString("name");
        }

        public String getName() {
            return name;
        }

    }

    public static class Response {

        private ComarCategory category;

        public Response(ComarCategory product) {
            this.category = category;
        }

        public ComarCategory getCategory() {
            return category;
        }

        private JSONObjectResponse toJson() {
            JSONObjectResponse jresponse = new JSONObjectResponse();
            JSONObject jproduct = new JSONObject();
            jproduct.put("id", UUIDUtils.toString((byte[]) category.getId()));
            jproduct.put("name", category.getName());
            return jresponse;
        }

    }

    public static ComarCategory serve(Connection connnection, String name) throws ComarServiceException {
        return new GetCategoryByName(connnection).execute(name);
    }
}
