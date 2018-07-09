/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.impl.ComarCategoryImpl;
import cl.rworks.rservices.JSONObjectResponse;
import cl.rworks.rservices.RService;
import cl.rworks.rservices.RServiceException;
import cl.rworks.rservices.UUIDUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.json.JSONObject;

/**
 *
 * @author aplik
 */
public class InsertCategory implements RService {

    private Connection connection;

    public InsertCategory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public JSONObjectResponse execute(JSONObject jresquest) throws RServiceException {
        try {
            Request request = new Request(jresquest);
            Response response = execute(request);
            return response.toJson();
        } catch (ComarServiceException ex) {
            throw new RServiceException("Error", ex);
        }
    }

    public Response execute(Request request) throws ComarServiceException {
        ComarCategory category = request.getCategory();
        execute(category);
        return new Response(category);
    }

    public void execute(ComarCategory category) throws ComarServiceException {
        byte[] id = UUIDUtils.createId();
        String sql = "INSERT INTO COMAR_CATEGORY VALUES(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBytes(1, id);
            ps.setString(2, category.getName());
            ps.executeUpdate();
            category.setId(id);
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new ComarServiceException("El nombre de la categoria ya existe: " + category.getName());
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static class Request {

        private ComarCategory category;

        public Request(JSONObject jrequest) {
            this.category = new ComarCategoryImpl();
            this.category.setName(jrequest.getString("name"));
        }

        public ComarCategory getCategory() {
            return category;
        }

    }

    public static class Response {

        private ComarCategory category;

        private Response(ComarCategory category) {
            this.category = category;
        }

        private JSONObjectResponse toJson() {
            JSONObjectResponse json = new JSONObjectResponse();
            json.put("id", UUIDUtils.toString(category.getId()));
            json.put("name", category.getName());
            return json;
        }
    }

    public static void serve(Connection connection, ComarCategory category) throws ComarServiceException {
        new InsertCategory(connection).execute(category);
    }
}
