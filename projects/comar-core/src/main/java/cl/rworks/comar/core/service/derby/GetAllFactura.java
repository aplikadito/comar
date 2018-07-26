/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.FacturaEntity;
import cl.rworks.comar.core.model.impl.FacturaEntityImpl;
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
public class GetAllFactura {

    private Connection connection;

    public GetAllFactura(Connection connection) {
        this.connection = connection;
    }

    private List<FacturaEntity> execute() throws ComarServiceException {
        String sql = "SELECT * FROM FACTURA";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            List<FacturaEntity> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FacturaEntity factura = FacturaEntityImpl.create(rs);
                list.add(factura);
            }
            return list;
        } catch (SQLException ex) {
            throw new ComarServiceException("", ex);
        }
    }

    public static List<FacturaEntity> serve(Connection connection) throws ComarServiceException {
        return new GetAllFactura(connection).execute();
    }

}
