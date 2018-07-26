/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import java.util.ArrayList;
import java.util.List;
import cl.rworks.comar.core.model.CategoriaEntity;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aplik
 */
public class ComarCategory {

    private CategoriaEntity entity;
    private List<ComarProduct> products = new ArrayList<>();
    private Map<String, ComarProduct> index = new HashMap<>();

    public ComarCategory(CategoriaEntity entity) {
        this.entity = entity;
    }

    public CategoriaEntity getEntity() {
        return entity;
    }

    public List<ComarProduct> getProducts() {
        return products;
    }

    public void removeProduct(ComarProduct pnode) {
        this.products.remove(pnode);
        this.index.remove(pnode.getEntity().getCodigo());
    }

    public void addProduct(ComarProduct pnode) {
        products.add(pnode);
        index.put(pnode.getEntity().getCodigo(), pnode);
        pnode.setCategory(this);
    }

    void removeProducts(List<ComarProduct> list) {
        products.removeAll(list);
    }

    public ComarProduct getProduct(String code) {
        return this.index.get(code);
    }

    @Override
    public String toString() {
        return entity.getNombre() + " (" + products.size() + ")";
    }

}
