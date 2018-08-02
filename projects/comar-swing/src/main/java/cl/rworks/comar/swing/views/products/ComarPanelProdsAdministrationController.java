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
import cl.rworks.comar.swing.model.ComarWorkspace;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.tree.DefaultMutableTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.core.model.ProductoEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aplik
 */
public class ComarPanelProdsAdministrationController {

    private static final Logger LOG = LoggerFactory.getLogger(ComarPanelProdsAdministrationController.class);
    //
    private ComarWorkspace ws;
    private DefaultMutableTreeNode rootNode;
    private Map<ComarCategory, DefaultMutableTreeNode> categoryToNode;

    public ComarPanelProdsAdministrationController() {
        ws = ComarSystem.getInstance().getWorkspace();
        initRootNode();
    }

    private void initRootNode() {
        this.rootNode = new DefaultMutableTreeNode("Categorias");
        this.categoryToNode = new HashMap<>();
        List<ComarCategory> categories = ws.getCategories();
        for (ComarCategory c : categories) {
            DefaultMutableTreeNode cnode = new DefaultMutableTreeNode(c);
            rootNode.add(cnode);
            categoryToNode.put(c, cnode);
        }
    }

    public DefaultMutableTreeNode getRootNode() {
        return rootNode;
    }

    public void insertCategory(String name) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            CategoriaEntity cat = service.insertCategoriaPorNombre(name);
            tx.commit();

            ComarCategory cnode = new ComarCategory(cat);
            ws.addCategoria(cnode);
            rootNode.add(new DefaultMutableTreeNode(cnode));
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }

    }

    public void removeCategory(ComarCategory category) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {

            service.deleteCategoria(category.getEntity());
            tx.commit();

            ws.removeCategoria(category);
            DefaultMutableTreeNode cview = categoryToNode.remove(category);
            rootNode.remove(cview);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }
    }

    public void updateCategory(ComarCategory category, String name) throws ComarControllerException {
        if (name.trim().equals(CategoriaEntity.DEFAULT_CATEGORY)) {
            throw new ComarControllerException("Nombre de Categoria reservado por el sistema: " + CategoriaEntity.DEFAULT_CATEGORY);
        }

        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateCategoriaPropiedad(category.getEntity(), "NOMBRE", name);
            tx.commit();

            category.getEntity().setNombre(name);
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

            ComarProduct product = new ComarProduct(pentity);
            ws.insertProduct(product, category);

            return product;
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }
    }

    public void deleteProducts(List<ComarProduct> products) throws ComarControllerException {
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
                service.updateProductoCodigo(product.getEntity(), code.trim());
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
            service.updateProductoDescripcion(product.getEntity(), desc.trim());
            tx.commit();

            product.getEntity().setDescripcion(desc);
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
            service.updateProductoMetrica(product.getEntity(), metrica);
            tx.commit();

            product.getEntity().setMetrica(metrica);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException(ex.getMessage(), ex);
        }
    }

}
