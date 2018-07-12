/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import cl.rworks.comar.core.model.impl.ProductoEntityImpl;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.ProductoEntity;

/**
 *
 * @author aplik
 */
public class InsertProductoPorCodigo {

    private Connection connection;

    public InsertProductoPorCodigo(Connection connection) {
        this.connection = connection;
    }

    public ProductoEntity execute(String codigoProducto, String nombreCategoria) throws ComarServiceException {
        if (codigoProducto.isEmpty()) {
            throw new ComarServiceException("Codigo de producto no valido");
        }

        if (codigoProducto.isEmpty()) {
            throw new ComarServiceException("Nombre de categoria no valido");
        }

        CategoriaEntity categoria = GetCategoriaPorNombre.serve(connection, nombreCategoria);
        return execute(codigoProducto, categoria);
    }

    public ProductoEntity execute(String codigo, CategoriaEntity categoria) throws ComarServiceException {
        if (codigo.isEmpty()) {
            throw new ComarServiceException("Codigo de producto no valido");
        }

        if (categoria == null) {
            throw new ComarServiceException("Producto sin categoria asignada");
        }

        byte[] id = UUIDUtils.createId();
        String sql = "INSERT INTO PRODUCTO (PRODUCTO_ID, PRODUCTO_CODIGO, PRODUCTO_CATEGORIA_ID) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            ps.setBytes(i++, id);
            ps.setString(i++, codigo);
            ps.setBytes(i++, categoria.getId());
            ps.executeUpdate();

            return ProductoEntityImpl.create(id, codigo, categoria.getId());
        } catch (DerbySQLIntegrityConstraintViolationException ex) {
            throw new ComarServiceException("Error de restriccion al insertar producto: " + codigo, ex);
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static ProductoEntity serve(Connection connection, String codigo, String nombreCategoria) throws ComarServiceException {
        return new InsertProductoPorCodigo(connection).execute(codigo, nombreCategoria);
    }

    public static ProductoEntity serve(Connection connection, String codigo, CategoriaEntity category) throws ComarServiceException {
        return new InsertProductoPorCodigo(connection).execute(codigo, category);
    }
}
