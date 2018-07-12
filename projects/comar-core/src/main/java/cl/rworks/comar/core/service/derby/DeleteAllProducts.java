/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class DeleteAllProducts {

    private Connection connection;

    public DeleteAllProducts(Connection connection) {
        this.connection = connection;
    }

//    @Override
//    public JSONObjectResponse execute(JSONObject data) throws RServiceException {
//        try {
//            execute();
//            return new JSONObjectResponse();
//        } catch (ComarServiceException ex) {
//            throw new RServiceException("Error", ex);
//        }
//    }

    public void execute() throws ComarServiceException {
        String sql = "DELETE FROM PRODUCTO";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection) throws ComarServiceException {
        new DeleteAllProducts(connection).execute();
    }
}
