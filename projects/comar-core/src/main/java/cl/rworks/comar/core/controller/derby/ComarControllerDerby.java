/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.derby;

import cl.rworks.comar.core.controller.derby.service.LoadProductsService;
import cl.rworks.comar.core.controller.ComarControllerException;
import cl.rworks.comar.core.model.ComarProduct;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import cl.rworks.comar.core.controller.ComarController;
import cl.rworks.comar.core.controller.derby.service.GetProductByCodeService;
import cl.rworks.comar.core.controller.derby.service.InsertProductService;
import cl.rworks.comar.core.model.ComarProductHistorial;
import java.time.LocalDateTime;

/**
 *
 * @author aplik
 */
public class ComarControllerDerby implements ComarController {

    private DataSource ds;
    //
    private Connection connection;

    public ComarControllerDerby() {
    }

    @Override
    public void startup(int dbOption) {
        switch (dbOption) {
            case MEMORY:
                throw new RuntimeException("opcion no soportada aun");
            case DISK: {
//                EmbeddedDataSource ds = new EmbeddedConnectionPoolDataSource();
//                ds.setDatabaseName(string);
                throw new RuntimeException("opcion no soportada aun");
            }
            case SERVER:
                throw new RuntimeException("opcion no soportada aun");
            default:
                throw new RuntimeException("opcion no soportada: " + dbOption);
        }
    }

    @Override
    public void createTransaction() throws ComarControllerException {
        try {
            connection = ds.getConnection();
        } catch (SQLException ex) {
            throw new ComarControllerException("Error", ex);
        }
    }

    @Override
    public void endTransaction() {
        try {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.err.println("Error al cerrar conexion");
                }
            }
        } finally {
            connection = null;
        }
    }

    @Override
    public void commit() throws ComarControllerException {
        try {
            connection.commit();
        } catch (SQLException ex) {
            throw new ComarControllerException("Error", ex);
        }
    }

    @Override
    public void rollback() {
//        try {
//            connection.rollback();
//        } catch (SQLException ex) {
//            throw new ComarControllerException("Error", ex);
//        }
    }

    @Override
    public List<ComarProduct> loadProducts(String str) throws ComarControllerException {
        LoadProductsService service = new LoadProductsService(connection);
        LoadProductsService.Response response = service.execute(new LoadProductsService.Request(str));
        return response.getProducts();
    }

    @Override
    public ComarProduct getProductByCode(String code) throws ComarControllerException {
        GetProductByCodeService service = new GetProductByCodeService(connection);
        GetProductByCodeService.Response response = service.execute(new GetProductByCodeService.Request(code));
        return response.getProduct();
    }

    @Override
    public void addProduct(ComarProduct product) throws ComarControllerException {
        InsertProductService service = new InsertProductService(connection);
        service.execute(new InsertProductService.Request(product));
    }

    @Override
    public void updateProduct(String code, LocalDateTime now, String action, ComarProduct changes) throws ComarControllerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ComarProductHistorial> searchProductHistorial(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
