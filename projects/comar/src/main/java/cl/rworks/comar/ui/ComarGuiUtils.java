/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author rgonzalez
 */
public class ComarGuiUtils {

    public static void showInfo(String infoMessage) {
        showAlert("Informacion", "", infoMessage, AlertType.INFORMATION);
    }

    public static void showWarn(String infoMessage) {
        showAlert("Aviso", "", infoMessage, AlertType.WARNING);
    }

    public static void showInfo(String titleBar, String infoMessage) {
        showAlert(titleBar, "", infoMessage, AlertType.INFORMATION);
    }

    public static void showWarn(String titleBar, String infoMessage) {
        showAlert(titleBar, "", infoMessage, AlertType.WARNING);
    }

    public static void showAlert(String titleBar, String infoMessage, AlertType type) {
        showAlert(titleBar, "", infoMessage, type);
    }

    public static void showAlert(String titleBar, String headerMessage, String infoMessage, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

//    public static HBox createRowSimple(Node value) {
//        HBox hbox = new HBox();
//        hbox.getStyleClass().add("comar-form-row");
//        hbox.getChildren().add(value);
//        return hbox;
//    }
//
//    public static HBox createRow(Node key, Node value) {
//        HBox hbox = new HBox();
//        hbox.getStyleClass().add("comar-form-row");
//        hbox.getChildren().add(key);
//        hbox.getChildren().add(value);
//        return hbox;
//    }
//
//    public static HBox createRow(String key, Node value) {
//        HBox hbox = new HBox();
//        hbox.getStyleClass().add("comar-form-row");
//        hbox.getChildren().add(createLabel(key));
//        hbox.getChildren().add(value);
//        return hbox;
//    }
//
//    public static Label createLabel(String text) {
//        return createLabel(text, text);
//    }
//
//    public static Label createLabel(String text, String tooltip) {
//        Label label = new Label(text);
//        label.setTooltip(new Tooltip(tooltip));
//        label.setMinWidth(120);
//        return label;
//    }
}
