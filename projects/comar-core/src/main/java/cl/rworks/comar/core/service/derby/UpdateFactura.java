/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.FacturaEntity;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author aplik
 */
public class UpdateFactura {

    private Connection connection;

    public UpdateFactura(Connection connection) {
        this.connection = connection;
    }

    public void execute(FacturaEntity factura) throws ComarServiceException {
        String sql = "UPDATE FACTURA SET FACTURA_CODIGO = ?, FACTURA_FECHA = ? WHERE FACTURA_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            ps.setString(i++, factura.getCodigo());
            ps.setDate(i++, Date.valueOf(factura.getFecha()));
            ps.setBytes(i++, factura.getId());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new ComarServiceException("El codigo de la factura ya existe: " + factura.getCodigo(), ex);
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, FacturaEntity factura) throws ComarServiceException {
        new UpdateFactura(connection).execute(factura);
    }
}
