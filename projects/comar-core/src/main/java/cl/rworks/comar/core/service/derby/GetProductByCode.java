/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.impl.ComarProductImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class GetProductByCode  {

    private Connection connection;

    public GetProductByCode(Connection connection) {
        this.connection = connection;
    }

    public ComarProduct execute(String code) throws ComarServiceException {
        ComarProduct product = null;
        String sql = "SELECT * FROM COMAR_PRODUCT WHERE CODE = ? FETCH FIRST 1 ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                product = ComarProductImpl.create(rs);
            }
            return product;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }


    public static ComarProduct serve(Connection connnection, String code) throws ComarServiceException {
        return new GetProductByCode(connnection).execute(code);
    }
}
