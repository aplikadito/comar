/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

/**
 *
 * @author aplik
 */
public class TableTestPane extends BorderPane {

    public TableTestPane() {
        initValues();
    }

    public void initValues() {
        List<Tuple> tuples = new ArrayList<>();
        tuples.add(new Tuple(LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 0), LocalDateTime.of(2017, Month.JANUARY, 1, 1, 0, 0), "MCM"));
        tuples.add(new Tuple(LocalDateTime.of(2017, Month.JANUARY, 2, 0, 0, 0), LocalDateTime.of(2017, Month.JANUARY, 2, 1, 0, 0), "MCM"));

        TableColumn<Tuple, String> colFrom = new TableColumn<>();
        colFrom.setText("Desde");
        colFrom.setCellValueFactory(from -> {
            SimpleStringProperty property = new SimpleStringProperty();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = from.getValue().getFrom().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            property.setValue(strDate);
            return property;
        });

        TableColumn<Tuple, LocalDateTime> colTo = new TableColumn<>();
        colTo.setText("Hasta");
        colTo.setCellValueFactory(new PropertyValueFactory<>("to"));
        
        TableColumn<Tuple, String> colType = new TableColumn<>();
        colType.setText("Tipo");
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colType.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<Tuple, Double> colCost = new TableColumn<>();
        colCost.setText("Costo");
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colCost.setCellFactory(e -> new EditingCell());
        colCost.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setCost(e.getNewValue()));

        TableView<Tuple> view = new TableView<>();
        view.setEditable(true);
        view.getColumns().addAll(colFrom, colTo, colType, colCost);
        view.setItems(FXCollections.observableArrayList(tuples));
        view.setColumnResizePolicy(e -> true);

//        setCenter(new ScrollPane(view));
        setCenter(view);
    }

}
