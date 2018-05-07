/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.products;

import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarLabel;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
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
    private WebTextField textSellPrice;
    private WebTextField textStock;
    //
    private ComarPanel panelButtons;
    private int fontSize = ComarSystem.getInstance().getProperties().getFontSize();
    //
    private ComarPanelProductRow oldRow;

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

        ComarLabel label = new ComarLabel("Codigo");
        panelForm.add(label);
        panelForm.add(textCode);

        textDescription = new WebTextField(30);
        textDescription.setFocusable(true);
        textDescription.setFontSize(fontSize);

        label = new ComarLabel("Descripcion");
        panelForm.add(label);
        panelForm.add(textDescription);

        comboMetric = new WebComboBox(ComarMetric.values());
        comboMetric.setFontSize(fontSize);

        label = new ComarLabel("Medida");
        panelForm.add(label);
        panelForm.add(comboMetric);

        textBuyPrice = new WebTextField();
        textBuyPrice.setFocusable(true);
        textBuyPrice.setFontSize(fontSize);

        label = new ComarLabel("Precio Compra");
        panelForm.add(label);
        panelForm.add(textBuyPrice);

        textSellPrice = new WebTextField();
        textSellPrice.setFocusable(true);
        textSellPrice.setFontSize(fontSize);

        label = new ComarLabel("Precio Venta");
        panelForm.add(label);
        panelForm.add(textSellPrice);

        textStock = new WebTextField();
        textStock.setFocusable(true);
        textStock.setFontSize(fontSize);

        label = new ComarLabel("Stock");
        panelForm.add(label);
        panelForm.add(textStock);

        return panelForm;
    }

    public void updateForm(ComarPanelProductRow row) {
        this.oldRow = row;
        
        this.textCode.setText(row.getCode() != null ? row.getCode() : "");
        this.textDescription.setText(row.getName() != null ? row.getName() : "");
        this.comboMetric.setSelectedItem(row.getMetric() != null ? row.getMetric() : ComarMetric.UNIDAD);
        this.textBuyPrice.setText(ComarUtils.formatDbl(row.getBuyPrice()));
        this.textSellPrice.setText(ComarUtils.formatDbl(row.getSellPrice()));
        this.textStock.setText(ComarUtils.formatDbl(row.getStock()));
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

    public WebTextField getTextName() {
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

    public ComarPanelProductRow getOldRow() {
        return oldRow;
    }
    
}
