/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import cl.rworks.comar.core.ComarCategory;
import cl.rworks.comar.core.ComarContext;
import cl.rworks.comar.ui.ComarIconLoader;
import cl.rworks.comar.ui.ComarPaneSectionTitle;
import java.util.NavigableSet;
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
import org.apache.log4j.Category;
import org.jsimpledb.JTransaction;
import org.jsimpledb.ValidationMode;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneAdmCategories extends BorderPane {

    private ComarContext context;
    //
    private PaneMain paneInv;
    private PaneAdd paneAdd;
    private PaneEdit paneEdit;
    private PaneDelete paneDelete;

    public ComarPaneAdmCategories(ComarContext context) {
        this.context = context;

        paneInv = new PaneMain();
        paneAdd = new PaneAdd();
        paneEdit = new PaneEdit();
        paneDelete = new PaneDelete();

        view(paneInv);
    }

    private void view(Node pane) {
        setCenter(pane);
    }

    private class PaneMain extends BorderPane {

        public PaneMain() {
            setTop(new ComarPaneSectionTitle("CATEGORIAS"));

            TableView<ComarCategory> table = new TableView();
            TableColumn firstNameCol = new TableColumn("Nombre");
            firstNameCol.setCellValueFactory(
                    new PropertyValueFactory<ComarCategory, String>("name")
            );
            
            
            table.getColumns().addAll(firstNameCol);

            JTransaction jtx = context.getDatabase().get().createTransaction(true, ValidationMode.DISABLED);
            JTransaction.setCurrent(jtx);
            try {
                ComarCategory[] categories = ComarCategory.getAll().toArray(new ComarCategory[0]);
                table.getItems().addAll(categories);
//                table.setItems(categories);
            } finally {

            }

            BorderPane paneTable = new BorderPane();
            paneTable.setCenter(table);

            FlowPane paneSearch = new FlowPane(new Label("Buscar"), new TextField());
            paneSearch.getStyleClass().addAll("comar-flowpane");
            paneTable.setBottom(paneSearch);

            setCenter(paneTable);

            Button buttonAdd = new Button("Agregar");
            buttonAdd.setOnMouseClicked(e -> view(paneAdd));

            Button buttonEdit = new Button("Editar");
            buttonEdit.setOnMouseClicked(e -> view(paneEdit));

            Button buttonDelete = new Button("Eliminar");
            buttonDelete.setOnMouseClicked(e -> view(paneDelete));

            FlowPane paneButtons = new FlowPane(buttonAdd, buttonEdit, buttonDelete);
            paneButtons.setAlignment(Pos.CENTER);
            paneButtons.getStyleClass().addAll("comar-flowpane");
            setBottom(paneButtons);
        }

    }

    private class PaneAdd extends BorderPane {

        public PaneAdd() {
            ComarPaneSectionTitle banner = new ComarPaneSectionTitle("CATEGORIAS > AGREGAR");
            Label buttonBack = ComarIconLoader.getIcon(ComarIconLoader.BACK, 16);
            buttonBack.getStyleClass().add("comar-link");
            buttonBack.setOnMouseClicked(e -> view(paneInv));
            banner.getPaneTitle().setRight(buttonBack);
            setTop(banner);
        }

    }

    private class PaneEdit extends BorderPane {

        public PaneEdit() {
            ComarPaneSectionTitle banner = new ComarPaneSectionTitle("CATEGORIAS > EDITAR");
            Label buttonBack = ComarIconLoader.getIcon(ComarIconLoader.BACK, 16);
            buttonBack.getStyleClass().add("comar-link");
            buttonBack.setOnMouseClicked(e -> view(paneInv));
            banner.getPaneTitle().setRight(buttonBack);
            setTop(banner);
        }

    }

    private class PaneDelete extends BorderPane {

        public PaneDelete() {
            ComarPaneSectionTitle banner = new ComarPaneSectionTitle("CATEGORIAS > ELIMINAR");
            Label buttonBack = ComarIconLoader.getIcon(ComarIconLoader.BACK, 16);
            buttonBack.getStyleClass().add("comar-link");
            buttonBack.setOnMouseClicked(e -> view(paneInv));
            banner.getPaneTitle().setRight(buttonBack);
            setTop(banner);
        }

    }
}
