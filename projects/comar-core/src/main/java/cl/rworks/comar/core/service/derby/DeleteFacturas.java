/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.FacturaEntity;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author aplik
 */
public class DeleteFacturas {

    private Connection connection;

    public DeleteFacturas(Connection connection) {
        this.connection = connection;
    }

    public void execute(List<FacturaEntity> facturas) throws ComarServiceException {
        if (facturas == null || facturas.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (FacturaEntity p : facturas) {
            sb.append("?").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);

        String sql = String.format("DELETE FROM FACTURA WHERE FACTURA_ID IN (%s)", sb.toString());
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            for (FacturaEntity e : facturas) {
                ps.setBytes(i++, e.getId());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ComarServiceException("Error", e);
        }
    }

    public static void serve(Connection connection, List<FacturaEntity> facturas) throws ComarServiceException {
        new DeleteFacturas(connection).execute(facturas);

    }

}
