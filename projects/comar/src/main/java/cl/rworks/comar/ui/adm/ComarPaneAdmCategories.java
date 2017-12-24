/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import cl.rworks.comar.core.model.ComarContext;
import cl.rworks.comar.ui.ComarIconLoader;
import cl.rworks.comar.ui.ComarPaneSectionTitle;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import cl.rworks.comar.core.model.ComarCategory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

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

    public ComarPaneAdmCategories(ComarContext context) {
        this.context = context;

        paneInv = new PaneMain();
        paneAdd = new PaneAdd();
        paneEdit = new PaneEdit();

        show(paneInv);
    }

    private void show(Node pane) {
        setCenter(pane);
    }

    private class PaneMain extends BorderPane {

        public PaneMain() {
            setTop(new ComarPaneSectionTitle("CATEGORIAS"));

            TableView<ComarCategory> table = new TableView();
            TableColumn firstNameCol = new TableColumn("Nombre");
            table.getColumns().addAll(firstNameCol);

            BorderPane paneTable = new BorderPane();
            paneTable.setCenter(table);

            FlowPane paneSearch = new FlowPane(new Label("Buscar"), new TextField());
            paneSearch.getStyleClass().addAll("comar-flowpane");
            paneTable.setBottom(paneSearch);

            setCenter(paneTable);

            Button buttonAdd = new Button("Agregar");
            buttonAdd.setOnMouseClicked(e -> addCategory());

            Button buttonEdit = new Button("Editar");
            buttonEdit.setOnMouseClicked(e -> editCategory());

            Button buttonDelete = new Button("Eliminar");
            buttonDelete.setOnMouseClicked(e -> deleteCategory());

            FlowPane paneButtons = new FlowPane(buttonAdd, buttonEdit, buttonDelete);
            paneButtons.setAlignment(Pos.CENTER);
            paneButtons.getStyleClass().addAll("comar-flowpane");
            setBottom(paneButtons);
        }

    }

    private void addCategory() {
        show(paneAdd);
    }

    private void editCategory() {
        show(paneEdit);
    }

    private void deleteCategory() {
    }

    private void goBack() {
        show(paneInv);
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

        private CategoryForm categoryForm;

        public PaneAdd() {
            setTop(new PaneTop("CATEGORIAS > AGREGAR"));
            this.categoryForm = new CategoryForm();
            setCenter(categoryForm);
        }

    }

    private class PaneEdit extends BorderPane {

        private CategoryForm categoryForm;

        public PaneEdit() {
            setTop(new PaneTop("CATEGORIAS > EDITAR"));
            this.categoryForm = new CategoryForm();
            setCenter(categoryForm);
        }

    }

    private class CategoryForm extends GridPane {

        private Text textName;

        public CategoryForm() {
            add(new Label("Nombre"), 0, 0);

            textName = new Text();
            add(textName, 1, 0);
        }

        public Text getTextName() {
            return textName;
        }
    }
}
