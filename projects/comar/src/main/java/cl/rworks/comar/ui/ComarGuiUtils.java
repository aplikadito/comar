/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 *
 * @author rgonzalez
 */
public class ComarGuiUtils {

    public static HBox createRowSimple(Node value) {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("comar-form-row");
        hbox.getChildren().add(value);
        return hbox;
    }

    public static HBox createRow(Node key, Node value) {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("comar-form-row");
        hbox.getChildren().add(key);
        hbox.getChildren().add(value);
        return hbox;
    }

    public static HBox createRow(String key, Node value) {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("comar-form-row");
        hbox.getChildren().add(createLabel(key));
        hbox.getChildren().add(value);
        return hbox;
    }

    public static Label createLabel(String text) {
        return createLabel(text, text);
    }

    public static Label createLabel(String text, String tooltip) {
        Label label = new Label(text);
        label.setTooltip(new Tooltip(tooltip));
        label.setMinWidth(120);
        return label;
    }

}
