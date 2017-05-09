/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

/**
 *
 * @author rgonzalez
 */
public class ComarScrollPane extends ScrollPane {

    public ComarScrollPane(Node content) {
        super(content);
        getStyleClass().add("comar-scrollpane");
    }

}
