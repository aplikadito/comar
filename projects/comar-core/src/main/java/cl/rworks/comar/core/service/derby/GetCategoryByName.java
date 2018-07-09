/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.impl.ComarCategoryImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class GetCategoryByName  {

    private Connection connection;

    public GetCategoryByName(Connection connection) {
        this.connection = connection;
    }

    public ComarCategory execute(String name) throws ComarServiceException {

        String sql = "SELECT * FROM COMAR_CATEGORY WHERE NAME = ? FETCH FIRST 1 ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);

            ComarCategory category = null;
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    category = ComarCategoryImpl.create(rs);
                }
            } catch (SQLException e) {
                throw e;
            }
            return category;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static ComarCategory serve(Connection connnection, String name) throws ComarServiceException {
        return new GetCategoryByName(connnection).execute(name);
    }
}
