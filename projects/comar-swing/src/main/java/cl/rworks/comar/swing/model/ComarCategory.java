/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

import java.util.ArrayList;
import java.util.List;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.util.UUIDUtils;
import java.util.Optional;

/**
 *
 * @author aplik
 */
public class ComarCategory {

    private CategoriaEntity entity;
    private List<ComarProduct> products = new ArrayList<>();

    public ComarCategory(CategoriaEntity entity) {
        this.entity = entity;
    }

    public CategoriaEntity getEntity() {
        return entity;
    }

    public String getId() {
        return UUIDUtils.toString(entity.getId());
    }

    public List<ComarProduct> getProducts() {
        return products;
    }

    public void removeProduct(ComarProduct pnode) {
        this.products.remove(pnode);
    }

    public void addProduct(ComarProduct pnode) {
        products.add(pnode);
    }

    void removeProducts(List<ComarProduct> list) {
        products.removeAll(list);
    }

    public ComarProduct getProduct(String code) {
        Optional<ComarProduct> opt = products.stream().filter(e -> e.getEntity().getCodigo().equals(code)).findFirst();
        return opt.isPresent() ? opt.get() : null;
    }

    public ComarProduct getProductById(byte[] id) {
        Optional<ComarProduct> opt = products.stream().filter(e -> {
            String id1 = UUIDUtils.toString(e.getEntity().getId());
            String id2 = UUIDUtils.toString(id);
            return id1.equals(id2);
        }).findFirst();
        return opt.isPresent() ? opt.get() : null;
    }

    @Override
    public String toString() {
        return entity.getNombre();
    }

}
