/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.impl.CategoriaEntityImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import cl.rworks.comar.core.model.CategoriaEntity;

/**
 *
 * @author aplik
 */
public class GetAllCategoria {

    private Connection connection;

    public GetAllCategoria(Connection connection) {
        this.connection = connection;
    }

    public List<CategoriaEntity> execute() throws ComarServiceException {
        List<CategoriaEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM CATEGORIA";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(CategoriaEntityImpl.create(rs));
            }
            return list;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static List<CategoriaEntity> serve(Connection connection) throws ComarServiceException {
        return new GetAllCategoria(connection).execute();
    }
}
