/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import cl.rworks.comar.core.ComarContext;
import cl.rworks.comar.ui.ComarGuiUtils;
import cl.rworks.comar.ui.ComarIconLoader;
import cl.rworks.comar.ui.ComarPaneSectionTitle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneAdmProducts extends BorderPane {

    private PaneMain paneInv;
    private PaneAdd paneAdd;
    private PaneEdit paneEdit;
    private PaneDelete paneDelete;

    public ComarPaneAdmProducts(ComarContext context) {
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

        private ObservableList<ComarProductUi> data;

        public PaneMain() {
            setTop(new ComarPaneSectionTitle("PRODUCTOS"));

            TableView table = new TableView();
            TableColumn<ComarProductUi, String> colCode = new TableColumn("Codigo");
            TableColumn<ComarProductUi, String> colName = new TableColumn("Nombre");

            colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));

            data = FXCollections.observableArrayList(
                    new ComarProductUi("0001", "Smith"),
                    new ComarProductUi("0002", "Johnson"),
                    new ComarProductUi("0003", "Williams"),
                    new ComarProductUi("0004", "Jones"),
                    new ComarProductUi("0005", "Brown")
            );
            table.setItems(data);
            table.getColumns().addAll(colCode, colName);

            BorderPane paneTable = new BorderPane();
            paneTable.setCenter(table);

            FlowPane paneSearch = new FlowPane(new Label("Buscar"), new TextField());
            paneSearch.getStyleClass().addAll("comar-flowpane");
            paneTable.setBottom(paneSearch);

            setCenter(paneTable);

            Button buttonAdd = new Button("Agregar");
            buttonAdd.setOnMouseClicked(e -> addProduct());

            Button buttonEdit = new Button("Editar");
            buttonEdit.setOnMouseClicked(e -> view(paneEdit));

            Button buttonDelete = new Button("Eliminar");
            buttonDelete.setOnMouseClicked(e -> view(paneDelete));

            FlowPane paneButtons = new FlowPane(buttonAdd, buttonEdit, buttonDelete);
            paneButtons.setAlignment(Pos.CENTER);
            paneButtons.getStyleClass().addAll("comar-flowpane");
            setBottom(paneButtons);
        }
        
        public void addProduct(){
//            paneAdd.ad
//            view(paneAdd);
        }

    }

    private class PaneAdd extends BorderPane {

        private HBox paneCode;
        private HBox paneName;
        private TextField textCode;
        private TextField textName;

        public PaneAdd() {
            ComarPaneSectionTitle banner = new ComarPaneSectionTitle("PRODUCTOS > AGREGAR");
            Label buttonBack = ComarIconLoader.getIcon(ComarIconLoader.BACK, 16);
            buttonBack.getStyleClass().add("comar-link");
            buttonBack.setOnMouseClicked(e -> view(paneInv));
            banner.getPaneTitle().setRight(buttonBack);
            setTop(banner);

            textCode = new TextField();
            textName = new TextField();

            paneCode = ComarGuiUtils.createRow("Code", textCode);
            paneName = ComarGuiUtils.createRow("Nombre", textName);

            setCenter(new VBox(paneCode, paneName));

            Button buttonOk = new Button("Agregar");
            buttonOk.setOnAction(e -> okAction());
            setBottom(new VBox(buttonOk));
        }

        public void okAction() {
            String code = textCode.getText();
            String name = textName.getText();
         
            view(paneInv);
        }
        

    }

    private class PaneEdit extends BorderPane {

        public PaneEdit() {
            ComarPaneSectionTitle banner = new ComarPaneSectionTitle("PRODUCTOS > EDITAR");
            Label buttonBack = ComarIconLoader.getIcon(ComarIconLoader.BACK, 16);
            buttonBack.getStyleClass().add("comar-link");
            buttonBack.setOnMouseClicked(e -> view(paneInv));
            banner.getPaneTitle().setRight(buttonBack);
            setTop(banner);
        }

    }

    private class PaneDelete extends BorderPane {

        public PaneDelete() {
            ComarPaneSectionTitle banner = new ComarPaneSectionTitle("PRODUCTOS > ELIMINAR");
            Label buttonBack = ComarIconLoader.getIcon(ComarIconLoader.BACK, 16);
            buttonBack.getStyleClass().add("comar-link");
            buttonBack.setOnMouseClicked(e -> view(paneInv));
            banner.getPaneTitle().setRight(buttonBack);
            setTop(banner);
        }

    }
}
