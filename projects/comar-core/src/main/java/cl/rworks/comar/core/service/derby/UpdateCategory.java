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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.json.JSONObject;

/**
 *
 * @author aplik
 */
public class UpdateCategory implements RService {

    private Connection connection;

    public UpdateCategory(Connection connection) {
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
        ComarCategory product = request.getCategory();
        execute(product);
        return new Response();
    }

    public void execute(ComarCategory category) throws ComarServiceException {
        byte[] id = (byte[]) category.getId();
        String sql = "UPDATE COMAR_CATEGORY SET NAME = ? WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setBytes(2, id);
            ps.executeUpdate();
        } catch (DerbySQLIntegrityConstraintViolationException ex) {
            throw new ComarServiceException("El nombre de la categoria ya existe: " + category.getName());
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static class Request {

        private ComarCategory category;

        public Request(JSONObject jrequest) {
            this.category = ComarCategoryImpl.create(jrequest);
        }

        public Request(ComarCategory category) {
            this.category = category;
        }

        public ComarCategory getCategory() {
            return category;
        }

    }

    public static class Response {

        private JSONObjectResponse toJson() {
            return new JSONObjectResponse();
        }

    }

    public static void serve(Connection connection, ComarCategory category) throws ComarServiceException {
        new UpdateCategory(connection).execute(category);
    }
}
