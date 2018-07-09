/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarProduct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author aplik
 */
public class DeleteProducts {

    private Connection connection;

    public DeleteProducts(Connection connection) {
        this.connection = connection;
    }

    private void execute(List<ComarProduct> products) throws ComarServiceException {
        if (products == null || products.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (ComarProduct p : products) {
            sb.append("?").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);

        String sql = String.format("DELETE FROM COMAR_PRODUCT WHERE ID IN (%s)", sb.toString());
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            for (ComarProduct p : products) {
                ps.setBytes(i++, p.getId());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ComarServiceException("Error", e);
        }
    }

    public static void serve(Connection connection, List<ComarProduct> products) throws ComarServiceException {
        new DeleteProducts(connection).execute(products);
    }

}
