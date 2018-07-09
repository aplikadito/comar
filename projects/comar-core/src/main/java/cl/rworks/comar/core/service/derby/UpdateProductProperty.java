/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author aplik
 */
public class UpdateProductProperty {

    private Connection connection;

    public UpdateProductProperty(Connection connection) {
        this.connection = connection;
    }

    private void execute(ComarProduct product, String property, Object value) throws ComarServiceException {
        if (property.equals("CODE")) {
            String code = (String) value;
            executeCode(product, code);
        } else if (property.equals("DESCRIPTION")) {
            String description = (String) value;
            executeDescription(product, description);
        } else if (property.equals("METRIC")) {
            ComarMetric metric = (ComarMetric) value;
            executeMetric(product, metric);
        } else {
            throw new ComarServiceException("Propiedad no soportada: " + property);
        }

    }

    private void executeCode(ComarProduct product, String code) throws ComarServiceException {
        String sql = "UPDATE COMAR_PRODUCT SET CODE = ? WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.setBytes(2, product.getId());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new ComarServiceException("El codigo ya existe: " + code, ex);
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    private void executeDescription(ComarProduct product, String description) throws ComarServiceException {
        String sql = "UPDATE COMAR_PRODUCT SET DESCRIPTION = ? WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, description);
            ps.setBytes(2, product.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    private void executeMetric(ComarProduct product, ComarMetric metric) throws ComarServiceException {
        String sql = "UPDATE COMAR_PRODUCT SET ID_METRIC = ? WHERE ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, metric.getId());
            ps.setBytes(2, product.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, ComarProduct product, String property, Object value) throws ComarServiceException {
        new UpdateProductProperty(connection).execute(product, property, value);
    }
}
