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
import cl.rworks.rservices.UUIDUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.json.JSONObject;

/**
 *
 * @author aplik
 */
public class InsertProductService implements RService {

    private Connection connection;

    public InsertProductService(Connection connection) {
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
        return new Response(execute(product));
    }

    public byte[] execute(ComarProduct product) throws ComarControllerException {
        byte[] id = UUIDUtils.newId();

        try {
            int i = 1;
            PreparedStatement ps = connection.prepareStatement("INSERT INTO COMAR_PRODUCT VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setBytes(i++, id);
            ps.setString(i++, product.getCode());
            ps.setString(i++, product.getDescription());
            ps.setLong(i++, ComarCoreUtils.toDatabase(product.getBuyPrice()));
            ps.setLong(i++, ComarCoreUtils.toDatabase(product.getTax()));
            ps.setLong(i++, ComarCoreUtils.toDatabase(product.getSellPrice()));
            ps.setLong(i++, ComarCoreUtils.toDatabase(product.getStock()));
            ps.setInt(i++, product.getMetric().getId());
            
            ps.executeUpdate();
            ps.close();

            return id;
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

        private byte[] id;

        public Response(byte[] id) {
            this.id = id;
        }

        public byte[] getId() {
            return id;
        }

        public void setId(byte[] id) {
            this.id = id;
        }

        private JSONObjectResponse toJson() {
            JSONObjectResponse jresponse = new JSONObjectResponse();
            jresponse.put("id", UUIDUtils.toString(id));
            return jresponse;
        }

    }

    public static void serve(Connection connection, ComarProductImpl product) throws ComarControllerException {
        new InsertProductService(connection).execute(product);
    }
}
