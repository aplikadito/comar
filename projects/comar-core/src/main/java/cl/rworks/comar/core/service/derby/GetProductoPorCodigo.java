/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.impl.ProductoEntityImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cl.rworks.comar.core.model.ProductoEntity;

/**
 *
 * @author aplik
 */
public class GetProductoPorCodigo  {

    private Connection connection;

    public GetProductoPorCodigo(Connection connection) {
        this.connection = connection;
    }

    public ProductoEntity execute(String code) throws ComarServiceException {
        ProductoEntity product = null;
        String sql = "SELECT * FROM PRODUCTO WHERE PRODUCTO_CODIGO = ? FETCH FIRST 1 ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                product = ProductoEntityImpl.create(rs);
            }
            return product;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }


    public static ProductoEntity serve(Connection connnection, String code) throws ComarServiceException {
        return new GetProductoPorCodigo(connnection).execute(code);
    }
}
