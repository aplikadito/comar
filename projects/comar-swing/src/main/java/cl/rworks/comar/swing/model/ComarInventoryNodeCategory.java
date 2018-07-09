/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.impl.ComarProductImpl;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.ComarTransaction;
import cl.rworks.comar.swing.ComarSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeNode;

/**
 *
 * @author aplik
 */
public class ComarInventoryNodeCategory implements TreeNode {

    private ComarInventoryNodeRoot root;
    private ComarCategory model;
    private List<ComarInventoryNodeProduct> products = new ArrayList<>();

    public ComarInventoryNodeCategory(ComarCategory model) {
        this.model = model;
    }

    public ComarCategory getModel() {
        return model;
    }

    public List<ComarInventoryNodeProduct> getProducts() {
        return products;
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
        return root;
    }

    public void setParent(ComarInventoryNodeRoot root) {
        this.root = root;
    }

    @Override
    public int getIndex(TreeNode node) {
        return products.indexOf(node);
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
        return Collections.enumeration(products);
    }

    public ComarInventoryNodeProduct insertProduct(String code) throws ComarModelException {
        ComarService service = ComarSystem.getInstance().getService();
        try (ComarTransaction tx = service.createTransaction()) {
            ComarProduct pmodel = ComarProductImpl.create(code);
            service.insertProduct(pmodel, getModel());
            tx.commit();

            ComarInventoryNodeProduct pnode = new ComarInventoryNodeProduct(pmodel);
            addProductNode(pnode);
            return pnode;
        } catch (ComarServiceException ex) {
            throw new ComarModelException("Error al agregar producto: " + ex.getMessage(), ex);
        }
    }

    public void addProductNode(ComarInventoryNodeProduct pnode) {
        products.add(pnode);
        pnode.setParent(this);
    }

    public String toString() {
        return model.getName() + " (" + products.size() + ")";
    }
}
