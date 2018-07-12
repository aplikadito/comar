/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.impl.CategoriaEntityImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cl.rworks.comar.core.model.CategoriaEntity;

/**
 *
 * @author aplik
 */
public class GetCategoriaPorNombre  {

    private Connection connection;

    public GetCategoriaPorNombre(Connection connection) {
        this.connection = connection;
    }

    public CategoriaEntity execute(String name) throws ComarServiceException {

        String sql = "SELECT * FROM CATEGORIA WHERE CATEGORIA_NOMBRE= ? FETCH FIRST 1 ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);

            CategoriaEntity category = null;
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    category = CategoriaEntityImpl.create(rs);
                }
            } catch (SQLException e) {
                throw e;
            }
            return category;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static CategoriaEntity serve(Connection connnection, String name) throws ComarServiceException {
        return new GetCategoriaPorNombre(connnection).execute(name);
    }
}
