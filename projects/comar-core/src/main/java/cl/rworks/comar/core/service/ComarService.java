/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service;

import cl.rworks.comar.core.model.Metrica;
import java.util.List;
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
public interface ComarService {

    int DERBY = 0;
    int PERMAZEN = 1;
    //
    int MEMORY = 0;
    int DISK = 1;
    int SERVER = 2;

    public void startup(int dbOption);

    public ComarTransaction createTransaction() throws ComarServiceException;

    public List<ProductoEntity> getAllProductos() throws ComarServiceException;

    public List<ProductoEntity> searchProductByCodeOrDescription(String str) throws ComarServiceException;

    public ProductoEntity getProductoPorCodigo(String codigo) throws ComarServiceException;

    public void insertProducto(ProductoEntity producto, CategoriaEntity categoria) throws ComarServiceException;
    
    public ProductoEntity insertProductoPorCodigo(String codigo, CategoriaEntity categoria) throws ComarServiceException;

    public List<CategoriaEntity> getAllCategorias() throws ComarServiceException;

    public void insertCategoria(CategoriaEntity category) throws ComarServiceException;

    public void deleteCategoria(CategoriaEntity category) throws ComarServiceException;

    public void deleteProducts(List<ProductoEntity> products) throws ComarServiceException;

    public void updateProductoPropiedad(ProductoEntity producto, String propiedad, Object valor) throws ComarServiceException;

    public void updateCategoriaDeProductos(List<ProductoEntity> products, CategoriaEntity category) throws ComarServiceException;

    public List<MetricaEntity> getAllMetrics() throws ComarServiceException;

    public CategoriaEntity insertCategoriaPorNombre(String name) throws ComarServiceException;

    public List<FacturaEntity> getAllFactura() throws ComarServiceException;

    public List<FacturaUnidadEntity> getAllFacturaUnidad() throws ComarServiceException;

    public void insertFactura(FacturaEntity factura) throws ComarServiceException;

    public void insertFacturaUnidad(FacturaUnidadEntity facturaUnidad, FacturaEntity factura, ProductoEntity producto) throws ComarServiceException;

    public void updateFactura(FacturaEntity entity) throws ComarServiceException;

    public void deleteFacturas(List<FacturaEntity> collect) throws ComarServiceException;

    public void deleteFacturaUnidades(List<FacturaUnidadEntity> collect) throws ComarServiceException;

    public List<VentaEntity> getAllVenta() throws ComarServiceException;

    public List<VentaUnidadEntity> getAllVentaUnidad() throws ComarServiceException;

    public void updateFacturaUnidadPropiedad(FacturaUnidadEntity entity, String propiedad, Object valor) throws ComarServiceException;

    public boolean existsProductCode(String code) throws ComarServiceException;

    public void checkProductCode(String code) throws ComarServiceException;

    public void updateCategoriaPropiedad(CategoriaEntity entity, String propiedad, Object valor) throws ComarServiceException;

    public void insertProductoBatch(List<ProductoEntity> productos, CategoriaEntity categoria) throws ComarServiceException;

    public void insertVenta(VentaEntity entity) throws ComarServiceException;
    
    public void insertVentaUnidad(VentaUnidadEntity ventaUnidad, VentaEntity venta, ProductoEntity producto) throws ComarServiceException;
    
}
