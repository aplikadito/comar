/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import cl.rworks.comar.core.model.ComarContext;
import cl.rworks.comar.ui.ComarPaneBanner;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneAdm extends BorderPane {

    public static final int PRODUCTS = 0;
    public static final int CATEGORIES = 1;
    public static final int STOCK = 2;
    public static final int SELLS = 3;
    //
    private ComarContext context;
    //
    private ComarPaneBanner paneBanner;
    private ComarPaneAdmMenu paneMenu;
    private BorderPane paneContent;
    //
    private ComarPaneAdmProducts paneProducts;
    private ComarPaneAdmCategories paneCategories;
    private ComarPaneAdmStock paneStock;
    private ComarPaneAdmSells paneSells;

    public ComarPaneAdm(ComarContext context) {
        this.context = context;

        this.paneBanner = new ComarPaneBanner("Administracion");
        this.paneMenu = new ComarPaneAdmMenu(this, context);

        this.paneContent = new BorderPane();
        this.paneContent.getStyleClass().add("comar-content");

        this.paneProducts = new ComarPaneAdmProducts();
        this.paneCategories = new ComarPaneAdmCategories(context);
        this.paneStock = new ComarPaneAdmStock(context);
        this.paneSells = new ComarPaneAdmSells(this, context);

        setTop(paneBanner);
        setLeft(paneMenu);
        setCenter(paneContent);

        setContent(PRODUCTS);
    }

    public final void setContent(int pane) {
        switch (pane) {
            case PRODUCTS:
                paneContent.setCenter(paneProducts);
                paneProducts.init(context);
                break;
            case CATEGORIES:
                paneContent.setCenter(paneCategories);
                break;
            case STOCK:
                paneContent.setCenter(paneStock);
                break;
            case SELLS:
                paneContent.setCenter(paneSells);
                break;
            default:
                throw new RuntimeException("Pane no registrado: " + pane);
        }
    }

}
