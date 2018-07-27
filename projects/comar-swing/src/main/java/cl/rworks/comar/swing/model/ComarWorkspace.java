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
public class ComarWorkspace {

    private List<ComarCategory> categories;
    private List<ComarBill> bills;
    private List<ComarSell> sells;

    public ComarWorkspace() {
    }

    public List<ComarCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ComarCategory> categories) {
        this.categories = categories;
    }

    public List<ComarBill> getBills() {
        return bills;
    }

    public void setBills(List<ComarBill> bills) {
        this.bills = bills;
    }

    public void addCategoria(ComarCategory c) {
        this.categories.add(c);
    }

    public void removeCategoria(ComarCategory c) {
        this.categories.remove(c);
    }

    public void insertProduct(ComarProduct pmodel, ComarCategory cmodel) {
        cmodel.addProduct(pmodel);
    }

    public void removeProducts(List<ComarProduct> products, ComarCategory cmodel) {
        cmodel.removeProducts(products);
    }

    public void moveProducts(List<ComarProduct> pmodels, ComarCategory cmodel) {
        for (ComarProduct pmodel : pmodels) {
            pmodel.getCategory().removeProduct(pmodel);
            cmodel.addProduct(pmodel);
        }
    }

    public void addBill(ComarBill bill) {
        this.bills.add(bill);
    }

    public void removeBill(ComarBill bill) {
        this.bills.remove(bill);
    }

    public ComarProduct getProductByCode(String code) {
        for (ComarCategory c : categories) {
            ComarProduct p = c.getProduct(code);
            if (p != null) {
                return p;
            }
        }
        return null;
    }

    public void deleteBills(List<ComarBill> bills) {
    }

    public List<ComarSell> getSells() {
        return sells;
    }

    public void setSells(List<ComarSell> sells) {
        this.sells = sells;
    }

}
