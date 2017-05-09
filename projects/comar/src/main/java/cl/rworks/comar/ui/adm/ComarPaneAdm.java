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

    public static final int INVENTORY = 0;
    public static final int SELLS = 1;
    
    private ComarPaneBanner paneBanner;
    private ComarPaneAdmMenu paneMenu;
    private BorderPane paneContent;
    //
    private ComarPaneAdmInventory paneInventory;
    private ComarPaneAdmSells paneSells;

    public ComarPaneAdm(ComarContext context) {
        this.paneBanner = new ComarPaneBanner("Administracion");
        this.paneMenu = new ComarPaneAdmMenu(this, context);
        
        this.paneContent = new BorderPane();
        this.paneContent.getStyleClass().add("comar-content");
        
        this.paneInventory = new ComarPaneAdmInventory(context);
        this.paneSells = new ComarPaneAdmSells(this, context);

        setTop(paneBanner);
        setLeft(paneMenu);
        setCenter(paneContent);
        
        setContent(INVENTORY);
    }

    public final void setContent(int pane) {
//        paneContent.getChildren().clear();

        switch (pane) {
            case INVENTORY:
//                paneContent.getChildren().add(paneInventory);
                paneContent.setCenter(paneInventory);
                break;
            case SELLS:
                paneContent.setCenter(paneSells);
                break;
            default:
                throw new RuntimeException("Pane no registrado: " + pane);
        }

    }

}
