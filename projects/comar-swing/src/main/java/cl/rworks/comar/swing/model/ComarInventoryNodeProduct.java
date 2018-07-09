/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarUtils;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;

/**
 *
 * @author aplik
 */
public class ComarInventoryNodeProduct implements TreeNode {

    private ComarInventoryNodeCategory category;
    private ComarProduct model;

    public ComarInventoryNodeProduct(ComarProduct model) {
        this.model = model;
    }

    public ComarProduct getModel() {
        return model;
    }

    public ComarInventoryNodeCategory getCategory() {
        return category;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public TreeNode getParent() {
        return category;
    }

    public void setParent(ComarInventoryNodeCategory parent) {
        this.category = parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return -1;
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public Enumeration children() {
        return null;
    }

    public void updateCode(String code) throws ComarModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.editProductCode(model, code);
            tx.commit();
            model.setCode(code);
        } catch (ComarServiceException ex) {
            throw new ComarModelException("Error al actualizar codigo: " + ex.getMessage(), ex);
        }
    }

    public void updateDescription(String description) throws ComarModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.editProductDescription(model, description);
            tx.commit();
            model.setDescription(description);
        } catch (ComarServiceException ex) {
            throw new ComarModelException("Error al actualizar descripcion: " + ex.getMessage(), ex);
        }
    }

    public void updateMetric(ComarMetric metric) throws ComarModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.editProductMetric(model, metric);
            tx.commit();
            model.setMetric(metric);
        } catch (ComarServiceException ex) {
            throw new ComarModelException("Error al actualizar metrica: " + ex.getMessage(), ex);
        }
    }
}
