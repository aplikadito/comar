/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import com.pepperonas.fxiconics.FxIconicsLabel;
import com.pepperonas.fxiconics.awf.FxFontAwesome;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneAdmMenuItem extends HBox {

    private Label label;

    public ComarPaneAdmMenuItem(String text, FxFontAwesome.Icons icon) {
        getStyleClass().add("comar-menu-item");

        FxIconicsLabel labelIcon = (FxIconicsLabel) new FxIconicsLabel.Builder(icon).size(14).color("337AB7").build();
        labelIcon.setId("icon");

        Label labelText = new Label(text);
        labelText.setId("text");
        
        getChildren().addAll(labelIcon, labelText);

//        label = GlyphsDude.createIconLabel(icon, text, "14", "14", ContentDisplay.LEFT);
//        Text textIcon = GlyphsDude.createIcon(FontAwesomeIcon.BUG, "16");
//        getChildren().addAll(label);
    }

    public Label getLabel() {
        return label;
    }

}
