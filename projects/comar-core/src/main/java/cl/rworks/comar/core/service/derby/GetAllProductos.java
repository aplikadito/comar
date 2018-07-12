/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.impl.ProductoEntityImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import cl.rworks.comar.core.model.ProductoEntity;

/**
 *
 * @author aplik
 */
public class GetAllProductos {

    private Connection connection;

    public GetAllProductos(Connection connection) {
        this.connection = connection;
    }

    public List<ProductoEntity> execute() throws ComarServiceException {
        String sql = "SELECT * FROM PRODUCTO";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            List<ProductoEntity> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(ProductoEntityImpl.create(rs));
            }
            return list;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static List<ProductoEntity> serve(Connection connection) throws ComarServiceException {
        return new GetAllProductos(connection).execute();
    }

}
