/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.ComarServiceException;
import java.util.List;
import javax.sql.DataSource;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.core.model.Metrica;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.derby.jdbc.EmbeddedDataSource;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.MetricaEntity;
import cl.rworks.comar.core.model.ProductoEntity;

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
    public List<ProductoEntity> getAllProductos() throws ComarServiceException {
        return GetAllProductos.serve(tx.getConnection());
    }

    @Override
    public List<ProductoEntity> searchProductByCodeOrDescription(String str) throws ComarServiceException {
        return SearchProductByCodeOrDescription.serve(tx.getConnection(), str);
    }

    @Override
    public ProductoEntity getProductoPorCodigo(String code) throws ComarServiceException {
        return GetProductoPorCodigo.serve(tx.getConnection(), code);
    }

    @Override
    public ProductoEntity insertProductoPorCodigo(String codigo, CategoriaEntity category) throws ComarServiceException {
        return InsertProductoPorCodigo.serve(tx.getConnection(), codigo, category);
    }

    public List<CategoriaEntity> getAllCategorias() throws ComarServiceException {
        return GetAllCategorias.serve(tx.getConnection());
    }

    public void insertCategoria(CategoriaEntity category) throws ComarServiceException {
        InsertCategoria.serve(tx.getConnection(), category);
    }

    @Override
    public void deleteCategoria(CategoriaEntity category) throws ComarServiceException {
        DeleteCategoriaPorNombre.serve(tx.getConnection(), category.getNombre());
    }

    public void deleteProducts(List<ProductoEntity> products) throws ComarServiceException {
        DeleteProductosPorCodigo.serve(tx.getConnection(), products);
    }

    public void updateProductoCodigo(ProductoEntity product, String code) throws ComarServiceException {
        UpdateProductoPropiedad.serve(tx.getConnection(), product, "CODIGO", code);
    }

    public void updateProductoDescripcion(ProductoEntity product, String description) throws ComarServiceException {
        UpdateProductoPropiedad.serve(tx.getConnection(), product, "DESCRIPCION", description);
    }

    public void updateProductoMetrica(ProductoEntity product, Metrica metric) throws ComarServiceException {
        UpdateProductoPropiedad.serve(tx.getConnection(), product, "METRICA", metric);
    }

    public void updateCategoriaDeProductos(List<ProductoEntity> products, CategoriaEntity category) throws ComarServiceException {
        UpdateCategoriaDeProductos.serve(tx.getConnection(), products, category);
    }

    public List<MetricaEntity> getAllMetrics() throws ComarServiceException {
        return GetAllMetricas.serve(tx.getConnection());
    }

    public CategoriaEntity insertCategoriaPorNombre(String name) throws ComarServiceException {
        return InsertCategoriaPorNombre.serve(tx.getConnection(), name);
    }

}
