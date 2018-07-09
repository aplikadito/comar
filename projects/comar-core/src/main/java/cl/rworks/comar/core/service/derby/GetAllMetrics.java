/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.ComarMetricObject;
import cl.rworks.comar.core.model.impl.ComarMetricObjectImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aplik
 */
public class GetAllMetrics {

    private Connection connection;

    public GetAllMetrics(Connection connection) {
        this.connection = connection;
    }

    public List<ComarMetricObject> execute() throws ComarServiceException {
        List<ComarMetricObject> metrics = new ArrayList<>();

        String sql = "SELECT * FROM COMAR_METRIC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String name = rs.getString("NAME");
                    String symbol = rs.getString("SYMBOL");
                    metrics.add(ComarMetricObjectImpl.create(id, name, symbol));
                }
            } catch (SQLException e) {
                throw e;
            }
        } catch (SQLException ex) {
            throw new ComarServiceException("Error, Codigo: " + ex.getErrorCode(), ex);
        }

        return metrics;
    }

    public static List<ComarMetricObject> serve(Connection connection) throws ComarServiceException {
        return new GetAllMetrics(connection).execute();
    }

}
