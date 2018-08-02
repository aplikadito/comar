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
import java.util.List;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

/**
 *
 * @author aplik
 */
public class InsertProductosPorCsv {

    private Connection connection;

    public InsertProductosPorCsv(Connection connection) {
        this.connection = connection;
    }

    public void execute(List<ProductoEntity> productos, CategoriaEntity categoria) throws ComarServiceException {
//        byte[] id = UUIDUtils.createId();
//        String sql = "INSERT INTO PRODUCTO (PRODUCTO_ID, PRODUCTO_CODIGO, PRODUCTO_DESCRIPCION, PRODUCTO_CATEGORIA_ID) VALUES (?, ?, ?)";
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            int i = 1;
//            ps.setBytes(i++, id);
//            ps.setString(i++, producto.getCodigo());
//            ps.setBytes(i++, categoria.getId());
//            ps.executeUpdate();
//
//            producto.setId(id);
//        } catch (DerbySQLIntegrityConstraintViolationException ex) {
//            throw new ComarServiceException("Error de restriccion al insertar producto: " + producto.getCodigo(), ex);
//        } catch (SQLException ex) {
//            throw new ComarServiceException("Error", ex);
//        }
        throw new ComarServiceException("No soportado aun");
    }

    public static void serve(Connection connection, List<ProductoEntity> productos, CategoriaEntity categoria) throws ComarServiceException {
        new InsertProductosPorCsv(connection).execute(productos, categoria);
    }
}
