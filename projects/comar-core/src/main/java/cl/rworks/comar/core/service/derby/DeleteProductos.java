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
import cl.rworks.comar.core.model.ProductoEntity;
import java.util.ArrayList;

/**
 *
 * @author aplik
 */
public class DeleteProductos {

    private static final int DEFAULT_BATCH = 1000;
    private Connection connection;

    public DeleteProductos(Connection connection) {
        this.connection = connection;
    }

    public void execute(List<ProductoEntity> productos, int batch) throws ComarServiceException {
        if (productos == null || productos.isEmpty()) {
            return;
        }

        try {
            int i = 0;
            List<ProductoEntity> chunk = new ArrayList<>();
            while (i < productos.size()) {
                chunk.add(productos.get(i++));
                if (chunk.size() >= batch) {
                    deleteChunk(chunk);
                    chunk = new ArrayList<>();
                }
            }

            if (chunk.size() > 0) {
                deleteChunk(chunk);
            }
        } catch (ComarServiceException e) {
            productos.stream().forEach(p -> p.setId(null));
            throw e;
        }
    }

    private void deleteChunk(List<ProductoEntity> chunk) throws ComarServiceException {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM PRODUCTO WHERE PRODUCTO_ID IN (");
        for (ProductoEntity p : chunk) {
            sb.append("?").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        String sql = sb.toString();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            for (ProductoEntity p : chunk) {
                ps.setBytes(i++, p.getId());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ComarServiceException("Error", e);
        }
    }

    public static void serve(Connection connection, List<ProductoEntity> products) throws ComarServiceException {
        new DeleteProductos(connection).execute(products, DEFAULT_BATCH);
    }

    public static void serve(Connection connection, List<ProductoEntity> products, int batch) throws ComarServiceException {
        new DeleteProductos(connection).execute(products, batch);
    }

}
