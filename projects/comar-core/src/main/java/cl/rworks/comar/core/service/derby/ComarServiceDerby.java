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
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.derby.jdbc.EmbeddedDataSource;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.FacturaEntity;
import cl.rworks.comar.core.model.FacturaUnidadEntity;
import cl.rworks.comar.core.model.MetricaEntity;
import cl.rworks.comar.core.model.ProductoEntity;
import cl.rworks.comar.core.model.VentaEntity;
import cl.rworks.comar.core.model.VentaUnidadEntity;

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
        return GetAllProducto.serve(tx.getConnection());
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
    public void insertProducto(ProductoEntity producto, CategoriaEntity category) throws ComarServiceException {
        InsertProducto.serve(tx.getConnection(), producto, category);
    }

    @Override
    public ProductoEntity insertProductoPorCodigo(String codigo, CategoriaEntity category) throws ComarServiceException {
        return InsertProducto.serve(tx.getConnection(), codigo, category);
    }

    public List<CategoriaEntity> getAllCategorias() throws ComarServiceException {
        return GetAllCategoria.serve(tx.getConnection());
    }

    public void insertCategoria(CategoriaEntity category) throws ComarServiceException {
        InsertCategoria.serve(tx.getConnection(), category);
    }

    @Override
    public void deleteCategoria(CategoriaEntity category) throws ComarServiceException {
        DeleteCategoria.serve(tx.getConnection(), category.getNombre());
    }

    public void deleteProducts(List<ProductoEntity> products) throws ComarServiceException {
        DeleteProductoBatch.serve(tx.getConnection(), products);
    }

    public void updateProductoPropiedad(ProductoEntity product, String propiedad, Object valor) throws ComarServiceException {
        UpdateProductoPropiedad.serve(tx.getConnection(), product, propiedad, valor);
    }

    public void updateCategoriaDeProductos(List<ProductoEntity> products, CategoriaEntity category) throws ComarServiceException {
        UpdateCategoriaDeProductos.serve(tx.getConnection(), products, category);
    }

    public List<MetricaEntity> getAllMetrics() throws ComarServiceException {
        return GetAllMetrica.serve(tx.getConnection());
    }

    public CategoriaEntity insertCategoriaPorNombre(String name) throws ComarServiceException {
        return InsertCategoria.serve(tx.getConnection(), name);
    }

    public List<FacturaEntity> getAllFactura() throws ComarServiceException {
        return GetAllFactura.serve(tx.getConnection());
    }

    public List<FacturaUnidadEntity> getAllFacturaUnidad() throws ComarServiceException {
        return GetAllFacturaUnidad.serve(tx.getConnection());
    }

    public void insertFactura(FacturaEntity factura) throws ComarServiceException {
        InsertFactura.serve(tx.getConnection(), factura);
    }

    @Override
    public void updateFactura(FacturaEntity factura) throws ComarServiceException {
        UpdateFactura.serve(tx.getConnection(), factura);
    }

    public void deleteFacturas(List<FacturaEntity> facturas) throws ComarServiceException {
        DeleteFacturas.serve(tx.getConnection(), facturas);
    }

    public void deleteFacturaUnidades(List<FacturaUnidadEntity> unidades) throws ComarServiceException {
        DeleteFacturaUnidades.serve(tx.getConnection(), unidades);
    }

    public void insertFacturaUnidad(FacturaUnidadEntity facturaUnidad, FacturaEntity factura, ProductoEntity producto) throws ComarServiceException {
        InsertFacturaUnidad.serve(tx.getConnection(), facturaUnidad, factura, producto);
    }

    public List<VentaEntity> getAllVenta() throws ComarServiceException {
        return GetAllVenta.serve(tx.getConnection());
    }

    public List<VentaUnidadEntity> getAllVentaUnidad() throws ComarServiceException {
        return GetAllVentaUnidad.serve(tx.getConnection());
    }

    public void updateFacturaUnidadPropiedad(FacturaUnidadEntity entity, String propiedad, Object valor) throws ComarServiceException {
        UpdateFacturaUnidadPropiedad.serve(tx.getConnection(), entity, propiedad, valor);
    }

    @Override
    public boolean existsProductCode(String code) throws ComarServiceException {
        ProductoEntity product = GetProductoPorCodigo.serve(tx.getConnection(), code);
        return product != null;
    }

    public void checkProductCode(String code) throws ComarServiceException {
        ProductoEntity product = GetProductoPorCodigo.serve(tx.getConnection(), code);
        if (product != null) {
            throw new ComarServiceException("El codigo del producto ya existe: " + code);
        }
    }

    public void updateCategoriaPropiedad(CategoriaEntity entity, String propiedad, Object valor) throws ComarServiceException {
        UpdateCategoriaPropiedad.serve(tx.getConnection(), entity, propiedad, valor);
    }

    public void insertProductoBatch(List<ProductoEntity> productos, CategoriaEntity categoria) throws ComarServiceException {
        InsertProductoBatch.serve(tx.getConnection(), productos, categoria);
    }

    public void insertVenta(VentaEntity venta) throws ComarServiceException {
        InsertVenta.serve(tx.getConnection(), venta);
    }

    public void insertVentaUnidad(VentaUnidadEntity ventaUnidad, VentaEntity venta, ProductoEntity producto) throws ComarServiceException {
        InsertVentaUnidad.serve(tx.getConnection(), ventaUnidad, venta, producto);
    }
}
