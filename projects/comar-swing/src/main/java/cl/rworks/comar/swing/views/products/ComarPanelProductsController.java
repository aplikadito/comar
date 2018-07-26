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

/**
 *
 * @author aplik
 */
public class ComarPanelProductsController {

    private static final Logger LOG = LoggerFactory.getLogger(ComarPanelProductsController.class);
    //
    private ComarWorkspace ws;
    private DefaultMutableTreeNode rootNode;

    public ComarPanelProductsController() {
        ws = ComarSystem.getInstance().getWorkspace();
        initRootNode();
    }

    private void initRootNode() {
        this.rootNode = new DefaultMutableTreeNode("Categorias");
        List<ComarCategory> categories = ws.getCategories();
        for (ComarCategory c : categories) {
            DefaultMutableTreeNode cnode = new DefaultMutableTreeNode(c);
            rootNode.add(cnode);
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
            throw new ComarControllerException("Error", ex);
        }

    }

    public void removeCategory(DefaultMutableTreeNode cview) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {

            ComarCategory cmodel = (ComarCategory) cview.getUserObject();
            service.deleteCategoria(cmodel.getEntity());
            tx.commit();

            ws.removeCategoria(cmodel);
            rootNode.remove(cview);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error", ex);
        }
    }

    public List<ComarProduct> getProducts(DefaultMutableTreeNode cview) {
        if (cview != null) {
            ComarCategory cmodel = (ComarCategory) cview.getUserObject();
            return new ArrayList<>(cmodel.getProducts());
        } else {
            return ws.getCategories().stream().flatMap(e -> e.getProducts().stream()).collect(Collectors.toList());

        }
    }

    public ComarProduct insertProduct(String code, DefaultMutableTreeNode cview) throws ComarControllerException {
        ComarCategory cmodel = (ComarCategory) cview.getUserObject();

        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            ProductoEntity pentity = service.insertProductoPorCodigo(code, cmodel.getEntity());
            tx.commit();

            ComarProduct pmodel = new ComarProduct(pentity);
            ws.insertProduct(pmodel, cmodel);

            return pmodel;
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error", ex);
        }
    }

    public void deleteProducts(List<ComarProduct> products, DefaultMutableTreeNode cview) throws ComarControllerException {
        ComarCategory cmodel = (ComarCategory) cview.getUserObject();

        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            List<ProductoEntity> entityList = products.stream().map(e -> e.getEntity()).collect(Collectors.toList());
            service.deleteProducts(entityList);
            tx.commit();

            ws.removeProducts(products, cmodel);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error", ex);
        }
    }

    public List<ComarCategory> getCategoryModels() {
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
            throw new ComarControllerException("Error", ex);
        }
    }

    public void updateProductCode(ComarProduct pmodel, String code) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateProductoCodigo(pmodel.getEntity(), code);
            tx.commit();

            pmodel.getEntity().setCodigo(code);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error", ex);
        }
    }

    public void updateProductDescription(ComarProduct pmodel, String desc) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateProductoDescripcion(pmodel.getEntity(), desc);
            tx.commit();

            pmodel.getEntity().setDescripcion(desc);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error", ex);
        }
    }

    public void updateProductMetric(ComarProduct pmodel, Metrica metrica) throws ComarControllerException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateProductoMetrica(pmodel.getEntity(), metrica);
            tx.commit();

            pmodel.getEntity().setMetrica(metrica);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ComarControllerException("Error", ex);
        }
    }

}
