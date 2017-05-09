/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneExit extends HBox {

    public ComarPaneExit() {
        Label label = new Label("Desea salir?");

        Button buttonBack = new Button("Volver");
        Button buttonExit = new Button("Salir");
        
        VBox paneLabel = new VBox(label);
        paneLabel.setAlignment(Pos.CENTER);
        paneLabel.setMinHeight(40);
        
        FlowPane paneButtons = new FlowPane(buttonBack, buttonExit);
        paneButtons.setAlignment(Pos.CENTER);
        paneButtons.setHgap(10);
        paneButtons.setVgap(10);

        VBox vbox = new VBox(paneLabel, paneButtons);
        vbox.setAlignment(Pos.CENTER);
        
        setFillHeight(true);
        setAlignment(Pos.CENTER);
        getChildren().add(vbox);
    }

}
