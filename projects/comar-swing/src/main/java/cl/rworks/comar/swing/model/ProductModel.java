/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.swing.ComarSystem;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;
import cl.rworks.comar.swing.model.ModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cl.rworks.comar.core.model.ProductoEntity;

/**
 *
 * @author aplik
 */
public class ProductModel {

    private static final Logger LOG = LoggerFactory.getLogger(ProductModel.class);
    private CategoryModel category;
    private ProductoEntity entity;

    public ProductModel(ProductoEntity entity) {
        this.entity = entity;
    }

    public ProductoEntity getEntity() {
        return entity;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }
    
    public CategoryModel getCategory() {
        return category;
    }

//    public void updateCode(String code) throws ComarModelException {
//        ComarService service = ComarSystem.getInstance().getService();
//        try (ComarTransaction tx = service.createTransaction()) {
//            service.updateProductoCodigo(model, code);
//            tx.commit();
//            model.setCodigo(code);
//        } catch (ComarServiceException ex) {
//            LOG.error("Error", ex);
//            throw new ComarModelException("Error al actualizar codigo: " + ex.getMessage(), ex);
//        }
//    }
//
//    public void updateDescription(String description) throws ComarModelException {
//        ComarService service = ComarSystem.getInstance().getService();
//        try (ComarTransaction tx = service.createTransaction()) {
//            service.updateProductoDescripcion(model, description);
//            tx.commit();
//            model.setDescripcion(description);
//        } catch (ComarServiceException ex) {
//            LOG.error("Error", ex);
//            throw new ComarModelException("Error al actualizar descripcion: " + ex.getMessage(), ex);
//        }
//    }
//
//    public void updateMetric(Metrica metric) throws ComarModelException {
//        ComarService service = ComarSystem.getInstance().getService();
//        try (ComarTransaction tx = service.createTransaction()) {
//            service.updateProductoMetrica(model, metric);
//            tx.commit();
//            model.setMetrica(metric);
//        } catch (ComarServiceException ex) {
//            LOG.error("Error", ex);
//            throw new ComarModelException("Error al actualizar metrica: " + ex.getMessage(), ex);
//        }
//    }
}
