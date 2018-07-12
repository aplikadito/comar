/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import java.util.List;

/**
 *
 * @author aplik
 */
public class Workspace {

    private List<CategoryModel> categories;

    public Workspace() {
    }

    public List<CategoryModel> getCategoryNodes() {
        return categories;
    }

    public void setCategoryNodes(List<CategoryModel> categoryNodes) {
        this.categories = categoryNodes;
    }

    public void addCategoria(CategoryModel c) {
        this.categories.add(c);
    }

    public void removeCategoria(CategoryModel c) {
        this.categories.remove(c);
    }

    public void insertProduct(ProductModel pmodel, CategoryModel cmodel) {
        cmodel.addProduct(pmodel);
    }

    public void removeProducts(List<ProductModel> products, CategoryModel cmodel) {
        cmodel.removeProducts(products);
    }

    public void moveProducts(List<ProductModel> pmodels, CategoryModel cmodel) {
        for (ProductModel pmodel : pmodels) {
            pmodel.getCategory().removeProduct(pmodel);
            cmodel.addProduct(pmodel);
        }
    }

}
