/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui;

import com.pepperonas.fxiconics.FxIconicsButton;
import com.pepperonas.fxiconics.awf.FxFontAwesome;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneSectionOption extends FlowPane {

    public ComarPaneSectionOption(String name, FxFontAwesome.Icons icon) {
        getStyleClass().add("comar-section-option");
//        setAlignment(Pos.CENTER);

//        FxIconicsLabel labelIcon = (FxIconicsLabel) new FxIconicsLabel.Builder(icon)
        FxIconicsButton labelIcon = (FxIconicsButton) new FxIconicsButton.Builder(icon)
                .size(25)
                .text(name)
                .color("337AB7")
                .build();

        labelIcon.setId("comar-section-option");
        labelIcon.setContentDisplay(ContentDisplay.BOTTOM);
        labelIcon.setMinHeight(50);
        labelIcon.setMinWidth(50);
        labelIcon.setAlignment(Pos.CENTER);

        getChildren().add(labelIcon);
    }

}
