/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.impl.ComarCategoryImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class InsertCategoryByName {

    private Connection connection;

    public InsertCategoryByName(Connection connection) {
        this.connection = connection;
    }

    private ComarCategory execute(String name) throws ComarServiceException {
        byte[] id = UUIDUtils.createId();
        String sql = "INSERT INTO COMAR_CATEGORY VALUES( ?,  ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBytes(1, id);
            ps.setString(2, name);
            ps.executeUpdate();

            return ComarCategoryImpl.create(id, name);
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static ComarCategory serve(Connection connection, String name) throws ComarServiceException {
        return new InsertCategoryByName(connection).execute(name);
    }

}
