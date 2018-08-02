/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import cl.rworks.comar.core.model.CategoriaEntity;

/**
 *
 * @author aplik
 */
public class InsertCategoria {

    private Connection connection;

    public InsertCategoria(Connection connection) {
        this.connection = connection;
    }

    public void execute(CategoriaEntity category) throws ComarServiceException {
        if (category == null) {
            throw new ComarServiceException("Categoria nula");
        }

        String name = category.getNombre();
        if (name == null) {
            throw new ComarServiceException("Nombre de la Categoria nula");
        }

        name = name.trim();
        if (name.isEmpty()) {
            throw new ComarServiceException("Nombre de la Categoria vacia, evite espacios antes y despues del nombre");
        }

        if (name.equals(CategoriaEntity.DEFAULT_CATEGORY)) {
            throw new ComarServiceException("Nombre de la categoria reservado por el sistema: " + CategoriaEntity.DEFAULT_CATEGORY);
        }

        byte[] id = UUIDUtils.createId();
        String sql = "INSERT INTO CATEGORIA VALUES(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBytes(1, id);
            ps.setString(2, category.getNombre());
            ps.executeUpdate();
            category.setId(id);
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new ComarServiceException("El nombre de la categoria ya existe: " + category.getNombre());
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, CategoriaEntity category) throws ComarServiceException {
        new InsertCategoria(connection).execute(category);
    }
}
