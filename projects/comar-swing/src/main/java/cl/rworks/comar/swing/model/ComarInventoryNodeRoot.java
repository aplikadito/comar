/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.impl.ComarCategoryImpl;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.swing.ComarSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.tree.TreeNode;

/**
 *
 * @author aplik
 */
public class ComarInventoryNodeRoot implements TreeNode {

    private List<ComarInventoryNodeCategory> categoryNodes = new ArrayList<>();

    public ComarInventoryNodeRoot() {
    }

    public List<ComarInventoryNodeCategory> getCategories() {
        return categoryNodes;
    }

    public void insertCategory(String name) throws ComarModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            ComarCategory model = ComarCategoryImpl.create(name);
            service.insertCategory(model);
            tx.commit();

            addCategoryNode(new ComarInventoryNodeCategory(model));
        } catch (ComarServiceException ex) {
            throw new ComarModelException("Error al crear categoria: " + ex.getMessage(), ex);
        }
    }

    public void addCategoryNode(ComarInventoryNodeCategory cnode) {
        this.categoryNodes.add(cnode);
        cnode.setParent(this);
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return categoryNodes.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return categoryNodes.size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return categoryNodes.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration children() {
        return Collections.enumeration(categoryNodes);
    }

    public String toString() {
        return "Categorias" + " " + "(" + getTotalProducts() + ")";
    }

    public int getTotalProducts() {
        return (int) categoryNodes.stream().flatMap(e -> e.getProducts().stream()).count();
    }

    public List<ComarInventoryNodeProduct> getProducts(ComarInventoryNodeCategory selectedCategory) {
        if (selectedCategory == null) {
            return categoryNodes.stream().flatMap(e -> e.getProducts().stream()).collect(Collectors.toList());
        } else {
            return new ArrayList<>(selectedCategory.getProducts());
        }
    }

    public void removeCategory(ComarInventoryNodeCategory cnode) throws ComarModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            service.deleteCategory(cnode.getModel());
            tx.commit();
            categoryNodes.remove(cnode);
        } catch (ComarServiceException ex) {
            throw new ComarModelException("Error al eliminar categoria: " + ex.getMessage(), ex);
        }
    }

    public void deleteProducts(List<ComarInventoryNodeProduct> list) throws ComarModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            List<ComarProduct> modelList = list.stream().map(e -> e.getModel()).collect(Collectors.toList());
            service.deleteProducts(modelList);
            tx.commit();

            for (ComarInventoryNodeProduct product : list) {
                ComarInventoryNodeCategory category = product.getCategory();
                category.getProducts().remove(product);
            }
        } catch (ComarServiceException ex) {
            throw new ComarModelException("Error al eliminar productos: " + ex.getMessage(), ex);
        }
    }

    public void moveProductNodes(List<ComarInventoryNodeProduct> selectedProducts, ComarInventoryNodeCategory selectedCategory) throws ComarModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            List<ComarProduct> productModels = selectedProducts.stream().map(e -> e.getModel()).collect(Collectors.toList());
            service.updateCategoryOfProducts(productModels, selectedCategory.getModel());
            tx.commit();

            for (ComarInventoryNodeProduct pnode : selectedProducts) {
                ComarInventoryNodeCategory oldCategoy = pnode.getCategory();
                oldCategoy.getProducts().remove(pnode);
                selectedCategory.getProducts().add(pnode);
            }
        } catch (ComarServiceException ex) {
            throw new ComarModelException("Error al mover productos: " + ex.getMessage(), ex);
        }
    }

}
