/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.admnistration;

import cl.rworks.comar.swing.util.ComarPanelSubtitle;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelProductSearch extends WebPanel {

    private WebPanel panelContent;
    private WebTable table;
    private ProductTableModel tableModel;
    private WebTextField textSearch;

    public ComarPanelProductSearch() {
        initValues();
    }

    private void initValues() {
        setLayout(new BorderLayout());

        add(new ComarPanelSubtitle("Buscar Producto"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    private WebPanel buildContent() {
        panelContent = new WebPanel(new BorderLayout());
        panelContent.setBorder(new EmptyBorder(10, 10, 10, 10));

        WebPanel panel = new WebPanel();
        panelContent.add(panel, BorderLayout.CENTER);

        panel.setLayout(new BorderLayout());
        panel.add(buildTableOptions(), BorderLayout.NORTH);
        panel.add(buildTable(), BorderLayout.CENTER);
        panel.add(buildTableStatus(), BorderLayout.SOUTH);

        return panelContent;
    }

    private WebPanel buildTableOptions() {
        WebPanel panel = new WebPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new WebLabel("Buscar"));

        textSearch = new WebTextField();
        panel.add(textSearch);

        return panel;
    }

    private WebPanel buildTable() {
        WebPanel panel = new WebPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
//        panel.setLayout(new BorderLayout());

        tableModel = new ProductTableModel();
        table = new WebTable(tableModel);
        panel.add(new WebScrollPane(table));

        return panel;
    }

    private WebPanel buildTableStatus() {
        WebPanel panelFormButtons = new WebPanel(new FlowLayout(FlowLayout.LEFT));
//        panelFormButtons.setMinimumSize(new Dimension(300, 30));
//        panelFormButtons.setPreferredSize(new Dimension(300, 30));
//        panelFormButtons.setMaximumSize(new Dimension(300, 30));
//        panelFormButtons.setAlignmentX(0.0f);

//        WebButton buttonOk = new WebButton(new AddAction());
//        buttonOk.setFocusable(true);
//        panelFormButtons.add(buttonOk);
//        
//        WebButton buttonClear = new WebButton(new ClearAction());
//        buttonClear.setFocusable(true);
//        panelFormButtons.add(buttonClear);
        return panelFormButtons;
    }

    private class ProductTableModel extends AbstractTableModel {

        private String[] columnNames = new String[]{"Codigo", "Nombre"};

        @Override
        public int getRowCount() {
            return 0;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return "";
        }

    }

    private class ClearAction extends AbstractAction {

        public ClearAction() {
            putValue(NAME, "Limpiar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clear();
        }

    }

    private void clear() {
        this.textSearch.clear();
    }

}
