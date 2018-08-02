/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.FacturaUnidadEntity;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.BigDecimalUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class UpdateFacturaUnidadPropiedad {

    private Connection connection;

    public UpdateFacturaUnidadPropiedad(Connection connection) {
        this.connection = connection;
    }

    public void execute(FacturaUnidadEntity factura, String propiedad, Object valor) throws ComarServiceException {
        if (propiedad.equals("PRECIOCOMPRA")) {
            if (valor instanceof BigDecimal) {
                updateBigDecimalProperty(factura, "FACTURAUNIDAD_PRECIOCOMPRA", (BigDecimal) valor);
            } else {
                throw new ComarServiceException(String.format("La propiedad %s debe ser numerica", propiedad));
            }
        } else if (propiedad.equals("CANTIDAD")) {
            if (valor instanceof BigDecimal) {
                updateBigDecimalProperty(factura, "FACTURAUNIDAD_CANTIDAD", (BigDecimal) valor);
            } else {
                throw new ComarServiceException(String.format("La propiedad %s debe ser numerica", propiedad));
            }

        } else {
            throw new ComarServiceException("Propiedad no soportada: " + propiedad);
        }
    }

    public void updateBigDecimalProperty(FacturaUnidadEntity factura, String dbProp, BigDecimal valor) throws ComarServiceException {
        String sql = "UPDATE FACTURAUNIDAD SET " + dbProp + " = ? WHERE FACTURAUNIDAD_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            ps.setLong(i++, BigDecimalUtils.toLong(valor));
            ps.setBytes(i++, factura.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, FacturaUnidadEntity factura, String propiedad, Object valor) throws ComarServiceException {
        new UpdateFacturaUnidadPropiedad(connection).execute(factura, propiedad, valor);
    }
}
