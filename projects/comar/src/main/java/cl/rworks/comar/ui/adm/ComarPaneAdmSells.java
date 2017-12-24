/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import cl.rworks.comar.core.model.ComarContext;
import cl.rworks.comar.ui.ComarPaneSectionTitle;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneAdmSells extends BorderPane {

    public ComarPaneAdmSells(ComarPaneAdm parent, ComarContext context) {
        setTop(new ComarPaneSectionTitle("VENTAS"));
    }
}
