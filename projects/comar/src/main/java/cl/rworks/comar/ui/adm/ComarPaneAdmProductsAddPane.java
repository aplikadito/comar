/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import cl.rworks.comar.ui.ComarIconLoader;
import cl.rworks.comar.ui.ComarPaneSectionTitle;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author aplik
 */
public class ComarPaneAdmProductsAddPane extends BorderPane {

    private ComarPaneAdmProducts parent;

    public ComarPaneAdmProductsAddPane(ComarPaneAdmProducts parent) {
        this.parent = parent;
        initValues();
    }

    private void initValues() {
        ComarPaneSectionTitle banner = new ComarPaneSectionTitle("PRODUCTOS > AGREGAR");
        Label buttonBack = ComarIconLoader.getIcon(ComarIconLoader.BACK, 16);
        buttonBack.getStyleClass().add("comar-link");
//        buttonBack.setOnMouseClicked(e -> view(parent));
        banner.getPaneTitle().setRight(buttonBack);
        setTop(banner);
    }
    
}
