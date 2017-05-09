/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import java.text.DecimalFormat;
import java.text.ParseException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author rgonzalez
 */
public class ComarPaneItemUpdate extends VBox {

    private TextField textCode;
    private Button buttonSearch;
    private TextField textName;
    private TextField textBuyPrice;
    private TextField textSellPrice;
    private TextField textStock;
    private CheckBox checkRate;
    private TextField textRate;
    private Button buttonClean;
    private Button buttonOk;

    public ComarPaneItemUpdate() {
        textCode = new TextField("");
        textCode.setAlignment(Pos.CENTER_RIGHT);

        textName = new TextField("");
        textName.setMinWidth(300);

        textBuyPrice = new TextField("0");
        textBuyPrice.setAlignment(Pos.CENTER_RIGHT);
        textBuyPrice.setOnKeyReleased(new BuyPriceEventHandler());

        checkRate = new CheckBox();
        checkRate.setTooltip(new Tooltip("Activar porcentaje de ganancia"));
        checkRate.setOnAction(new CheckRateEventHandler());

        textRate = new TextField("30");
        textRate.setAlignment(Pos.CENTER_RIGHT);
        textRate.setMaxWidth(80);

        textSellPrice = new TextField("0");
        textSellPrice.setAlignment(Pos.CENTER_RIGHT);

        textStock = new TextField("0");
        textStock.setAlignment(Pos.CENTER_RIGHT);

        getStyleClass().add("comar-form");

        buttonSearch = new Button("Buscar");
        buttonSearch.setVisible(false);
        
        FlowPane paneCode = new FlowPane(textCode, buttonSearch);
        paneCode.setAlignment(Pos.CENTER_LEFT);
        paneCode.setHgap(5);
        
        HBox rowCode = createRow(createLabel("Codigo", "Codigo de barras (EAN13)"), paneCode);
        rowCode.setStyle("-fx-padding: 0 0 20 0");
        
        getChildren().add(rowCode);
        getChildren().add(createRow(createLabel("Nombre"), textName));
        getChildren().add(createRow(createLabel("Precio Compra"), textBuyPrice));
        getChildren().add(createRow(createLabel("Tasa Venta", "Porcentaje de ganancia respecto al Precio de Compra"), createRatePane()));
        getChildren().add(createRow(createLabel("Precio Venta"), textSellPrice));
        getChildren().add(createRow(createLabel("Cantidad"), textStock));

        buttonClean = new Button("Limpiar");
        buttonOk = new Button("");
        
        FlowPane paneButtons = new FlowPane(buttonClean, buttonOk);
        paneButtons.setAlignment(Pos.CENTER);
        paneButtons.setMaxWidth(500);
        paneButtons.setStyle("-fx-padding: 10 0 0 0");
        paneButtons.setHgap(5);
        
        getChildren().add(paneButtons);
        
        

        checkRate.setSelected(true);
        setRateActived(true);
    }

    private HBox createRow(Node key, Node value) {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("comar-form-row");
        hbox.getChildren().add(key);
        hbox.getChildren().add(value);
        return hbox;
    }

    private Label createLabel(String text) {
        return createLabel(text, text);
    }

    private Label createLabel(String text, String tooltip) {
        Label label = new Label(text);
        label.setTooltip(new Tooltip(tooltip));
        label.setMinWidth(120);
        return label;
    }

    private void setRateActived(boolean actived) {
//        this.checkRate.setDisable(!actived);
        this.textRate.setDisable(!actived);
        this.textSellPrice.setDisable(actived);
    }

    private FlowPane createRatePane() {
        FlowPane paneRate = new FlowPane(checkRate, textRate, new Label("%"));
        paneRate.getStyleClass().add("comar-flowpane");
        paneRate.setMaxWidth(300);
        return paneRate;
    }

    public TextField getTextName() {
        return textName;
    }

    public TextField getTextBuyPrice() {
        return textBuyPrice;
    }

    public TextField getTextSellPrice() {
        return textSellPrice;
    }

    public TextField getTextStock() {
        return textStock;
    }

    public CheckBox getCheckRate() {
        return checkRate;
    }

    public TextField getTextRate() {
        return textRate;
    }

    public TextField getTextCode() {
        return textCode;
    }

    public Button getButtonSearch() {
        return buttonSearch;
    }
    
    public Button getButtonClean() {
        return buttonClean;
    }

    public Button getButtonOk() {
        return buttonOk;
    }
    
    private class BuyPriceEventHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            if (checkRate.isSelected()) {
                double rate = -1;
                try {
                    rate = DecimalFormat.getInstance().parse(textRate.getText()).doubleValue();
                    rate = 1 + (rate / 100.0);
                } catch (ParseException ex) {
                }

                double price = -1;
                try {
                    price = DecimalFormat.getInstance().parse(textBuyPrice.getText()).doubleValue();
                } catch (ParseException ex) {
                }

                if (rate >= 0 && price >= 0) {
                    textSellPrice.setText(DecimalFormat.getInstance().format(rate * price));
                } else {
                    textSellPrice.setText("...");
                }

            }
        }
    }

    private class CheckRateEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            setRateActived(checkRate.isSelected());
        }
    }
}
