/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import cl.rworks.comar.core.ComarContext;
import cl.rworks.comar.ui.ComarPaneSectionTitle;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneAdmInventory extends BorderPane {

    public ComarPaneAdmInventory(ComarContext context) {
        setTop(new ComarPaneSectionTitle("INVENTARIO"));

        TabPane tabbed = new TabPane();
        tabbed.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabbed.setStyle("-fx-background: white;");
        
        tabbed.getTabs().add(new Tab("Productos", new ComarPaneAdmInventoryProducts()));
        tabbed.getTabs().add(new Tab("Agregar", new ComarPaneAdmInventoryCreate(context)));
        setCenter(tabbed);
    }
}
