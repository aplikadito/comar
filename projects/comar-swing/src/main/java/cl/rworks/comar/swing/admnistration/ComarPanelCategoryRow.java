/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelCategoryRow {

    private String name = "";
    private boolean includeInBill = true;
    private int productCount = 0;

    public ComarPanelCategoryRow() {
    }

    public ComarPanelCategoryRow(String name, boolean includeInBill, int productCount) {
        this.name = name;
        this.includeInBill = includeInBill;
        this.productCount = productCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIncludeInBill() {
        return includeInBill;
    }

    public void setIncludeInBill(boolean includeInBill) {
        this.includeInBill = includeInBill;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

}
