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
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

/**
 *
 * @author aplik
 */
public class UpdateCategory {

    private Connection connection;

    public UpdateCategory(Connection connection) {
        this.connection = connection;
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

    public static void serve(Connection connection, ComarCategory category) throws ComarServiceException {
        new UpdateCategory(connection).execute(category);
    }
}
