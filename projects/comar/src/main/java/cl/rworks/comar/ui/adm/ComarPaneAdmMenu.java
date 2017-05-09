/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import cl.rworks.comar.core.ComarContext;
import com.pepperonas.fxiconics.awf.FxFontAwesome;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneAdmMenu extends VBox {

    private ComarPaneAdm paneParent;
    private ComarContext context;
    private ComarPaneAdmMenuItem menuInventory;
    private ComarPaneAdmMenuItem menuSells;

    public ComarPaneAdmMenu(ComarPaneAdm parent, ComarContext context) {
        this.paneParent = parent;
        this.context = context;

        getStyleClass().add("comar-menu");
        setMinWidth(200);

        this.menuInventory = new ComarPaneAdmMenuItem("Inventario", FxFontAwesome.Icons.faw_folder);
        this.menuInventory.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                paneParent.setContent(ComarPaneAdm.INVENTORY);
            }
        });

        this.menuSells = new ComarPaneAdmMenuItem("Ventas", FxFontAwesome.Icons.faw_briefcase);
        this.menuSells.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                paneParent.setContent(ComarPaneAdm.SELLS);
            }
        });

        getChildren().add(menuInventory);
        getChildren().add(menuSells);
    }

}
