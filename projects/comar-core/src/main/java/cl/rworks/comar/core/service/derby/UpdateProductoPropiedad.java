/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import cl.rworks.comar.core.model.ProductoEntity;

/**
 *
 * @author aplik
 */
public class UpdateProductoPropiedad {

    private Connection connection;

    public UpdateProductoPropiedad(Connection connection) {
        this.connection = connection;
    }

    private void execute(ProductoEntity product, String property, Object value) throws ComarServiceException {
        if (property.equals("CODIGO")) {
            String code = (String) value;
            executeCode(product, code);
        } else if (property.equals("DESCRIPCION")) {
            String description = (String) value;
            executeDescription(product, description);
        } else if (property.equals("METRICA")) {
            Metrica metric = (Metrica) value;
            executeMetric(product, metric);
        } else {
            throw new ComarServiceException("Propiedad no soportada: " + property);
        }

    }

    private void executeCode(ProductoEntity product, String codigo) throws ComarServiceException {
        String sql = "UPDATE PRODUCTO SET PRODUCTO_CODIGO = ? WHERE PRODUCTO_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.setBytes(2, product.getId());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new ComarServiceException("El codigo ya existe: " + codigo, ex);
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    private void executeDescription(ProductoEntity product, String descripcion) throws ComarServiceException {
        String sql = "UPDATE PRODUCTO SET PRODUCTO_DESCRIPCION = ? WHERE PRODUCTO_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, descripcion);
            ps.setBytes(2, product.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    private void executeMetric(ProductoEntity product, Metrica metrica) throws ComarServiceException {
        String sql = "UPDATE PRODUCTO SET PRODUCTO_METRICA_ID = ? WHERE PRODUCTO_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, metrica.getId());
            ps.setBytes(2, product.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, ProductoEntity product, String property, Object value) throws ComarServiceException {
        new UpdateProductoPropiedad(connection).execute(product, property, value);
    }
}
