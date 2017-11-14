/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import cl.rworks.comar.core.ComarContext;
import cl.rworks.comar.core.model.ComarUnit;
import cl.rworks.comar.ui.ComarGuiUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneAdmInventoryCreate extends BorderPane {

    private final ComarContext context;
    //
    private TextField textCode;
    private TextField textName;
    private ComboBox<ComarUnit> comboUnit;
    private VBox paneForm;
    private FlowPane paneButtons;

    public ComarPaneAdmInventoryCreate(ComarContext context) {
        this.context = context;

        createForm();
        createButtons();
    }

    private void createForm() {
        textCode = new TextField("");
        textCode.setAlignment(Pos.CENTER_RIGHT);

        textName = new TextField("");
        textName.setMinWidth(300);

        List<ComarUnit> units = new ArrayList<>(Arrays.asList(ComarUnit.values()));
        comboUnit = new ComboBox<ComarUnit>();
        comboUnit.getItems().addAll(units);
        comboUnit.getSelectionModel().selectFirst();
        comboUnit.setConverter(new UnitConverter());

        paneForm = new VBox();
        paneForm.getStyleClass().add("comar-form");
        this.setCenter(paneForm);

        ObservableList<Node> children = paneForm.getChildren();
        children.add(ComarGuiUtils.createRow("Codigo", textCode));
        children.add(ComarGuiUtils.createRow("Nombre", textName));
        children.add(ComarGuiUtils.createRow("Unidad", comboUnit));

    }

    private void createButtons() {
        paneButtons = new FlowPane();
        this.setBottom(paneButtons);

        ObservableList<Node> children = paneButtons.getChildren();
        children.add(new Button("Crear"));
    }

    private class UnitConverter extends StringConverter<ComarUnit> {

        @Override
        public String toString(ComarUnit unit) {
            return unit.getName() + " " + unit.getSymbol();
        }

        @Override
        public ComarUnit fromString(String string) {
            return null;
        }
    }

}
