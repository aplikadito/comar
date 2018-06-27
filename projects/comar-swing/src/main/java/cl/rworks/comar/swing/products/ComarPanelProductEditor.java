/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.products;

import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarLabel;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarTextField;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelProductEditor extends ComarPanel {

    private ComarPanel panelContent;
    private ComarPanel panelForm;
    //
    private WebTextField textCode;
    private WebTextField textDescription;
    private WebComboBox comboMetric;
    private WebTextField textBuyPrice;
    private WebTextField textTax;
    private WebTextField textSellPrice;
    private WebTextField textStock;
    //
    private ComarPanel panelButtons;
    private int fontSize = ComarSystem.getInstance().getProperties().getFontSize();
    //
    private ComarProduct oldRow;

    public ComarPanelProductEditor() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());

        add(buildContent(), BorderLayout.CENTER);
        add(buildPanelButtons(), BorderLayout.SOUTH);
    }

    private ComarPanel buildContent() {
        panelContent = new ComarPanel(new BorderLayout());
        panelContent.setBorder(new EmptyBorder(10, 10, 10, 10));

        ComarPanel panelFormContainer = new ComarPanel();
        panelFormContainer.setLayout(new BoxLayout(panelFormContainer, BoxLayout.PAGE_AXIS));
        panelFormContainer.add(buildForm());
        panelContent.add(panelFormContainer, BorderLayout.CENTER);

        return panelContent;
    }

    private ComarPanel buildForm() {
        panelForm = new ComarPanel(new FormLayout(false, true, 10, 10));
        panelForm.setAlignmentX(0.0f);

        textCode = new WebTextField(20);
        textCode.setFocusable(true);
        textCode.setFontSize(fontSize);

        panelForm.add(new ComarLabel("Codigo"));
        panelForm.add(textCode);

        textDescription = new ComarTextField(30);
        panelForm.add(new ComarLabel("Descripcion"));
        panelForm.add(textDescription);

        comboMetric = new WebComboBox(ComarMetric.values());
        comboMetric.setFontSize(fontSize);
        panelForm.add(new ComarLabel("Medida"));
        panelForm.add(comboMetric);

        textBuyPrice = new ComarTextField();
        panelForm.add(new ComarLabel("Precio Compra"));
        panelForm.add(textBuyPrice);

        textTax = new ComarTextField();
        panelForm.add(new ComarLabel("Impuesto"));
        panelForm.add(textTax);

        textSellPrice = new ComarTextField();
        panelForm.add(new ComarLabel("Precio Venta"));
        panelForm.add(textSellPrice);

        textStock = new ComarTextField();
        panelForm.add(new ComarLabel("Stock"));
        panelForm.add(textStock);

        return panelForm;
    }

//    private ComarPanel createNumberPanel(JTextField panelInt, JTextField panelDecs) {
//        ComarPanel panel = new ComarPanel();
//        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
//        panel.add(panelInt);
//        panel.add(new ComarLabel(","));
//        panel.add(panelDecs);
//        return panel;
//    }
    public void updateForm(ComarProduct row) {
        this.oldRow = row;

        this.textCode.setText(row.getCode() != null ? row.getCode() : "");
        this.textDescription.setText(row.getDescription() != null ? row.getDescription() : "");
        this.comboMetric.setSelectedItem(row.getMetric() != null ? row.getMetric() : ComarMetric.UNIDADES);
        this.textBuyPrice.setText(ComarUtils.format(row.getBuyPrice()));
        this.textSellPrice.setText(ComarUtils.format(row.getSellPrice()));
        this.textStock.setText(ComarUtils.format(row.getStock()));
        this.textTax.setText(ComarUtils.format(row.getTax()));
    }

    private ComarPanel buildPanelButtons() {
        panelButtons = new ComarPanel(new FlowLayout());
        panelButtons.setMinimumSize(new Dimension(300, 60));
        panelButtons.setPreferredSize(new Dimension(300, 60));
        panelButtons.setMaximumSize(new Dimension(300, 60));
        panelButtons.setAlignmentX(0.0f);
        return panelButtons;
    }

    public ComarPanel getPanelButtons() {
        return panelButtons;
    }

    public WebTextField getTextCode() {
        return textCode;
    }

    public WebTextField getTextDescription() {
        return textDescription;
    }

    public WebComboBox getComboMetric() {
        return comboMetric;
    }

    public WebTextField getTextBuyPrice() {
        return textBuyPrice;
    }

    public WebTextField getTextSellPrice() {
        return textSellPrice;
    }

    public WebTextField getTextStock() {
        return textStock;
    }

    public WebTextField getTextTax() {
        return textTax;
    }

    public ComarProduct getOldRow() {
        return oldRow;
    }

}
