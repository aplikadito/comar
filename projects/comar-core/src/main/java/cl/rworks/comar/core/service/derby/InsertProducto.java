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
import cl.rworks.comar.core.util.BigDecimalUtils;
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
        String sql = "INSERT INTO PRODUCTO VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            ps.setBytes(i++, id);
            ps.setString(i++, producto.getCodigo());
            ps.setString(i++, producto.getDescripcion());
            ps.setLong(i++, BigDecimalUtils.toLong(producto.getPrecioVentaActual()));
            ps.setBoolean(i++, producto.isIncluirEnBoleta());
            ps.setBoolean(i++, producto.isPrecioVentaFijo());
            ps.setLong(i++, BigDecimalUtils.toLong(producto.getStockComprado()));
            ps.setLong(i++, BigDecimalUtils.toLong(producto.getStockVendido()));
            ps.setInt(i++, producto.getMetrica().getId());
            ps.setBytes(i++, categoria.getId());
            ps.executeUpdate();

            producto.setId(id);
            producto.setCategoriaId(categoria.getId());
        } catch (DerbySQLIntegrityConstraintViolationException ex) {
            throw new ComarServiceException("Error de restriccion al insertar producto: " + producto.getCodigo(), ex);
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, ProductoEntity producto, CategoriaEntity categoria) throws ComarServiceException {
        new InsertProducto(connection).execute(producto, categoria);
    }

    public static ProductoEntity serve(Connection connection, String codigo, CategoriaEntity categoria) throws ComarServiceException {
        ProductoEntity product = ProductoEntityImpl.create(codigo);
        new InsertProducto(connection).execute(product, categoria);
        return product;
    }
}
