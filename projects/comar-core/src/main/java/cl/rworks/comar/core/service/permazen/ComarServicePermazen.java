/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.permazen;

import java.util.List;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.ValidationMode;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.MetricaEntity;
import cl.rworks.comar.core.model.ProductoEntity;

/**
 *
 * @author aplik
 */
public class ComarServicePermazen implements ComarService {

    private Permazen dataSource;

    private Class[] modelClasses = new Class[]{ //        ComarProductPermazen.class,
    //        ComarSellPermazen.class
    };

    public ComarServicePermazen() {
    }

    @Override
    public void startup(int dbOption) {
        switch (dbOption) {
            case MEMORY:
                dataSource = ComarServicePermazenUtils.createPermazen(ComarServicePermazenUtils.createOnMemoryDatabase(), modelClasses);
                break;
            case DISK:
                dataSource = ComarServicePermazenUtils.createPermazen(ComarServicePermazenUtils.createDerbyDatabase("storage"), modelClasses);
                break;
            case SERVER:
//                dataSource = KiteUtils.createPermazen(KiteUtils.createMysqlDatabase(null), modelClasses);
//                break;
                throw new RuntimeException("opcion no soportada aun");
            default:
                throw new RuntimeException("opcion no soportada: " + dataSource);
        }
    }

    @Override
    public ComarTransaction createTransaction() throws ComarServiceException {
        JTransaction jtx = dataSource.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        return new ComarTransactionPermazen(jtx);
    }

    @Override
    public List<ProductoEntity> searchProductByCodeOrDescription(String str) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductoEntity getProductoPorCodigo(String code) throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean validateCode(String code) throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CategoriaEntity> getAllCategorias() throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertCategoria(CategoriaEntity category) throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProductoEntity> getAllProductos() throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteCategoria(CategoriaEntity category) throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteProducts(List<ProductoEntity> products) throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateProductoCodigo(ProductoEntity model, String code) throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateProductoDescripcion(ProductoEntity model, String description) throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateProductoMetrica(ProductoEntity model, Metrica metric) throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateCategoriaDeProductos(List<ProductoEntity> products, CategoriaEntity model) throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MetricaEntity> getAllMetrics() throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CategoriaEntity insertCategoriaPorNombre(String name) throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductoEntity insertProductoPorCodigo(String codigo, CategoriaEntity categoria) throws ComarServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
