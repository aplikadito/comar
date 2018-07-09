/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author aplik
 */
public class InsertCategory {

    private Connection connection;

    public InsertCategory(Connection connection) {
        this.connection = connection;
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

    public static void serve(Connection connection, ComarCategory category) throws ComarServiceException {
        new InsertCategory(connection).execute(category);
    }
}
