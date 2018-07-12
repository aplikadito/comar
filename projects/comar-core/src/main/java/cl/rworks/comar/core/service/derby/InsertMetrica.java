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

/**
 *
 * @author aplik
 */
public class InsertMetrica {

    private Connection connection;

    public InsertMetrica(Connection connection) {
        this.connection = connection;
    }

    private void execute(Metrica metric) throws ComarServiceException {
        String sql = "INSERT INTO METRICA VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, metric.getId());
            ps.setString(2, metric.getName());
            ps.setString(3, metric.getSymbol());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection conn, Metrica metric) throws ComarServiceException {
        new InsertMetrica(conn).execute(metric);
    }

}
