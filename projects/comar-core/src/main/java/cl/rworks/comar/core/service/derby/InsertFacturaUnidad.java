/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.FacturaEntity;
import cl.rworks.comar.core.model.FacturaUnidadEntity;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.BigDecimalUtils;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author aplik
 */
public class InsertFacturaUnidad {

    private Connection connection;

    public InsertFacturaUnidad(Connection connection) {
        this.connection = connection;
    }

    public void execute(FacturaUnidadEntity unidad, FacturaEntity factura) throws ComarServiceException {
        byte[] id = UUIDUtils.createId();
        String sql = "INSERT INTO FACTURAUNIDAD VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            ps.setBytes(i++, id);
            ps.setBytes(i++, unidad.getIdProducto());
            ps.setString(i++, unidad.getCodigoProducto());
            ps.setString(i++, unidad.getDescripcionProducto());
            ps.setLong(i++, BigDecimalUtils.toLong(unidad.getPrecioCompra()));
            ps.setLong(i++, BigDecimalUtils.toLong(unidad.getCantidad()));
            ps.setBytes(i++, factura.getId());
            ps.executeUpdate();

            unidad.setId(id);
            unidad.setIdFactura(factura.getId());
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new ComarServiceException("El codigo de la factura ya existe: " + unidad.getCodigoProducto());
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, FacturaUnidadEntity unidad, FacturaEntity factura) throws ComarServiceException {
        new InsertFacturaUnidad(connection).execute(unidad, factura);
    }
}
