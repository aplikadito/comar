/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class DeleteCategoriaPorNombre {

    private Connection connection;

    public DeleteCategoriaPorNombre(Connection connection) {
        this.connection = connection;
    }

    public void execute(String name) throws ComarServiceException {
        String sql = "DELETE FROM CATEGORIA WHERE CATEGORIA_NOMBRE = ?";

        if (name.equals(CategoriaEntity.DEFAULT_CATEGORY)) {
            throw new ComarServiceException("Esta categoria no puede ser eliminada: " + CategoriaEntity.DEFAULT_CATEGORY);
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ComarServiceException("Error", e);
        }
    }

    public static void serve(Connection connection, String name) throws ComarServiceException {
        new DeleteCategoriaPorNombre(connection).execute(name);
    }
}
