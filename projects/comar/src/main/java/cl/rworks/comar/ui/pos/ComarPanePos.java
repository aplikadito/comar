/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.pos;

import cl.rworks.comar.core.ComarContext;
import cl.rworks.comar.ui.ComarPaneBanner;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author rgonzalez
 */
public class ComarPanePos extends BorderPane {

    private ComarContext context;
    //
    private ComarPaneBanner banner;

    public ComarPanePos(ComarContext context) {
        this.context = context;

        this.banner = new ComarPaneBanner("Punto de Venta");
        this.setTop(banner);

    }

}
