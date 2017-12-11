/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import cl.rworks.comar.core.ComarContext;
import cl.rworks.comar.ui.ComarIconLoader;
import cl.rworks.comar.ui.ComarPaneSectionTitle;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import cl.rworks.comar.core.impl.ComarStockKite;
import cl.rworks.comar.core.model.ComarStock;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneAdmStock extends BorderPane {

    private ComarContext context;
    //
    private PaneMain paneMain;
    private PaneAdd paneAdd;
    private PaneEdit paneEdit;

    public ComarPaneAdmStock(ComarContext context) {
        this.context = context;

        paneMain = new PaneMain();
        paneAdd = new PaneAdd();
        paneEdit = new PaneEdit();

        show(paneMain);
    }

    private void show(Node pane) {
        setCenter(pane);
    }

    private class PaneMain extends BorderPane {

        public PaneMain() {
            setTop(new ComarPaneSectionTitle("INVENTARIO"));

            TableView<ComarStock> table = new TableView();
            TableColumn firstNameCol = new TableColumn("Codigo");
            table.getColumns().addAll(firstNameCol);

            BorderPane paneTable = new BorderPane();
            paneTable.setCenter(table);

            FlowPane paneSearch = new FlowPane(new Label("Buscar"), new TextField());
            paneSearch.getStyleClass().addAll("comar-flowpane");
            paneTable.setBottom(paneSearch);

            setCenter(paneTable);

            Button buttonAdd = new Button("Agregar");
            buttonAdd.setOnMouseClicked(e -> show(paneAdd));

            Button buttonEdit = new Button("Editar");
            buttonEdit.setOnMouseClicked(e -> show(paneEdit));

            FlowPane paneButtons = new FlowPane(buttonAdd, buttonEdit);
            paneButtons.setAlignment(Pos.CENTER);
            paneButtons.getStyleClass().addAll("comar-flowpane");
            setBottom(paneButtons);
        }

    }

    private void goBack() {
        show(paneMain);
    }

    private class PaneTop extends ComarPaneSectionTitle {

        public PaneTop(String title) {
            super(title);
            Label buttonBack = ComarIconLoader.getIcon(ComarIconLoader.BACK, 16);
            buttonBack.getStyleClass().add("comar-link");
            buttonBack.setOnMouseClicked(e -> goBack());
            getPaneTitle().setRight(buttonBack);
        }

    }

    private class PaneAdd extends BorderPane {

        public PaneAdd() {
            setTop(new PaneTop("STOCK > AGREGAR"));
        }

    }

    private class PaneEdit extends BorderPane {

        public PaneEdit() {
            setTop(new PaneTop("STOCK > EDITAR"));
        }

    }

}
