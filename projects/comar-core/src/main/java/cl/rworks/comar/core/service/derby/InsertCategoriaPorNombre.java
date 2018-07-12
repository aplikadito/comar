/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.impl.CategoriaEntityImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import cl.rworks.comar.core.model.CategoriaEntity;

/**
 *
 * @author aplik
 */
public class InsertCategoriaPorNombre {

    private Connection connection;

    public InsertCategoriaPorNombre(Connection connection) {
        this.connection = connection;
    }

    private CategoriaEntity execute(String name) throws ComarServiceException {
        byte[] id = UUIDUtils.createId();
        String sql = "INSERT INTO CATEGORIA VALUES(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBytes(1, id);
            ps.setString(2, name);
            ps.executeUpdate();

            return CategoriaEntityImpl.create(id, name);
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static CategoriaEntity serve(Connection connection, String name) throws ComarServiceException {
        return new InsertCategoriaPorNombre(connection).execute(name);
    }

}
