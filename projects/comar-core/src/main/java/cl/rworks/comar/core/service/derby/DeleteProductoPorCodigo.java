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
public class DeleteProductoPorCodigo {

    private Connection connection;

    public DeleteProductoPorCodigo(Connection connection) {
        this.connection = connection;
    }

    public void execute(String code) throws ComarServiceException {
        String sql = "DELETE FROM PRODUCTO WHERE PRODUCTO_CODIGO = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, String code) throws ComarServiceException {
        new DeleteProductoPorCodigo(connection).execute(code);
    }
}
