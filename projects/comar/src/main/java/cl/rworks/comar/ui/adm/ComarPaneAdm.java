/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import cl.rworks.comar.core.ComarContext;
import cl.rworks.comar.ui.ComarPaneBanner;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneAdm extends BorderPane {

    public static final int PRODUCTS = 0;
    public static final int CATEGORIES = 1;
    public static final int SELLS = 2;

    private ComarPaneBanner paneBanner;
    private ComarPaneAdmMenu paneMenu;
    private BorderPane paneContent;
    //
    private ComarPaneAdmProducts paneProducts;
    private ComarPaneAdmCategories paneCategories;
    private ComarPaneAdmSells paneSells;

    public ComarPaneAdm(ComarContext context) {
        this.paneBanner = new ComarPaneBanner("Administracion");
        this.paneMenu = new ComarPaneAdmMenu(this, context);

        this.paneContent = new BorderPane();
        this.paneContent.getStyleClass().add("comar-content");

        this.paneProducts = new ComarPaneAdmProducts(context);
        this.paneCategories = new ComarPaneAdmCategories(context);
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
                break;
            case CATEGORIES:
                paneContent.setCenter(paneCategories);
                break;
            case SELLS:
                paneContent.setCenter(paneSells);
                break;
            default:
                throw new RuntimeException("Pane no registrado: " + pane);
        }
    }

}
