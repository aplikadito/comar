/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.VentaUnidadEntity;
import cl.rworks.comar.core.model.impl.VentaUnidadEntityImpl;
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
public class GetAllVentaUnidad {

    private Connection connection;

    public GetAllVentaUnidad(Connection connection) {
        this.connection = connection;
    }

    public List<VentaUnidadEntity> execute() throws ComarServiceException {
        String sql = "SELECT * FROM VENTAUNIDAD";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            List<VentaUnidadEntity> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(VentaUnidadEntityImpl.create(rs));
            }
            return list;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static List<VentaUnidadEntity> serve(Connection connection) throws ComarServiceException {
        return new GetAllVentaUnidad(connection).execute();
    }
}
