/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.derby.service;

import cl.rworks.comar.core.controller.ComarControllerException;
import cl.rworks.rservices.JSONObjectResponse;
import cl.rworks.rservices.RService;
import cl.rworks.rservices.RServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.json.JSONObject;

/**
 *
 * @author aplik
 */
public class DeleteAllProductsService implements RService {

    private Connection connection;

    public DeleteAllProductsService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public JSONObjectResponse execute(JSONObject data) throws RServiceException {
        try {
            execute();
            return new JSONObjectResponse();
        } catch (ComarControllerException ex) {
            throw new RServiceException("Error", ex);
        }
    }

    public void execute() throws ComarControllerException {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM COMAR_PRODUCT");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new ComarControllerException("Error", ex);
        }
    }

    public static void serve(Connection connection) throws ComarControllerException {
        new DeleteAllProductsService(connection).execute();
    }
}
