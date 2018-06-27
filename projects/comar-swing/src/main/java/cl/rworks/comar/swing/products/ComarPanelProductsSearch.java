/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.products;

import cl.rworks.comar.core.ComarNumberFormat;
import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarProductHistorialAction;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.util.ComarButton;
import cl.rworks.comar.swing.util.ComarCloseAction;
import cl.rworks.comar.swing.util.ComarIconLoader;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarTable;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.text.WebTextField;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import io.permazen.JTransaction;
import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.table.AbstractTableModel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import cl.rworks.comar.core.controller.ComarController;
import cl.rworks.comar.core.controller.ComarControllerException;
import cl.rworks.comar.core.model.impl.ComarProductImpl;
import cl.rworks.comar.swing.util.ComarPanelTitle;
import java.awt.Component;
import java.math.BigDecimal;
import java.text.ParseException;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author rgonzalez
 */
public class ComarPanelProductsSearch extends ComarPanel {

    private BaseEditorPanel panelEditor;
    private TableModel tableModel;
//    private DateTimeFormatter df = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy HH:mm:ss").toFormatter();

    public ComarPanelProductsSearch() {
        setLayout(new BorderLayout());
        add(buildContent(), BorderLayout.CENTER);
    }

    private ComarPanel buildContent() {
        ComarPanel panel = new ComarPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        panel.add(new ComarPanelTitle("Inventario"), BorderLayout.NORTH);

        this.panelEditor = new BaseEditorPanel();
        panel.add(panelEditor, BorderLayout.CENTER);

        this.tableModel = new TableModel();
        ComarTable table = panelEditor.getTable();
        table.setModel(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    new EditAction().actionPerformed(null);
                }
            }
        });

        table.setDefaultRenderer(BigDecimal.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String strValue = ComarUtils.format((BigDecimal) value);
                label.setText(strValue);
                return label;
            }

        });

        WebPopupMenu popup = new WebPopupMenu();
        popup.add(new EditAction());
        popup.add(new DeleteAction());
        table.setComponentPopupMenu(popup);

        this.panelEditor.getButtonSearch().setAction(new SearchAction());
        this.panelEditor.getButtonClear().setAction(new ClearAction());
        this.panelEditor.getButtonAdd().setAction(new AddAction());
        this.panelEditor.getButtonEdit().setAction(new EditAction());
        this.panelEditor.getButtonDelete().setAction(new DeleteAction());

        return panel;
    }

    private class TableModel extends AbstractTableModel {

        private final String[] columnNames = new String[]{
            "Codigo",
            "Descripcion",
            "Unidad",
            "Precio Compra",
            "Impuestos",
            "Precio Venta",
            "Stock"
        };

        private List<ComarProduct> products;

        public List<ComarProduct> getProducts() {
            return products;
        }

        public void setProducts(List<ComarProduct> products) {
            this.products = products;
        }

        @Override
        public int getRowCount() {
            return products != null ? products.size() : 0;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                    return String.class;
                case 2:
                    return String.class;
                case 3:
                    return BigDecimal.class;
                case 4:
                    return BigDecimal.class;
                case 5:
                    return BigDecimal.class;
                case 6:
                    return BigDecimal.class;
                default:
                    return String.class;
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ComarProduct p = products.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return p.getCode();
                case 1:
                    return p.getDescription();
                case 2:
                    return p.getMetric().getName();
                case 3:
                    return p.getBuyPrice();
                case 4:
                    return p.getTax();
                case 5:
                    return p.getSellPrice();
                case 6:
                    return p.getStock();
                default:
                    return "";
            }
        }

    }

    private class SearchAction extends AbstractAction {

        public SearchAction() {
            putValue(NAME, "Buscar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            search();
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

    private void search() {
        String strText = this.panelEditor.getTextSearch().getText();
        List<ComarProduct> products = loadProducts(strText);
        tableModel.setProducts(products);
        tableModel.fireTableDataChanged();
        ComarUtils.showInfo(products.size() + " productos encontrados");
    }

    private void clear() {
        this.panelEditor.getTextSearch().clear();
        this.tableModel.setProducts(null);
        this.tableModel.fireTableDataChanged();
    }

    private List<ComarProduct> loadProducts(String strText) {
        ComarService service = ComarSystem.getInstance().getService();
        List<ComarProduct> rows = new ArrayList<>();

        ComarController controller = service.getController();
        try {
            controller.createTransaction();
            List<ComarProduct> list = controller.loadProducts(strText);
            list.stream().forEach(p -> rows.add(toRow(p)));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            controller.rollback();
            controller.endTransaction();
        }
        return rows;
    }

    private class AddAction extends AbstractAction {

        public AddAction() {
            putValue(NAME, "Agregar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            final JDialog dialog = new JDialog(null, "Agregar Producto", ModalityType.APPLICATION_MODAL);
            dialog.getContentPane().setLayout(new BorderLayout());

            String text = panelEditor.getTextSearch().getText();
            ComarProductImpl product = new ComarProductImpl();
            product.setCode(text.trim());

            ComarPanelProductEditor panel = new ComarPanelProductEditor();
            panel.updateForm(product);
            dialog.getContentPane().add(panel, BorderLayout.CENTER);

            ComarButton buttonOk = new ComarButton(new AddOkAction(dialog, panel));
            buttonOk.setFocusable(true);
            buttonOk.setFontSize(ComarSystem.getInstance().getProperties().getFontSize());
            panel.getPanelButtons().add(buttonOk);

            ComarButton buttonClose = new ComarButton(new ComarCloseAction(dialog));
            buttonClose.setFocusable(true);
            buttonClose.setFontSize(ComarSystem.getInstance().getProperties().getFontSize());
            panel.getPanelButtons().add(buttonClose);

            JPanel contentPane = (JPanel) dialog.getContentPane();
            contentPane.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "ok");
            contentPane.getActionMap().put("ok", new AddOkAction(dialog, panel));
            contentPane.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "close");
            contentPane.getActionMap().put("close", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });

            dialog.setSize(600, 500);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

    }

    private class AddOkAction extends AbstractAction {

        private JDialog dialog;
        private ComarPanelProductEditor panel;

        private AddOkAction(JDialog dialog, ComarPanelProductEditor panel) {
            this.dialog = dialog;
            this.panel = panel;
            putValue(NAME, "Agregar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            Object[] values = new Object[]{
                validateString(panel.getTextCode(), "Codigo"),
                validateString(panel.getTextDescription(), "Descripcion"),
                (ComarMetric) panel.getComboMetric().getSelectedItem(),
                validateNumber(panel.getTextBuyPrice(), "Precio de Compra"),
                validateNumber(panel.getTextSellPrice(), "Precio de Venta"),
                validateNumber(panel.getTextStock(), "Stock"),
                validateNumber(panel.getTextTax(), "Impuestos"),};

            boolean ok = true;
            for (Object val : values) {
                if (val == null) {
                    ok = false;
                    break;
                }
            }

            if (ok) {
                ComarService service = ComarSystem.getInstance().getService();
                ComarController controller = service.getController();
                try {
                    controller.createTransaction();

                    ComarProductImpl temp = new ComarProductImpl();
                    temp.setCode((String) values[0]);
                    temp.setDescription((String) values[1]);
                    temp.setMetric((ComarMetric) values[2]);
                    temp.setBuyPrice((BigDecimal) values[3]);
                    temp.setSellPrice((BigDecimal) values[4]);
                    temp.setStock((BigDecimal) values[5]);

                    controller.addProduct(temp);
                    controller.commit();

                    search();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ComarUtils.showWarn(ComarPanelProductsSearch.this, ex.getMessage());
                } finally {
                    controller.endTransaction();
                }

                dialog.dispose();
            }
        }
    }

    private class EditAction extends AbstractAction {

        public EditAction() {
            putValue(NAME, "Editar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ComarProduct row = getSelectedProductRow();
            if (row == null) {
                return;
            }

            JDialog dialog = new JDialog(null, "Editar Producto", ModalityType.APPLICATION_MODAL);
            dialog.getContentPane().setLayout(new BorderLayout());

            ComarPanelProductEditor panel = new ComarPanelProductEditor();
            panel.updateForm(row);
            dialog.getContentPane().add(panel, BorderLayout.CENTER);

            ComarButton buttonOk = new ComarButton(new EditOkAction(dialog, panel));
            buttonOk.setFocusable(true);
            panel.getPanelButtons().add(buttonOk);

            ComarButton buttonClose = new ComarButton(new ComarCloseAction(dialog));
            buttonClose.setFocusable(true);
            panel.getPanelButtons().add(buttonClose);

            JPanel contentPane = (JPanel) dialog.getContentPane();
            contentPane.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "ok");
            contentPane.getActionMap().put("ok", new EditOkAction(dialog, panel));
            contentPane.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "close");
            contentPane.getActionMap().put("close", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });

            dialog.setSize(600, 500);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

    }

    private class EditOkAction extends AbstractAction {

        private JDialog dialog;
        private ComarPanelProductEditor panel;

        private EditOkAction(JDialog dialog, ComarPanelProductEditor panel) {
            this.dialog = dialog;
            this.panel = panel;
            putValue(NAME, "Editar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ComarProduct oldRow = panel.getOldRow();

            Object[] values = new Object[]{
                validateString(panel.getTextCode(), "Codigo"),
                validateString(panel.getTextDescription(), "Descripcion"),
                (ComarMetric) panel.getComboMetric().getSelectedItem(),
                validateNumber(panel.getTextBuyPrice(), "Precio de Compra"),
                validateNumber(panel.getTextTax(), "Precio de Compra"),
                validateNumber(panel.getTextSellPrice(), "Precio de Venta"),
                validateNumber(panel.getTextStock(), "Stock")
            };

            boolean ok = true;
            for (Object val : values) {
                if (val == null) {
                    ok = false;
                    break;
                }
            }

            if (ok) {
                ComarService service = ComarSystem.getInstance().getService();
                ComarController controller = service.getController();

                ComarProductImpl temp = new ComarProductImpl();
                temp.setCode((String) values[0]);
                temp.setDescription((String) values[1]);
                temp.setMetric((ComarMetric) values[2]);
                temp.setBuyPrice((BigDecimal) values[3]);
                temp.setTax((BigDecimal) values[4]);
                temp.setSellPrice((BigDecimal) values[5]);
                temp.setStock((BigDecimal) values[6]);

                try {
                    controller.createTransaction();

                    LocalDateTime now = LocalDateTime.now();
                    String action = ComarProductHistorialAction.EDIT.name();
                    controller.updateProduct(oldRow.getCode(), now, action, temp);
                    controller.commit();

                    search();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ComarUtils.showWarn(ComarPanelProductsSearch.this, ex.getMessage());
                } finally {
                    JTransaction.setCurrent(null);
                }

                dialog.dispose();
            }
        }

    }

    private class DeleteAction extends AbstractAction {

        public DeleteAction() {
            putValue(NAME, "Eliminar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] vrows = panelEditor.getTable().getSelectedRows();
            if (vrows.length == 0) {
                ComarUtils.showWarn(ComarPanelProductsSearch.this, "Seleccione al menos un producto");
                return;
            }

            int r = ComarUtils.showYesNo(null, "Desea eliminar los productos seleccionados?", "Eliminar");
            if (r == WebOptionPane.NO_OPTION) {
                return;
            }

            List<ComarProduct> list = new ArrayList<>();
            for (int i = 0; i < vrows.length; i++) {
                int vrow = vrows[i];
                int mrow = panelEditor.getTable().convertRowIndexToModel(vrow);
                ComarProduct row = tableModel.getProducts().get(mrow);
                list.add(row);
            }

            tableModel.getProducts().removeAll(list);
            tableModel.fireTableDataChanged();
        }

    }

    private ComarProduct toRow(ComarProduct p) {
        ComarProduct row = new ComarProductImpl();
        row.setId(p.getId());
        row.setCode(p.getCode());
        row.setDescription(p.getDescription());
        row.setMetric(p.getMetric());
        row.setBuyPrice(p.getBuyPrice());
        row.setTax(p.getTax());
        row.setSellPrice(p.getSellPrice());
        row.setStock(p.getStock());
        return row;
    }

    private ComarProduct getSelectedProductRow() {
        int vrow = panelEditor.getTable().getSelectedRow();
        if (vrow == -1) {
            ComarUtils.showWarn(ComarPanelProductsSearch.this, "Seleccione un producto");
            return null;
        }

        int mrow = panelEditor.getTable().convertRowIndexToModel(vrow);
        ComarProduct selected = tableModel.getProducts().get(mrow);

        ComarService service = ComarSystem.getInstance().getService();
        ComarController controller = service.getController();
        ComarProduct row = new ComarProductImpl();
        try {
            controller.createTransaction();
            ComarProduct dbproduct = controller.getProductByCode(selected.getCode());
            row.setId(dbproduct.getId());
            row.setCode(dbproduct.getCode());
            row.setDescription(dbproduct.getDescription());
            row.setMetric(dbproduct.getMetric());
            row.setBuyPrice(dbproduct.getBuyPrice());
            row.setTax(dbproduct.getTax());
            row.setSellPrice(dbproduct.getSellPrice());
            row.setStock(dbproduct.getStock());
        } catch (ComarControllerException e) {
            e.printStackTrace();
        } finally {
            controller.rollback();
            controller.endTransaction();
        }

        return row;
    }

    private String validateString(JTextField text, String property) {
        String str = text.getText();
        if (str == null || str.isEmpty()) {
            TooltipManager.showOneTimeTooltip(text, null, ComarIconLoader.load(ComarIconLoader.ERROR), "'" + property + "' no valido", TooltipWay.down);
            return null;
        }
        return str;
    }

//    private Integer validateInt(WebTextField text, String property) {
//        String strValue = validateString(text, "'" + property + "' no valido");
//        if (strValue == null) {
//            return null;
//        }
//
//        Integer intValue;
//        try {
//            intValue = Integer.parseInt(strValue);
//        } catch (NumberFormatException ex) {
//            TooltipManager.showOneTimeTooltip(text, null, ComarIconLoader.load(ComarIconLoader.ERROR), "'" + property + "' no valido", TooltipWay.down);
//            return null;
//        }
//
//        if (intValue < 0) {
//            TooltipManager.showOneTimeTooltip(text, null, ComarIconLoader.load(ComarIconLoader.ERROR), "'" + property + "' menor que cero", TooltipWay.down);
//            return null;
//        }
//
//        return intValue;
//    }
//
//    private Integer validateDec(WebTextField text, String property) {
//        String strValue = validateString(text, "'" + property + "' no valido");
//        if (strValue == null) {
//            return null;
//        }
//
//        Integer intValue;
//        try {
//            intValue = Integer.parseInt(strValue);
//        } catch (NumberFormatException ex) {
//            TooltipManager.showOneTimeTooltip(text, null, ComarIconLoader.load(ComarIconLoader.ERROR), "'" + property + "' no valido", TooltipWay.down);
//            return null;
//        }
//
//        if (intValue < 0 || intValue >= ComarProduct.DECS_BASE) {
//            TooltipManager.showOneTimeTooltip(text, null, ComarIconLoader.load(ComarIconLoader.ERROR), "'" + property + "' fuera de rango", TooltipWay.down);
//            return null;
//        }
//
//        return intValue;
//    }
    private BigDecimal validateNumber(WebTextField text, String property) {
        String str = validateString(text, "'" + property + "' no valido");
        if (str == null) {
            return null;
        }

        BigDecimal bgValue;
        try {
            bgValue = ComarNumberFormat.parse(str);
        } catch (ParseException ex) {
            TooltipManager.showOneTimeTooltip(text, null, ComarIconLoader.load(ComarIconLoader.ERROR), "'" + property + "' no valido", TooltipWay.down);
            return null;
        }

        if (bgValue.compareTo(BigDecimal.ZERO) < 0) {
            TooltipManager.showOneTimeTooltip(text, null, ComarIconLoader.load(ComarIconLoader.ERROR), "'" + property + "' fuera de rango", TooltipWay.down);
            return null;
        }
        return bgValue;
    }
}
