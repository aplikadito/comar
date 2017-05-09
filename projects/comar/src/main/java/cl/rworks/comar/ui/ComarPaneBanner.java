/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui;

import cl.rworks.comar.ui.adm.ComarSceneAdm;
import cl.rworks.comar.ui.pos.ComarScenePos;
import com.pepperonas.fxiconics.awf.FxFontAwesome;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneBanner extends BorderPane {

    private final Label labelTitle;
    
    private Label iconPos;

    public ComarPaneBanner() {
        this("");
    }

    public ComarPaneBanner(String title) {
        getStyleClass().add("comar-banner");

        labelTitle = new Label(title);
        labelTitle.setId("comar-banner-title");
        setLeft(labelTitle);

        FlowPane paneButtons = new FlowPane(Orientation.HORIZONTAL);
        paneButtons.setAlignment(Pos.CENTER_RIGHT);
        paneButtons.getStyleClass().add("comar-flowpane-10");
        paneButtons.setStyle("-fx-padding: 0 20 0 0;");

        iconPos = ComarIconLoader.getIcon(ComarIconLoader.POS, 24);
        Label iconAdm = ComarIconLoader.getIcon(ComarIconLoader.ADM, 24);

        iconPos.setTooltip(new Tooltip("Ir al Punto de Venta"));
        iconAdm.setTooltip(new Tooltip("Ir a Administracion"));
        
        iconPos.getStyleClass().add("comar-link");
        iconAdm.getStyleClass().add("comar-link");

        iconPos.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Window window = ((Node) event.getSource()).getScene().getWindow();
                Stage stage = (Stage) window;
                stage.setScene(ComarScenePos.getInstance());
                stage.setWidth(1024);
                stage.setHeight(700);
            }
        });
        
        iconPos.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            }
        });

        iconAdm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Window window = ((Node) event.getSource()).getScene().getWindow();
                Stage stage = (Stage) window;
                stage.setScene(ComarSceneAdm.getInstance());
                stage.setWidth(1024);
                stage.setHeight(700);
            }
        });

        paneButtons.getChildren().add(iconPos);
        paneButtons.getChildren().add(iconAdm);

        setRight(paneButtons);
    }

    public void setTitle(String title) {
        this.labelTitle.setText(title);
    }

}
