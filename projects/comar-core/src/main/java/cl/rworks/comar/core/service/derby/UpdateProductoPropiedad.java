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
import cl.rworks.comar.core.util.BigDecimalUtils;
import cl.rworks.comar.core.util.ComarVerifierUtils;
import java.math.BigDecimal;

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
            ComarVerifierUtils.checkString(property, value);
            String code = (String) value;
            executeCode(product, code);
        } else if (property.equals("DESCRIPCION")) {
            ComarVerifierUtils.checkString(property, value);
            String description = (String) value;
            executeDescription(product, description);
        } else if (property.equals("PRECIOVENTAACTUAL")) {
            ComarVerifierUtils.checkBigDecimal(property, value);
            BigDecimal precioVenta = (BigDecimal) value;
            executePrecioVentaActual(product, precioVenta);
        } else if (property.equals("METRICA")) {
            ComarVerifierUtils.checkMetrica(property, value);
            Metrica metric = (Metrica) value;
            executeMetric(product, metric);
        } else if (property.equals("INCLUIRENBOLETA")) {
            ComarVerifierUtils.checkBoolean(property, value);
            Boolean incluirEnBoleta = (Boolean) value;
            executeIncluirEnBoleta(product, incluirEnBoleta);
        } else if (property.equals("PRECIOVENTAFIJO")) {
            ComarVerifierUtils.checkBoolean(property, value);
            Boolean precioVentaFijo = (Boolean) value;
            executePrecioVentaFijo(product, precioVentaFijo);
        } else if (property.equals("STOCKCOMPRADO")) {
            ComarVerifierUtils.checkBigDecimal(property, value);
            BigDecimal stockComprado = (BigDecimal) value;
            executeStockComprado(product, stockComprado);
        } else if (property.equals("STOCKVENDIDO")) {
            ComarVerifierUtils.checkBigDecimal(property, value);
            BigDecimal stockVendido = (BigDecimal) value;
            executeStockVendido(product, stockVendido);
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

    private void executePrecioVentaActual(ProductoEntity product, BigDecimal precioVenta) throws ComarServiceException {
        String sql = "UPDATE PRODUCTO SET PRODUCTO_PRECIOVENTAACTUAL = ? WHERE PRODUCTO_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, BigDecimalUtils.toLong(precioVenta));
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

    private void executeIncluirEnBoleta(ProductoEntity product, Boolean incluirEnBoleta) throws ComarServiceException {
        String sql = "UPDATE PRODUCTO SET PRODUCTO_INCLUIRENBOLETA = ? WHERE PRODUCTO_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, incluirEnBoleta);
            ps.setBytes(2, product.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }
    
    private void executePrecioVentaFijo(ProductoEntity product, Boolean precioVentaFijo) throws ComarServiceException {
        String sql = "UPDATE PRODUCTO SET PRODUCTO_PRECIOVENTAFIJO = ? WHERE PRODUCTO_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, precioVentaFijo);
            ps.setBytes(2, product.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    private void executeStockComprado(ProductoEntity product, BigDecimal value) throws ComarServiceException {
        String sql = "UPDATE PRODUCTO SET PRODUCTO_STOCKCOMPRADO = ? WHERE PRODUCTO_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, BigDecimalUtils.toLong(value));
            ps.setBytes(2, product.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    private void executeStockVendido(ProductoEntity product, BigDecimal value) throws ComarServiceException {
        String sql = "UPDATE PRODUCTO SET PRODUCTO_STOCKVENDIDO = ? WHERE PRODUCTO_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, BigDecimalUtils.toLong(value));
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
