/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.ProductoEntity;

/**
 *
 * @author aplik
 */
public class UpdateCategoriaDeProductos {

    private Connection connection;

    public UpdateCategoriaDeProductos(Connection connection) {
        this.connection = connection;
    }

    private void execute(List<ProductoEntity> products, CategoriaEntity category) throws ComarServiceException {
        StringBuilder sb = new StringBuilder();
        for (ProductoEntity p : products) {
            sb.append("?").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);

        String sql = String.format("UPDATE PRODUCTO SET PRODUCTO_CATEGORIA_ID = ? WHERE PRODUCTO_ID IN (%s)", sb.toString());
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBytes(1, category.getId());

            int i = 2;
            for (ProductoEntity p : products) {
                ps.setBytes(i++, p.getId());
            }
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    static void serve(Connection connection, List<ProductoEntity> products, CategoriaEntity category) throws ComarServiceException {
        new UpdateCategoriaDeProductos(connection).execute(products, category);
    }

}
