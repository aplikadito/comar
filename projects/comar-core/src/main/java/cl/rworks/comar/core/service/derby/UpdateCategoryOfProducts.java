/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aplik
 */
public class UpdateCategoryOfProducts {

    private Connection connection;

    public UpdateCategoryOfProducts(Connection connection) {
        this.connection = connection;
    }

    private void execute(List<ComarProduct> products, ComarCategory category) throws ComarServiceException {
        StringBuilder sb = new StringBuilder();
        for (ComarProduct p : products) {
            sb.append("?").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);

        String sql = String.format("UPDATE COMAR_PRODUCT SET ID_CATEGORY = ? WHERE ID IN (%s)", sb.toString());
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBytes(1, category.getId());

            int i = 2;
            for (ComarProduct p : products) {
                ps.setBytes(i++, p.getId());
            }
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    static void serve(Connection connection, List<ComarProduct> products, ComarCategory category) throws ComarServiceException {
        new UpdateCategoryOfProducts(connection).execute(products, category);
    }

}
