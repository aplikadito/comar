/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cl.rworks.comar.core.model.ProductoEntity;

/**
 *
 * @author aplik
 */
public class ComarProduct {

    private static final Logger LOG = LoggerFactory.getLogger(ComarProduct.class);
    private ComarCategory category;
    private ProductoEntity entity;

    public ComarProduct(ProductoEntity entity) {
        this.entity = entity;
    }

    public ProductoEntity getEntity() {
        return entity;
    }

    public void setCategory(ComarCategory category) {
        this.category = category;
    }
    
    public ComarCategory getCategory() {
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
