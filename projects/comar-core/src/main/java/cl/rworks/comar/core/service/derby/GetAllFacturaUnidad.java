/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.FacturaUnidadEntity;
import cl.rworks.comar.core.model.impl.FacturaUnidadEntityImpl;
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
public class GetAllFacturaUnidad {

    private Connection connection;

    public GetAllFacturaUnidad(Connection connection) {
        this.connection = connection;
    }

    private List<FacturaUnidadEntity> execute() throws ComarServiceException {
        String sql = "SELECT * FROM FACTURAUNIDAD";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            List<FacturaUnidadEntity> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FacturaUnidadEntity factura = FacturaUnidadEntityImpl.create(rs);
                list.add(factura);
            }
            return list;
        } catch (SQLException ex) {
            throw new ComarServiceException("", ex);
        }
    }

    public static List<FacturaUnidadEntity> serve(Connection connection) throws ComarServiceException {
        return new GetAllFacturaUnidad(connection).execute();
    }

}
