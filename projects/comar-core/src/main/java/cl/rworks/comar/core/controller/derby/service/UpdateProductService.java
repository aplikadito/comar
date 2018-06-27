/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.derby.service;

import cl.rworks.comar.core.ComarCoreUtils;
import cl.rworks.comar.core.controller.ComarControllerException;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.impl.ComarProductImpl;
import cl.rworks.rservices.JSONObjectResponse;
import cl.rworks.rservices.RService;
import cl.rworks.rservices.RServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.json.JSONObject;

/**
 *
 * @author aplik
 */
public class UpdateProductService implements RService {

    private Connection connection;

    public UpdateProductService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public JSONObjectResponse execute(JSONObject jresquest) throws RServiceException {
        try {
            Request request = new Request(jresquest);
            Response response = execute(request);
            return response.toJson();
        } catch (ComarControllerException ex) {
            throw new RServiceException("Error", ex);
        }
    }

    public Response execute(Request request) throws ComarControllerException {
        ComarProduct product = request.getProduct();
        return execute(product);
    }

    public Response execute(ComarProduct product) throws ComarControllerException {
        byte[] id = (byte[]) product.getId();

        try {
            String sql = "UPDATE COMAR_PRODUCT SET"
                    + " CODE = ?,"
                    + " DESCRIPTION = ?,"
                    + " BUYPRICE = ?,"
                    + " SELLPRICE = ?,"
                    + " STOCK = ?,"
                    + " ID_METRIC = ?"
                    + " TAX = ?"
                    + " WHERE ID = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, product.getCode());
            ps.setString(i++, product.getDescription());
            ps.setLong(i++, ComarCoreUtils.toDatabase(product.getBuyPrice()));
            ps.setLong(i++, ComarCoreUtils.toDatabase(product.getSellPrice()));
            ps.setLong(i++, ComarCoreUtils.toDatabase(product.getStock()));
            ps.setInt(i++, product.getMetric().getId());
            ps.setLong(i++, ComarCoreUtils.toDatabase(product.getTax()));
            ps.setBytes(i++, id);
            ps.executeUpdate();
            ps.close();

            return new Response();
        } catch (DerbySQLIntegrityConstraintViolationException ex) {
            throw new ComarControllerException("El codigo de producto ya existe: " + product.getCode());
        } catch (SQLException ex) {
            throw new ComarControllerException("Error", ex);
        }
    }

    public static class Request {

        private ComarProduct product;

        public Request(JSONObject jrequest) {
            this.product = ComarProductImpl.create(jrequest);
        }

        public Request(ComarProduct product) {
            this.product = product;
        }

        public ComarProduct getProduct() {
            return product;
        }

    }

    public static class Response {

        private JSONObjectResponse toJson() {
            return new JSONObjectResponse();
        }

    }

    public static void serve(Connection connection, ComarProduct product) throws ComarControllerException {
        new UpdateProductService(connection).execute(product);
    }
}
