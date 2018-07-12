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
import java.util.Collections;
import java.util.List;
import cl.rworks.comar.core.model.ProductoEntity;

/**
 *
 * @author aplik
 */
public class SearchProductByCodeOrDescription {

    private Connection connection;

    public SearchProductByCodeOrDescription(Connection connection) {
        this.connection = connection;
    }

    public List<ProductoEntity> execute(String str) throws ComarServiceException {
        if (str == null) {
            return Collections.EMPTY_LIST;
        }

        str = str.trim();
        if (str.isEmpty()) {
            return executeGetAll();
        } else {
            return executeSearch(str);
        }
    }

    private List<ProductoEntity> executeGetAll() throws ComarServiceException {
        String sql = "SELECT * FROM COMAR_PRODUCT";
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

    private List<ProductoEntity> executeSearch(String str) throws ComarServiceException {
        String sql = "SELECT * FROM COMAR_PRODUCT WHERE PRODUCT_CODE LIKE ? OR RODUCT_DESCRIPTION LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + str + "%");
            ps.setString(2, "%" + str + "%");

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


    public static List<ProductoEntity> serve(Connection connection, String str) throws ComarServiceException {
        return new SearchProductByCodeOrDescription(connection).execute(str);
    }

}
