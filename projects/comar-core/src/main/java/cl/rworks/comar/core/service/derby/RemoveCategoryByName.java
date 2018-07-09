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
public class RemoveCategoryByName {

    private Connection connection;

    public RemoveCategoryByName(Connection connection) {
        this.connection = connection;
    }

    public void execute(String name) throws ComarServiceException {
        String sql = "DELETE FROM COMAR_CATEGORY WHERE NAME = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ComarServiceException("Error", e);
        }
    }

    public static void serve(Connection connection, String name) throws ComarServiceException {
        new RemoveCategoryByName(connection).execute(name);
    }

}
