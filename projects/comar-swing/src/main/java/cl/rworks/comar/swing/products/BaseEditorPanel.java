/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.products;

import cl.rworks.comar.swing.util.ComarButton;
import cl.rworks.comar.swing.util.ComarLabel;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarTable;
import cl.rworks.comar.swing.util.ComarTextField;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
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
public class BaseEditorPanel extends ComarPanel {

    private WebTable table;
    private WebTextField textSearch;
    private ComarButton buttonSearch;
    private ComarButton buttonClear;
    private ComarButton buttonAdd;
    private ComarButton buttonEdit;
    private ComarButton buttonDelete;

    public BaseEditorPanel() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());

//        add(new ComarPanelSubtitle("Productos"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    private ComarPanel buildContent() {
        ComarPanel panel = new ComarPanel();
        panel.setLayout(new BorderLayout());

        panel.add(buildTableOptions(), BorderLayout.NORTH);
        panel.add(buildTable(), BorderLayout.CENTER);
//        panel.add(buildTableButtons(), BorderLayout.EAST);

        return panel;
    }

    private ComarPanel buildTableOptions() {
        ComarPanel panelWest = new ComarPanel(new FlowLayout(FlowLayout.LEFT));
        ComarLabel labelSearch = new ComarLabel("Buscar");
        panelWest.add(labelSearch);

//        Dimension prefDim = new Dimension(190, 35);
        Dimension prefDim = ComarUtils.BUTTON_PREF_DIM;

        textSearch = new ComarTextField(20);
        panelWest.add(textSearch);

        buttonSearch = new ComarButton("Buscar");
        buttonSearch.setPreferredSize(prefDim);
        panelWest.add(buttonSearch);

        buttonClear = new ComarButton("Limpiar");
        buttonClear.setPreferredSize(prefDim);
        panelWest.add(buttonClear);

        ComarPanel panelEast = new ComarPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonAdd = new ComarButton("Agregar");
        buttonAdd.setFocusable(true);
        panelEast.add(buttonAdd);

        buttonEdit = new ComarButton("Editar");
        buttonEdit.setFocusable(true);
        panelEast.add(buttonEdit);

        buttonDelete = new ComarButton("Eliminar");
        buttonDelete.setFocusable(true);
        panelEast.add(buttonDelete);

        ComarPanel panel = new ComarPanel(new BorderLayout());
        panel.add(panelWest, BorderLayout.WEST);
        panel.add(panelEast, BorderLayout.EAST);

        return panel;
    }

    private ComarPanel buildTable() {
        ComarPanel panel = new ComarPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        table = new ComarTable();
        panel.add(new WebScrollPane(table));

        return panel;
    }

//    private WebPanel buildTableButtons() {
//        WebPanel panel = new WebPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
//
//        buttonAdd = new WebButton("Agregar");
//        buttonAdd.setFontSize(normalFontSize);
//        buttonAdd.setFocusable(true);
//        buttonAdd.setMinimumSize(dimensionButton);
//        buttonAdd.setPreferredSize(dimensionButton);
//        buttonAdd.setMaximumSize(dimensionButton);
//        panel.add(buttonAdd);
//
//        buttonEdit = new WebButton("Editar");
//        buttonEdit.setFontSize(normalFontSize);
//        buttonEdit.setFocusable(true);
//        buttonEdit.setMinimumSize(dimensionButton);
//        buttonEdit.setPreferredSize(dimensionButton);
//        buttonEdit.setMaximumSize(dimensionButton);
//        panel.add(buttonEdit);
//
//        buttonDelete = new WebButton("Eliminar");
//        buttonDelete.setFontSize(normalFontSize);
//        buttonDelete.setFocusable(true);
//        buttonDelete.setMinimumSize(dimensionButton);
//        buttonDelete.setPreferredSize(dimensionButton);
//        buttonDelete.setMaximumSize(dimensionButton);
//        panel.add(buttonDelete);
//
//        return panel;
//    }
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
