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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aplik
 */
public class GetAllCategories {

    private Connection connection;

    public GetAllCategories(Connection connection) {
        this.connection = connection;
    }

    public List<ComarCategory> execute() throws ComarServiceException {
        List<ComarCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM COMAR_CATEGORY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(ComarCategoryImpl.create(rs));
            }
            return list;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static List<ComarCategory> serve(Connection connection) throws ComarServiceException {
        return new GetAllCategories(connection).execute();
    }
}
