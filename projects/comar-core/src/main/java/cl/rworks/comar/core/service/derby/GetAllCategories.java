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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author aplik
 */
public class GetAllCategories implements RService {

    private Connection connection;

    public GetAllCategories(Connection connection) {
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
        return new Response(execute());
    }

    public List<ComarCategory> execute() throws ComarServiceException {
        List<ComarCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM COMAR_CATEGORY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(ComarCategoryImpl.create(rs));
            }
            return list;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static class Request {

        private Request(JSONObject jresquest) {
        }

    }

    public static class Response {

        private List<ComarCategory> categories;

        public Response(List<ComarCategory> categories) {
            this.categories = categories;
        }

        public void setCategories(List<ComarCategory> categories) {
            this.categories = categories;
        }

        private JSONObjectResponse toJson() {
            return new JSONObjectResponse();
        }

    }

    public static List<ComarCategory> serve(Connection connection) throws ComarServiceException {
        return new GetAllCategories(connection).execute();
    }
}
