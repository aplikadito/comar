/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.VentaEntity;
import cl.rworks.comar.core.model.impl.VentaEntityImpl;
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
public class GetAllVenta {

    private Connection connection;

    public GetAllVenta(Connection connection) {
        this.connection = connection;
    }

    public List<VentaEntity> execute() throws ComarServiceException {
        String sql = "SELECT * FROM VENTA";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            List<VentaEntity> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(VentaEntityImpl.create(rs));
            }
            return list;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static List<VentaEntity> serve(Connection connection) throws ComarServiceException {
        return new GetAllVenta(connection).execute();
    }
}
