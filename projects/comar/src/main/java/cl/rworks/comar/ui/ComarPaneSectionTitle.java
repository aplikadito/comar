/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneSectionTitle extends BorderPane {

    private BorderPane paneTitle;
    
    public ComarPaneSectionTitle(String title) {
        paneTitle = new BorderPane();
        Label labelTitle = new Label(title);
        labelTitle.setId("comar-section-label-title");
        paneTitle.setLeft(labelTitle);
        setTop(paneTitle);
        setCenter(new ComarPaneSectionSeparator());

        setStyle("-fx-padding: 0 0 10 0");
    }

    public BorderPane getPaneTitle() {
        return paneTitle;
    }
    
}
