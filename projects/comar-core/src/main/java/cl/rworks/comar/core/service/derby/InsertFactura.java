/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.FacturaEntity;
import cl.rworks.comar.core.model.impl.FacturaEntityImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;

/**
 *
 * @author aplik
 */
public class InsertFactura {

    private Connection connection;

    public InsertFactura(Connection connection) {
        this.connection = connection;
    }

    public void execute(FacturaEntity factura) throws ComarServiceException {
        byte[] id = UUIDUtils.createId();
        String sql = "INSERT INTO FACTURA VALUES(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBytes(1, id);
            ps.setString(2, factura.getCodigo());
            ps.setDate(3, Date.valueOf(factura.getFecha()));
            ps.executeUpdate();
            factura.setId(id);
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new ComarServiceException("El codigo de la factura ya existe: " + factura.getCodigo(), ex);
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, FacturaEntity factura) throws ComarServiceException {
        new InsertFactura(connection).execute(factura);
    }

    public static FacturaEntity serve(Connection connection, String code) throws ComarServiceException {
        FacturaEntity factura = FacturaEntityImpl.create(code, LocalDate.now());
        new InsertFactura(connection).execute(factura);
        return factura;
    }
}
