/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class DeleteCategory {

    private Connection connection;

    public DeleteCategory(Connection connection) {
        this.connection = connection;
    }

    public void execute(ComarCategory category) throws ComarServiceException {
        byte[] id = category.getId();
        String sql = "DELETE FROM COMAR_CATEGORY WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBytes(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ComarServiceException("Error", e);
        }
    }

    public static void serve(Connection connection, ComarCategory category) throws ComarServiceException {
        new DeleteCategory(connection).execute(category);
    }

}
