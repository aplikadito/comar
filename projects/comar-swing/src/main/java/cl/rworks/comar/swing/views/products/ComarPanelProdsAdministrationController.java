/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.products;

import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.model.ComarCategory;
import cl.rworks.comar.swing.model.ComarControllerException;
import cl.rworks.comar.swing.model.ComarProduct;
import cl.rworks.comar.swing.model.ComarEntityManager;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.core.model.ProductoEntity;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author aplik
 */
public class ComarPanelProdsAdministrationController {

    private static final Logger LOG = LoggerFactory.getLogger(ComarPanelProdsAdministrationController.class);
    //
    private ComarEntityManager ws;

    public ComarPanelProdsAdministrationController() {
        ws = ComarSystem.getInstance().getEntityManager();
    }

    public void insertCategory(String name) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            CategoriaEntity e = service.insertCategoriaPorNombre(name);
            tx.commit();

            ComarCategory category = new ComarCategory(e);
            ws.addCategory(category);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }

    }

    public void deleteCategory(ComarCategory category) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.deleteCategoria(category.getEntity());
            tx.commit();

            ws.removeCategory(category);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }
    }

    public void updateCategory(ComarCategory category, String name, BigDecimal tax1, BigDecimal tax2, BigDecimal profit) throws ComarControllerException {
//        if (name.trim().equals(CategoriaEntity.DEFAULT_CATEGORY)) {
//            throw new ComarControllerException("Nombre de Categoria reservado por el sistema: " + CategoriaEntity.DEFAULT_CATEGORY);
//        }

        if (tax1.compareTo(CategoriaEntity.MIN_PORCENTAJE) < 0) {
            throw new ComarControllerException("IVA debe ser mayor que cero");
        }

        if (tax1.compareTo(CategoriaEntity.MAX_PORCENTAJE) > 0) {
            throw new ComarControllerException("IVA debe ser menor que uno");
        }
        
        if (tax2.compareTo(CategoriaEntity.MIN_PORCENTAJE) < 0) {
            throw new ComarControllerException("Imp. Extra debe ser mayor que cero");
        }

        if (tax2.compareTo(CategoriaEntity.MAX_PORCENTAJE) > 0) {
            throw new ComarControllerException("Imp. Extra debe ser menor que uno");
        }

        if (profit.compareTo(CategoriaEntity.MIN_PORCENTAJE) < 0) {
            throw new ComarControllerException("% Ganancia debe ser mayor que cero");
        }
        
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateCategoriaPropiedad(category.getEntity(), "NOMBRE", name);
            service.updateCategoriaPropiedad(category.getEntity(), "IMPUESTOPRINCIPAL", tax1);
            service.updateCategoriaPropiedad(category.getEntity(), "IMPUESTOSECUNDARIO", tax2);
            service.updateCategoriaPropiedad(category.getEntity(), "PORCENTAJEGANANCIA", profit);
            tx.commit();

            category.getEntity().setNombre(name);
            category.getEntity().setImpuestoPrincipal(tax1);
            category.getEntity().setImpuestoSecundario(tax2);
            category.getEntity().setPorcentajeGanancia(profit);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }
    }

    public List<ComarProduct> getProducts(ComarCategory category) {
        if (category != null) {
            return new ArrayList<>(category.getProducts());
        } else {
            return ws.getCategories().stream().flatMap(e -> e.getProducts().stream()).collect(Collectors.toList());

        }
    }

    public ComarProduct insertProduct(String code, ComarCategory category) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.checkProductCode(code);

            ProductoEntity pentity = service.insertProductoPorCodigo(code, category.getEntity());
            tx.commit();

            ComarProduct product = new ComarProduct(pentity, category);
            ws.addProduct(product);

            return product;
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error: " + ex.getMessage(), ex);
        }
    }

    public void deleteProducts(List<ComarProduct> products) throws ComarControllerException {
        ComarEntityManager em = ComarSystem.getInstance().getEntityManager();
        boolean found = em.checkReferences(products);
        if (found) {
            LOG.error("Productos con referencias, no pueden ser eliminados");
            throw new ComarControllerException("Uno o mas productos no pueden ser eliminados dado que estan siendo utilizados por otras entidades");
        }

        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            List<ProductoEntity> entityList = products.stream().map(e -> e.getEntity()).collect(Collectors.toList());
            service.deleteProducts(entityList);
            tx.commit();

            ws.removeProducts(products);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }
    }

    public List<ComarCategory> getCategories() {
        return ws.getCategories();
    }

    public void moveProducts(List<ComarProduct> products, ComarCategory category) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            List<ProductoEntity> pentities = products.stream().map(e -> e.getEntity()).collect(Collectors.toList());
            CategoriaEntity centity = category.getEntity();
            service.updateCategoriaDeProductos(pentities, centity);
            tx.commit();

            ws.moveProducts(products, category);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }
    }

    public void updateProductCode(ComarProduct product, String code) throws ComarControllerException {
        if (product.getEntity().getCodigo().equals(code.trim())) {
            return;
        }

        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            if (!service.existsProductCode(code)) {
                service.updateProductoPropiedad(product.getEntity(), "CODIGO", code.trim());
                tx.commit();
                product.getEntity().setCodigo(code);
            } else {
                throw new ComarControllerException("El codigo del producto ya existe: " + code);
            }
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }
    }

    public void updateProductDescription(ComarProduct product, String desc) throws ComarControllerException {
        if (product.getEntity().getDescripcion().equals(desc.trim())) {
            return;
        }

        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateProductoPropiedad(product.getEntity(), "DESCRIPCION", desc.trim());
            tx.commit();

            product.getEntity().setDescripcion(desc);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }
    }

    public void updateProductSellPrice(ComarProduct product, BigDecimal sellPrice) throws ComarControllerException {
        if (product.getEntity().getPrecioVentaActual().equals(sellPrice)) {
            return;
        }

        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateProductoPropiedad(product.getEntity(), "PRECIOVENTAACTUAL", sellPrice);
            tx.commit();

            product.getEntity().setPrecioVentaActual(sellPrice);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }
    }

    public void updateProductMetric(ComarProduct product, Metrica metrica) throws ComarControllerException {
        if (product.getEntity().getMetrica().equals(metrica)) {
            return;
        }

        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateProductoPropiedad(product.getEntity(), "METRICA", metrica);
            tx.commit();

            product.getEntity().setMetrica(metrica);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }
    }

    public void updateProductIncludeInSell(ComarProduct product, boolean incluirEnBoleta) throws ComarControllerException {
        if (product.getEntity().isIncluirEnBoleta() == incluirEnBoleta) {
            return;
        }

        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateProductoPropiedad(product.getEntity(), "INCLUIRENBOLETA", incluirEnBoleta);
            tx.commit();

            product.getEntity().setIncluirEnBoleta(incluirEnBoleta);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }
    }

    public void updateProductPrecioVentaFijo(ComarProduct product, boolean precioVentaFijo) throws ComarControllerException {
        if (product.getEntity().isPrecioVentaFijo() == precioVentaFijo) {
            return;
        }

        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateProductoPropiedad(product.getEntity(), "PRECIOVENTAFIJO", precioVentaFijo);
            tx.commit();

            product.getEntity().setPrecioVentaFijo(precioVentaFijo);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }
    }

}
