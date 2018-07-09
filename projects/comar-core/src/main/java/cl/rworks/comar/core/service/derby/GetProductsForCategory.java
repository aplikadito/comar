/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.impl.ComarCategoryImpl;
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
public class GetProductsForCategory {

    private Connection connection;

    public GetProductsForCategory(Connection connection) {
        this.connection = connection;
    }

    public List<ComarProduct> execute(ComarCategory category) throws ComarServiceException {
        if (category == null) {
            return executeNull();
        } else {
            return executeCategory(category);
        }
    }

    public List<ComarProduct> executeNull() throws ComarServiceException {
        List<ComarProduct> list = new ArrayList<>();
        String sql = "SELECT COMAR_PRODUCT.ID, COMAR_PRODUCT.CODE, COMAR_PRODUCT.DESCRIPTION, COMAR_PRODUCT.ID_METRIC, COMAR_CATEGORY.ID, COMAR_CATEGORY.NAME FROM COMAR_PRODUCT INNER JOIN COMAR_CATEGORY ON COMAR_PRODUCT.ID_CATEOGORY = COMAR_CATEGORY.ID";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                byte[] idProduct = rs.getBytes(1);
                String code = rs.getString(2);
                String description = rs.getString(3);
                int idMetric = rs.getInt(5);
                byte[] idCategory = rs.getBytes(6);
                String categoryName = rs.getString(7);

                ComarCategory cmodel = ComarCategoryImpl.create(idCategory, categoryName);
                ComarProduct pmodel = ComarProductImpl.create(idProduct, code, description, idMetric);
                pmodel.setCategoryId(idCategory);
                list.add(pmodel);
            }
            return list;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public List<ComarProduct> executeCategory(ComarCategory category) throws ComarServiceException {
        List<ComarProduct> list = new ArrayList<>();
        String sql = "SELECT * FROM COMAR_PRODUCT WHERE ID_CATEGORY = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBytes(1, category != null ? category.getId() : null);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(ComarProductImpl.create(rs));
            }
            return list;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static List<ComarProduct> serve(Connection connection, ComarCategory category) throws ComarServiceException {
        return new GetProductsForCategory(connection).execute(category);
    }

}
