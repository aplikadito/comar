/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.model;

/**
 *
 * @author aplik
 */
public class ComarWorkspace {

    private ComarInventoryNodeRoot inventoryRootNode;
    
    public ComarWorkspace(){
    }

    public ComarInventoryNodeRoot getInventoryRootNode() {
        return inventoryRootNode;
    }

    public void setInventoryRootNode(ComarInventoryNodeRoot inventoryRoot) {
        this.inventoryRootNode = inventoryRoot;
    }

}
