/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.VentaEntity;
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
public class InsertVenta {

    private Connection connection;

    public InsertVenta(Connection connection) {
        this.connection = connection;
    }

    public void execute(VentaEntity venta) throws ComarServiceException {
        byte[] id = UUIDUtils.createId();
        String sql = "INSERT INTO VENTA VALUES(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBytes(1, id);
            ps.setString(2, venta.getCodigo());
            ps.setDate(3, Date.valueOf(venta.getFecha()));
            ps.executeUpdate();
            venta.setId(id);
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new ComarServiceException("El codigo de la venta ya existe: " + venta.getCodigo(), ex);
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, VentaEntity venta) throws ComarServiceException {
        new InsertVenta(connection).execute(venta);
    }
}
