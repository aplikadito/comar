/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.productos;

import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.model.CategoryModel;
import cl.rworks.comar.swing.model.ModelException;
import cl.rworks.comar.swing.model.ProductModel;
import cl.rworks.comar.swing.model.Workspace;
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
public class ComarPanelProductosAreaController {

    private static final Logger LOG = LoggerFactory.getLogger(ComarPanelProductosAreaController.class);
    //
    private Workspace ws;
    private DefaultMutableTreeNode rootNode;

    public ComarPanelProductosAreaController() {
        ws = ComarSystem.getInstance().getWorkspace();
        initRootNode();
    }

    private void initRootNode() {
        this.rootNode = new DefaultMutableTreeNode("Categorias");
        List<CategoryModel> categories = ws.getCategoryNodes();
        for (CategoryModel c : categories) {
            DefaultMutableTreeNode cnode = new DefaultMutableTreeNode(c);
            rootNode.add(cnode);
        }
    }

    public DefaultMutableTreeNode getRootNode() {
        return rootNode;
    }

    public void insertCategory(String name) throws ModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            CategoriaEntity cat = service.insertCategoriaPorNombre(name);
            tx.commit();

            CategoryModel cnode = new CategoryModel(cat);
            ws.addCategoria(cnode);
            rootNode.add(new DefaultMutableTreeNode(cnode));
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ModelException("Error", ex);
        }

    }

    public void removeCategory(DefaultMutableTreeNode cview) throws ModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {

            CategoryModel cmodel = (CategoryModel) cview.getUserObject();
            service.deleteCategoria(cmodel.getEntity());
            tx.commit();

            ws.removeCategoria(cmodel);
            rootNode.remove(cview);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ModelException("Error", ex);
        }
    }

    public List<ProductModel> getProducts(DefaultMutableTreeNode cview) {
        if (cview != null) {
            CategoryModel cmodel = (CategoryModel) cview.getUserObject();
            return new ArrayList<>(cmodel.getProducts());
        } else {
            return ws.getCategoryNodes().stream().flatMap(e -> e.getProducts().stream()).collect(Collectors.toList());

        }
    }

    public ProductModel insertProduct(String code, DefaultMutableTreeNode cview) throws ModelException {
        CategoryModel cmodel = (CategoryModel) cview.getUserObject();

        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            ProductoEntity pentity = service.insertProductoPorCodigo(code, cmodel.getEntity());
            tx.commit();

            ProductModel pmodel = new ProductModel(pentity);
            ws.insertProduct(pmodel, cmodel);

            return pmodel;
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ModelException("Error", ex);
        }
    }

    public void deleteProducts(List<ProductModel> products, DefaultMutableTreeNode cview) throws ModelException {
        CategoryModel cmodel = (CategoryModel) cview.getUserObject();

        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            List<ProductoEntity> entityList = products.stream().map(e -> e.getEntity()).collect(Collectors.toList());
            service.deleteProducts(entityList);
            tx.commit();

            ws.removeProducts(products, cmodel);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ModelException("Error", ex);
        }
    }

    public List<CategoryModel> getCategoryModels() {
        return ws.getCategoryNodes();
    }

    public void moveProducts(List<ProductModel> products, CategoryModel category) throws ModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            List<ProductoEntity> pentities = products.stream().map(e -> e.getEntity()).collect(Collectors.toList());
            CategoriaEntity centity = category.getEntity();
            service.updateCategoriaDeProductos(pentities, centity);
            tx.commit();

            ws.moveProducts(products, category);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ModelException("Error", ex);
        }
    }

    public void updateProductCode(ProductModel pmodel, String code) throws ModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateProductoCodigo(pmodel.getEntity(), code);
            tx.commit();

            pmodel.getEntity().setCodigo(code);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ModelException("Error", ex);
        }
    }

    public void updateProductDescription(ProductModel pmodel, String desc) throws ModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateProductoDescripcion(pmodel.getEntity(), desc);
            tx.commit();

            pmodel.getEntity().setDescripcion(desc);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ModelException("Error", ex);
        }
    }

    public void updateProductMetric(ProductModel pmodel, Metrica metrica) throws ModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.updateProductoMetrica(pmodel.getEntity(), metrica);
            tx.commit();

            pmodel.getEntity().setMetrica(metrica);
        } catch (ComarServiceException ex) {
            LOG.error("Error", ex);
            throw new ModelException("Error", ex);
        }
    }

}
