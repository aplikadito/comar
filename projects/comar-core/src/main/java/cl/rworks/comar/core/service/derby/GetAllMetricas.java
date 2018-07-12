/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.impl.MetricaEntidadEntityImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import cl.rworks.comar.core.model.MetricaEntity;

/**
 *
 * @author aplik
 */
public class GetAllMetricas {

    private Connection connection;

    public GetAllMetricas(Connection connection) {
        this.connection = connection;
    }

    public List<MetricaEntity> execute() throws ComarServiceException {
        List<MetricaEntity> metrics = new ArrayList<>();

        String sql = "SELECT * FROM METRICA";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String symbol = rs.getString(3);
                    metrics.add(MetricaEntidadEntityImpl.create(id, name, symbol));
                }
            } catch (SQLException e) {
                throw e;
            }
        } catch (SQLException ex) {
            throw new ComarServiceException("Error, Codigo: " + ex.getErrorCode(), ex);
        }

        return metrics;
    }

    public static List<MetricaEntity> serve(Connection connection) throws ComarServiceException {
        return new GetAllMetricas(connection).execute();
    }

}
