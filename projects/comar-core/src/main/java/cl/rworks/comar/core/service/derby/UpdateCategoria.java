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
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import cl.rworks.comar.core.model.CategoriaEntity;

/**
 *
 * @author aplik
 */
public class UpdateCategoria {

    private Connection connection;

    public UpdateCategoria(Connection connection) {
        this.connection = connection;
    }

    public void execute(String oldName, String newName) throws ComarServiceException {
        if (oldName == null || oldName.isEmpty()) {
            throw new ComarServiceException("Nombre de categoria no valido");
        }

        if (newName == null || newName.isEmpty()) {
            throw new ComarServiceException("Nuevo nombre de categoria no valido");
        }

        CategoriaEntity cat = GetCategoriaPorNombre.serve(connection, oldName);
        cat.setNombre(newName);
        execute(cat);
    }

    public void execute(CategoriaEntity category) throws ComarServiceException {
        byte[] id = (byte[]) category.getId();
        String sql = "UPDATE CATEGORIA SET CATEGORIA_NOMBRE = ? WHERE CATEGORIA_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category.getNombre());
            ps.setBytes(2, id);
            ps.executeUpdate();
        } catch (DerbySQLIntegrityConstraintViolationException ex) {
            throw new ComarServiceException("El nombre de la categoria ya existe: " + category.getNombre());
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, CategoriaEntity category) throws ComarServiceException {
        new UpdateCategoria(connection).execute(category);
    }

    public static void serve(Connection connection, String oldName, String newName) throws ComarServiceException {
        new UpdateCategoria(connection).execute(oldName, newName);
    }
}
