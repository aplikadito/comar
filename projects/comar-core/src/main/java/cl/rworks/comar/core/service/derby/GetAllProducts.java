/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.impl.ComarProductImpl;
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
public class GetAllProducts {

    private Connection connection;

    public GetAllProducts(Connection connection) {
        this.connection = connection;
    }

    public List<ComarProduct> execute() throws ComarServiceException {
        String sql = "SELECT * FROM COMAR_PRODUCT";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            List<ComarProduct> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(ComarProductImpl.create(rs));
            }
            return list;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static List<ComarProduct> serve(Connection connection) throws ComarServiceException {
        return new GetAllProducts(connection).execute();
    }

}
