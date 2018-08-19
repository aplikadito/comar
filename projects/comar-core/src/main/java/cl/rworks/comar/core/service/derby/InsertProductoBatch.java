/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.ProductoEntity;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aplik
 */
public class InsertProductoBatch {

    private static final int DEFAULT_BATCH = 1000;
    private Connection connection;

    public InsertProductoBatch(Connection connection) {
        this.connection = connection;
    }

    public void execute(List<ProductoEntity> productos, CategoriaEntity categoria, int batch) throws ComarServiceException {
        try {
            int i = 0;
            List<ProductoEntity> chunks = new ArrayList<>();
            while (i < productos.size()) {
                chunks.add(productos.get(i++));
                if (chunks.size() >= batch) {
                    insertChunk(chunks, categoria);
                    chunks = new ArrayList<>();
                }
            }

            if (chunks.size() > 0) {
                insertChunk(chunks, categoria);
            }
        } catch (ComarServiceException e) {
            productos.stream().forEach(p -> p.setId(null));
            throw e;
        }
    }

    public void insertChunk(List<ProductoEntity> chunks, CategoriaEntity categoria) throws ComarServiceException {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO PRODUCTO (PRODUCTO_ID, PRODUCTO_CODIGO, PRODUCTO_DESCRIPCION, PRODUCTO_CATEGORIA_ID) VALUES ");
        for (int i = 0; i < chunks.size(); i++) {
            sb.append("(?, ?, ?, ?),");
        }
        sb.deleteCharAt(sb.length() - 1);

        String sql = sb.toString();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            for (ProductoEntity producto : chunks) {
                byte[] id = UUIDUtils.createId();

                ps.setBytes(i++, id);
                ps.setString(i++, producto.getCodigo());
                ps.setString(i++, producto.getDescripcion());
                ps.setBytes(i++, categoria.getId());

                producto.setId(id);
            }

            int executeUpdate = ps.executeUpdate();
//            System.out.println("InsertProductosPorCsv.insertChunk: " + executeUpdate);
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, List<ProductoEntity> productos, CategoriaEntity categoria) throws ComarServiceException {
        new InsertProductoBatch(connection).execute(productos, categoria, DEFAULT_BATCH);
    }

    public static void serve(Connection connection, List<ProductoEntity> productos, CategoriaEntity categoria, int batch) throws ComarServiceException {
        new InsertProductoBatch(connection).execute(productos, categoria, batch);
    }
}
