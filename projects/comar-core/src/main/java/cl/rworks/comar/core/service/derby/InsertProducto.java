/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.ProductoEntity;
import cl.rworks.comar.core.model.impl.ProductoEntityImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

/**
 *
 * @author aplik
 */
public class InsertProducto {

    private Connection connection;

    public InsertProducto(Connection connection) {
        this.connection = connection;
    }

    public void execute(ProductoEntity producto, CategoriaEntity categoria) throws ComarServiceException {
        byte[] id = UUIDUtils.createId();
        String sql = "INSERT INTO PRODUCTO (PRODUCTO_ID, PRODUCTO_CODIGO, PRODUCTO_CATEGORIA_ID) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            ps.setBytes(i++, id);
            ps.setString(i++, producto.getCodigo());
            ps.setBytes(i++, categoria.getId());
            ps.executeUpdate();

            producto.setId(id);
        } catch (DerbySQLIntegrityConstraintViolationException ex) {
            throw new ComarServiceException("Error de restriccion al insertar producto: " + producto.getCodigo(), ex);
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, ProductoEntity producto, CategoriaEntity categoria) throws ComarServiceException {
        new InsertProducto(connection).execute(producto, categoria);
    }
}
