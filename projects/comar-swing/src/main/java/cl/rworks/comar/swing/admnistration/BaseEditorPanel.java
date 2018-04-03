/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.swing.ComarSystem;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

/**
 *
 * @author rgonzalez
 */
public class BaseEditorPanel extends WebPanel {

//    private WebPanel panelContent;
    private WebTable table;
    private WebTextField textSearch;
    private WebButton buttonSearch;
    private WebButton buttonClear;
    private WebButton buttonAdd;
    private WebButton buttonEdit;
    private WebButton buttonDelete;
    //
    private Dimension dimensionButton = new Dimension(100, 28);
    private int normalFontSize = ComarSystem.getInstance().getProperties().getNormalFontSize();

    public BaseEditorPanel() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());

//        add(new ComarPanelSubtitle("Productos"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    private WebPanel buildContent() {
        WebPanel panel = new WebPanel();
        panel.setLayout(new BorderLayout());
        
        panel.add(buildTableOptions(), BorderLayout.NORTH);
        panel.add(buildTable(), BorderLayout.CENTER);
        panel.add(buildTableButtons(), BorderLayout.EAST);

        return panel;
    }

    private WebPanel buildTableOptions() {
        WebPanel panelSearch = new WebPanel(new FlowLayout(FlowLayout.LEFT));
        WebLabel labelSearch = new WebLabel("Buscar");
        labelSearch.setFontSize(normalFontSize);
        panelSearch.add(labelSearch);

        textSearch = new WebTextField(20);
        textSearch.setFontSize(normalFontSize);
        panelSearch.add(textSearch);

        buttonSearch = new WebButton("Buscar");
        buttonSearch.setFontSize(normalFontSize);
        buttonSearch.setPreferredSize(dimensionButton);
        panelSearch.add(buttonSearch);

        buttonClear = new WebButton("Limpiar");
        buttonClear.setFontSize(normalFontSize);
        buttonClear.setPreferredSize(dimensionButton);
        panelSearch.add(buttonClear);

        WebPanel panel = new WebPanel(new BorderLayout());
        panel.add(panelSearch, BorderLayout.WEST);
//        panel.add(panelButtons, BorderLayout.EAST);
        return panel;
    }

    private WebPanel buildTable() {
        WebPanel panel = new WebPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        table = new WebTable();
        panel.add(new WebScrollPane(table));
        
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont((float) normalFontSize));
        table.setFontSize(normalFontSize);
        table.setRowHeight(normalFontSize + 4);

        return panel;
    }

    private WebPanel buildTableButtons() {
        WebPanel panel = new WebPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        buttonAdd = new WebButton("Agregar");
        buttonAdd.setFontSize(normalFontSize);
        buttonAdd.setFocusable(true);
        buttonAdd.setMinimumSize(dimensionButton);
        buttonAdd.setPreferredSize(dimensionButton);
        buttonAdd.setMaximumSize(dimensionButton);
        panel.add(buttonAdd);

        buttonEdit = new WebButton("Editar");
        buttonEdit.setFontSize(normalFontSize);
        buttonEdit.setFocusable(true);
        buttonEdit.setMinimumSize(dimensionButton);
        buttonEdit.setPreferredSize(dimensionButton);
        buttonEdit.setMaximumSize(dimensionButton);
        panel.add(buttonEdit);

        buttonDelete = new WebButton("Eliminar");
        buttonDelete.setFontSize(normalFontSize);
        buttonDelete.setFocusable(true);
        buttonDelete.setMinimumSize(dimensionButton);
        buttonDelete.setPreferredSize(dimensionButton);
        buttonDelete.setMaximumSize(dimensionButton);
        panel.add(buttonDelete);

        return panel;
    }

    public WebTable getTable() {
        return table;
    }

    public WebTextField getTextSearch() {
        return textSearch;
    }

    public WebButton getButtonSearch() {
        return buttonSearch;
    }

    public WebButton getButtonClear() {
        return buttonClear;
    }

    public WebButton getButtonAdd() {
        return buttonAdd;
    }

    public WebButton getButtonEdit() {
        return buttonEdit;
    }

    public WebButton getButtonDelete() {
        return buttonDelete;
    }
    

}
