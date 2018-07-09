/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarProduct;
import java.util.List;
import javax.sql.DataSource;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarMetricObject;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.derby.jdbc.EmbeddedDataSource;
import cl.rworks.comar.core.service.ComarService;

/**
 *
 * @author aplik
 */
public class ComarServiceDerby implements ComarService {

    private DataSource datasource;
    private ComarTransactionDerby tx;

    public ComarServiceDerby() {
    }

    @Override
    public void startup(int dbOption) {
        switch (dbOption) {
            case MEMORY:
                throw new RuntimeException("opcion no soportada aun");
            case DISK: {
                startupDisk();
                break;
            }
            case SERVER:
                throw new RuntimeException("opcion no soportada aun");
            default:
                throw new RuntimeException("opcion no soportada: " + dbOption);
        }
    }

    private void startupDisk() {
        boolean runScript = false;
        File file = new File("storage");
        if (!file.exists()) {
            runScript = true;
        }

        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("storage" + ";create=true");
        this.datasource = ds;

        if (runScript) {
            ComarServiceDerbyDatabaseCreator creator = new ComarServiceDerbyDatabaseCreator();
            creator.create(datasource);
        }
    }

    @Override
    public ComarTransaction createTransaction() throws ComarServiceException {
        if (tx != null) {
            tx.close();
        }

        try {
            Connection connection = datasource.getConnection();
            tx = new ComarTransactionDerby(connection);
            return tx;
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    @Override
    public List<ComarProduct> getAllProducts() throws ComarServiceException {
        return GetAllProducts.serve(tx.getConnection());
    }

    @Override
    public List<ComarProduct> searchProductByCodeOrDescription(String str) throws ComarServiceException {
        SearchProductByCodeOrDescription service = new SearchProductByCodeOrDescription(tx.getConnection());
        SearchProductByCodeOrDescription.Response response = service.execute(new SearchProductByCodeOrDescription.Request(str));
        return response.getProducts();
    }

    @Override
    public ComarProduct getProductByCode(String code) throws ComarServiceException {
        return GetProductByCode.serve(tx.getConnection(), code);
    }

    @Override
    public void insertProduct(ComarProduct product, ComarCategory category) throws ComarServiceException {
        InsertProduct.serve(tx.getConnection(), product, category);
    }

    @Override
    public void updateProduct(ComarProduct product, ComarCategory category) throws ComarServiceException {
        UpdateProduct.serve(tx.getConnection(), product, category);
    }

    public List<ComarCategory> getAllCategories() throws ComarServiceException {
        return GetAllCategories.serve(tx.getConnection());
    }

    public void insertCategory(ComarCategory category) throws ComarServiceException {
        InsertCategory.serve(tx.getConnection(), category);
    }

    @Override
    public List<ComarProduct> getProductForCategory(ComarCategory category) throws ComarServiceException {
        return GetProductsForCategory.serve(tx.getConnection(), category);
    }

    @Override
    public void deleteCategory(ComarCategory category) throws ComarServiceException {
        DeleteCategory.serve(tx.getConnection(), category);
    }

    public void deleteProducts(List<ComarProduct> products) throws ComarServiceException {
        DeleteProducts.serve(tx.getConnection(), products);
    }

    public void editProductCode(ComarProduct product, String code) throws ComarServiceException {
        UpdateProductProperty.serve(tx.getConnection(), product, "CODE", code);
    }

    public void editProductDescription(ComarProduct product, String description) throws ComarServiceException {
        UpdateProductProperty.serve(tx.getConnection(), product, "DESCRIPTION", description);
    }

    public void editProductMetric(ComarProduct product, ComarMetric metric) throws ComarServiceException {
        UpdateProductProperty.serve(tx.getConnection(), product, "METRIC", metric);
    }
    
    public void updateCategoryOfProducts(List<ComarProduct> products, ComarCategory category) throws ComarServiceException{
        UpdateCategoryOfProducts.serve(tx.getConnection(), products, category);
    }
    
    public List<ComarMetricObject> getAllMetrics() throws ComarServiceException{
        return GetAllMetrics.serve(tx.getConnection());
    }

}
