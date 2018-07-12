/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import java.util.ArrayList;
import java.util.List;
import cl.rworks.comar.core.model.CategoriaEntity;

/**
 *
 * @author aplik
 */
public class CategoryModel {

    private CategoriaEntity entity;
    private List<ProductModel> products = new ArrayList<>();

    public CategoryModel(CategoriaEntity entity) {
        this.entity = entity;
    }

    public CategoriaEntity getEntity() {
        return entity;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void removeProduct(ProductModel pnode) {
        this.products.remove(pnode);
    }

    public void addProduct(ProductModel pnode) {
        products.add(pnode);
        pnode.setCategory(this);
    }

    void removeProducts(List<ProductModel> list) {
        products.removeAll(list);
    }

    @Override
    public String toString() {
        return entity.getNombre() + " (" + products.size() + ")";
    }

}
