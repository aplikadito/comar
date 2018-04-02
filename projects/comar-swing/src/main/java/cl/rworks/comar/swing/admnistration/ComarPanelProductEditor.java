/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.swing.ComarSystem;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelProductEditor extends WebPanel {

    private WebPanel panelContent;
    private WebPanel panelForm;
    //
    private WebTextField textCode;
    private WebTextField textName;
    private WebComboBox comboMetric;
    private WebTextField textBuyPrice;
    private WebTextField textSellPrice;
    private WebTextField textStock;
    //
    private WebPanel panelButtons;
    private int fontSize = ComarSystem.getInstance().getProperties().getNormalFontSize();
    private DecimalFormat df = new DecimalFormat("#0.00");
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

    private WebPanel buildContent() {
        panelContent = new WebPanel(new BorderLayout());
        panelContent.setBorder(new EmptyBorder(10, 10, 10, 10));

        WebPanel panelFormContainer = new WebPanel();
        panelFormContainer.setLayout(new BoxLayout(panelFormContainer, BoxLayout.PAGE_AXIS));
        panelFormContainer.add(buildForm());
        panelContent.add(panelFormContainer, BorderLayout.CENTER);

        return panelContent;
    }

    private WebPanel buildForm() {
        panelForm = new WebPanel(new FormLayout(false, true, 10, 10));
        panelForm.setMinimumSize(new Dimension(100, 100));
        panelForm.setPreferredSize(new Dimension(300, 250));
        panelForm.setMaximumSize(new Dimension(300, 300));
        panelForm.setAlignmentX(0.0f);

        textCode = new WebTextField(20);
        textCode.setFocusable(true);
        textCode.setFontSize(fontSize);

        WebLabel label = new WebLabel("Codigo");
        label.setFontSize(fontSize);
        panelForm.add(label);
        panelForm.add(textCode);

        textName = new WebTextField();
        textName.setFocusable(true);
        textName.setFontSize(fontSize);

        label = new WebLabel("Nombre");
        label.setFontSize(fontSize);
        panelForm.add(label);
        panelForm.add(textName);

        comboMetric = new WebComboBox(ComarMetric.values());
        comboMetric.setFontSize(fontSize);

        label = new WebLabel("Medida");
        label.setFontSize(fontSize);
        panelForm.add(label);
        panelForm.add(comboMetric);

        textBuyPrice = new WebTextField();
        textBuyPrice.setFocusable(true);
        textBuyPrice.setFontSize(fontSize);

        label = new WebLabel("Precio Compra");
        label.setFontSize(fontSize);
        panelForm.add(label);
        panelForm.add(textBuyPrice);

        textSellPrice = new WebTextField();
        textSellPrice.setFocusable(true);
        textSellPrice.setFontSize(fontSize);

        label = new WebLabel("Precio Venta");
        label.setFontSize(fontSize);
        panelForm.add(label);
        panelForm.add(textSellPrice);

        textStock = new WebTextField();
        textStock.setFocusable(true);
        textStock.setFontSize(fontSize);

        label = new WebLabel("Stock");
        label.setFontSize(fontSize);
        panelForm.add(label);
        panelForm.add(textStock);

        return panelForm;
    }

    public void updateForm(ComarPanelProductRow row) {
        this.oldRow = row;
        
        this.textCode.setText(row.getCode() != null ? row.getCode() : "");
        this.textName.setText(row.getName() != null ? row.getName() : "");
        this.comboMetric.setSelectedItem(row.getMetric() != null ? row.getMetric() : ComarMetric.UNIDAD);
        this.textBuyPrice.setText(df.format(row.getBuyPrice()));
        this.textSellPrice.setText(df.format(row.getSellPrice()));
        this.textStock.setText(df.format(row.getStock()));
    }

    private WebPanel buildPanelButtons() {
        panelButtons = new WebPanel(new FlowLayout());
        panelButtons.setMinimumSize(new Dimension(300, 30));
        panelButtons.setPreferredSize(new Dimension(300, 40));
        panelButtons.setMaximumSize(new Dimension(300, 50));
        panelButtons.setAlignmentX(0.0f);
        return panelButtons;
    }

    public WebPanel getPanelButtons() {
        return panelButtons;
    }

    public WebTextField getTextCode() {
        return textCode;
    }

    public WebTextField getTextName() {
        return textName;
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
