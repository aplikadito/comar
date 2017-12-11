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
    private ComarPaneAdmMenuItem menuProducts;
    private ComarPaneAdmMenuItem menuCategories;
    private ComarPaneAdmMenuItem menuStock;
    private ComarPaneAdmMenuItem menuSells;

    public ComarPaneAdmMenu(ComarPaneAdm parent, ComarContext context) {
        this.paneParent = parent;
        this.context = context;

        getStyleClass().add("comar-menu");
        setMinWidth(200);

        this.menuProducts = new ComarPaneAdmMenuItem("Productos", FxFontAwesome.Icons.faw_barcode);
        this.menuProducts.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                paneParent.setContent(ComarPaneAdm.PRODUCTS);
            }
        });
        
        this.menuCategories = new ComarPaneAdmMenuItem("Categorias", FxFontAwesome.Icons.faw_folder);
        this.menuCategories.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                paneParent.setContent(ComarPaneAdm.CATEGORIES);
            }
        });
        
        this.menuStock = new ComarPaneAdmMenuItem("Inventario", FxFontAwesome.Icons.faw_book);
        this.menuStock.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                paneParent.setContent(ComarPaneAdm.STOCK);
            }
        });

        this.menuSells = new ComarPaneAdmMenuItem("Ventas", FxFontAwesome.Icons.faw_briefcase);
        this.menuSells.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                paneParent.setContent(ComarPaneAdm.SELLS);
            }
        });

        getChildren().add(menuProducts);
        getChildren().add(menuCategories);
        getChildren().add(menuStock);
        getChildren().add(menuSells);
    }

}
