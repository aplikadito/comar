/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.products;

import cl.rworks.comar.core.data.ComarProductDb;
import cl.rworks.comar.core.data.ComarProductHistorialDb;
import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarProductHistorialAction;
import cl.rworks.comar.core.model.ComarProductProperties;
import cl.rworks.comar.core.service.ComarService;
import cl.rworks.comar.swing.ComarSystem;
import cl.rworks.comar.swing.pointofsell.ComarPanelPointOfSell;
import cl.rworks.comar.swing.util.ComarButton;
import cl.rworks.comar.swing.util.ComarCloseAction;
import cl.rworks.comar.swing.util.ComarIconLoader;
import cl.rworks.comar.swing.util.ComarPanel;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.ValidationMode;
import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.table.AbstractTableModel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

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

        this.panelEditor = new BaseEditorPanel();
        panel.add(panelEditor, BorderLayout.CENTER);

        this.tableModel = new TableModel();
        WebTable table = panelEditor.getTable();
        table.setModel(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    new EditAction().actionPerformed(null);
                }
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

        private String[] columnNames = new String[]{"Codigo", "Nombre", "Unidad", "Precio Compra", "Precio Venta", "Stock"};

        private List<ComarPanelProductRow> products;

        public List<ComarPanelProductRow> getProducts() {
            return products;
        }

        public void setProducts(List<ComarPanelProductRow> products) {
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
        public Object getValueAt(int rowIndex, int columnIndex) {
            ComarPanelProductRow p = products.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return p.getCode();
                case 1:
                    return p.getName();
                case 2:
                    return p.getMetric().getName();
                case 3:
                    return ComarUtils.formatDbl(p.getBuyPrice());
                case 4:
                    return ComarUtils.formatDbl(p.getSellPrice());
                case 5:
                    return ComarUtils.formatDbl(p.getStock());
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
        List<ComarPanelProductRow> products = loadProducts(strText);
        tableModel.setProducts(products);
        tableModel.fireTableDataChanged();
        ComarUtils.showInfo(products.size() + " productos encontrados");
    }

    private void clear() {
        this.panelEditor.getTextSearch().clear();
        this.tableModel.setProducts(null);
        this.tableModel.fireTableDataChanged();
    }

    private List<ComarPanelProductRow> loadProducts(String strText) {
        ComarService service = ComarSystem.getInstance().getService();
        Permazen db = service.getDb();

        List<ComarPanelProductRow> rows = new ArrayList<>();
        JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        try {
            if (strText.isEmpty()) {
                ComarProductDb.getAll().stream().forEach((ComarProduct p) -> {
                    rows.add(toRow(p));
                });
            } else {
                ComarProductDb.search(strText).stream().forEach((ComarProduct p) -> {
                    rows.add(toRow(p));
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jtx.rollback();
            JTransaction.setCurrent(null);
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
            ComarPanelProductEditor panel = new ComarPanelProductEditor();
            panel.updateForm(new ComarPanelProductRow(text));
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
            WebTextField textCode = panel.getTextCode();
            WebTextField textName = panel.getTextName();
            WebComboBox comboMetric = panel.getComboMetric();
            WebTextField textBuyPrice = panel.getTextBuyPrice();
            WebTextField textSellPrice = panel.getTextSellPrice();
            WebTextField textStock = panel.getTextStock();

            String code = validateCode(textCode);
            String name = validateString(textName, "Nombre");
            ComarMetric metric = (ComarMetric) comboMetric.getSelectedItem();
            Double buyPrice = validateDouble(textBuyPrice, "Precio de Compra");
            Double sellPrice = validateDouble(textSellPrice, "Precio de Venta");
            Double stock = validateDouble(textStock, "Stock");

            boolean validate = code != null;
            validate = validate && name != null;
            validate = validate && metric != null;
            validate = validate && buyPrice != null;
            validate = validate && sellPrice != null;
            validate = validate && stock != null;

            if (validate) {
                ComarService service = ComarSystem.getInstance().getService();
                Permazen db = service.getDb();
                JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
                JTransaction.setCurrent(jtx);
                try {
                    ComarProduct product = ComarProductDb.create();
                    product.setCode(code);
                    product.setDescription(name);
                    product.setMetric(metric);
                    product.setBuyPrice(buyPrice);
                    product.setSellPrice(sellPrice);
                    product.setStock(stock);

                    LocalDateTime now = LocalDateTime.now();
//                    String nowStr = now.formatDbl(df);
                    ComarProductHistorialDb.create(code, now, "NEW", ComarProductProperties.CODE.name(), "", code);
                    ComarProductHistorialDb.create(code, now, "NEW", ComarProductProperties.NAME.name(), "", name);
                    ComarProductHistorialDb.create(code, now, "NEW", ComarProductProperties.METRIC.name(), "", metric.name());
                    ComarProductHistorialDb.create(code, now, "NEW", ComarProductProperties.BUYPRICE.name(), "", ComarUtils.formatDbl(buyPrice));
                    ComarProductHistorialDb.create(code, now, "NEW", ComarProductProperties.SELLPRICE.name(), "", ComarUtils.formatDbl(sellPrice));
                    ComarProductHistorialDb.create(code, now, "NEW", ComarProductProperties.STOCK.name(), "", ComarUtils.formatDbl(stock));
                    jtx.commit();

//                    ComarUtils.showInfo("Producto Agregado");
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

    private class EditAction extends AbstractAction {

        public EditAction() {
            putValue(NAME, "Editar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ComarPanelProductRow row = getSelectedProductRow();
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
            ComarPanelProductRow oldRow = panel.getOldRow();

            WebTextField textCode = panel.getTextCode();
            WebTextField textName = panel.getTextName();
            WebComboBox comboMetric = panel.getComboMetric();
            WebTextField textBuyPrice = panel.getTextBuyPrice();
            WebTextField textSellPrice = panel.getTextSellPrice();
            WebTextField textStock = panel.getTextStock();

            ComarService service = ComarSystem.getInstance().getService();
            Permazen db = service.getDb();

            boolean codeChanged = false;
            String code = textCode.getText().trim();
            if (!oldRow.getCode().equals(code)) {
                code = validateCode(textCode);
                codeChanged = true;
            }

            boolean nameChanged = false;
            String name = textName.getText().trim();
            if (!oldRow.getName().equals(name)) {
                name = validateString(textName, "Nombre");
                nameChanged = true;
            }

            boolean metricChanged = false;
            ComarMetric metric = (ComarMetric) comboMetric.getSelectedItem();
            if (!oldRow.getMetric().equals(metric)) {
                metricChanged = true;
            }

            boolean buyPriceChanged = false;
            Double buyPrice = validateDouble(textBuyPrice, "Precio de Compra");
            if (oldRow.getBuyPrice() != buyPrice) {
                buyPriceChanged = true;
            }

            boolean sellPriceChanged = false;
            Double sellPrice = validateDouble(textSellPrice, "Precio de Venta");
            if (oldRow.getSellPrice() != sellPrice) {
                sellPriceChanged = true;
            }

            boolean stockChanged = false;
            Double stock = validateDouble(textStock, "Stock");
            if (oldRow.getStock() != stock) {
                stockChanged = true;
            }

            boolean validate = code != null;
            validate = validate && name != null;
            validate = validate && metric != null;
            validate = validate && buyPrice != null;
            validate = validate && sellPrice != null;
            validate = validate && stock != null;

            if (validate) {
                JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
                JTransaction.setCurrent(jtx);
                try {
                    ComarProduct product = ComarProductDb.getByCode(oldRow.getCode());
                    LocalDateTime now = LocalDateTime.now();
                    String action = ComarProductHistorialAction.EDIT.name();
                    if (codeChanged) {
                        product.setCode(code);
                        ComarProductHistorialDb.create(oldRow.getCode(), now, action, ComarProductProperties.CODE.name(), oldRow.getCode(), code);
                    }

                    if (nameChanged) {
                        product.setDescription(name);
                        ComarProductHistorialDb.create(code, now, action, ComarProductProperties.NAME.name(), oldRow.getName(), name);
                    }

                    if (metricChanged) {
                        product.setMetric(metric);
                        ComarProductHistorialDb.create(code, now, action, ComarProductProperties.METRIC.name(), oldRow.getMetric().name(), metric.name());
                    }

                    if (buyPriceChanged) {
                        product.setBuyPrice(buyPrice);
                        ComarProductHistorialDb.create(code, now, action, ComarProductProperties.BUYPRICE.name(), ComarUtils.formatDbl(oldRow.getBuyPrice()), ComarUtils.formatDbl(buyPrice));
                    }

                    if (sellPriceChanged) {
                        product.setSellPrice(sellPrice);
                        ComarProductHistorialDb.create(code, now, action, ComarProductProperties.SELLPRICE.name(), ComarUtils.formatDbl(oldRow.getSellPrice()), ComarUtils.formatDbl(sellPrice));
                    }

                    if (stockChanged) {
                        product.setStock(stock);
                        ComarProductHistorialDb.create(code, now, action, ComarProductProperties.STOCK.name(), ComarUtils.formatDbl(oldRow.getStock()), ComarUtils.formatDbl(stock));
                    }

                    jtx.commit();

//                    ComarUtils.showInfo("Producto Editado");
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

            List<ComarPanelProductRow> list = new ArrayList<>();
            for (int i = 0; i < vrows.length; i++) {
                int vrow = vrows[i];
                int mrow = panelEditor.getTable().convertRowIndexToModel(vrow);
                ComarPanelProductRow row = tableModel.getProducts().get(mrow);
                list.add(row);
            }

            tableModel.getProducts().removeAll(list);
            tableModel.fireTableDataChanged();
        }

    }

    private ComarPanelProductRow toRow(ComarProduct p) {
        ComarPanelProductRow row = new ComarPanelProductRow();
        row.setCode(p.getCode());
        row.setName(p.getDescription());
        row.setMetric(p.getMetric());
        row.setBuyPrice(p.getBuyPrice());
        row.setSellPrice(p.getSellPrice());
        row.setStock(p.getStock());
        return row;
    }

    private ComarPanelProductRow getSelectedProductRow() {
        int vrow = panelEditor.getTable().getSelectedRow();
        if (vrow == -1) {
            ComarUtils.showWarn(ComarPanelProductsSearch.this, "Seleccione un producto");
            return null;
        }

        int mrow = panelEditor.getTable().convertRowIndexToModel(vrow);
        ComarPanelProductRow selected = tableModel.getProducts().get(mrow);
        Permazen db = ComarSystem.getInstance().getService().getDb();
        JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        ComarPanelProductRow row;
        try {
            ComarProduct dbproduct = ComarProductDb.getByCode(selected.getCode());
            row = new ComarPanelProductRow();
            row.setCode(dbproduct.getCode());
            row.setName(dbproduct.getDescription());
            row.setMetric(dbproduct.getMetric());
            row.setBuyPrice(dbproduct.getBuyPrice());
            row.setSellPrice(dbproduct.getSellPrice());
            row.setStock(dbproduct.getStock());
        } finally {
            jtx.rollback();
            JTransaction.setCurrent(null);
        }

        return row;
    }

    private String validateString(WebTextField text, String property) {
        String strCode = text.getText().trim();
        if (strCode == null || strCode.isEmpty()) {
            TooltipManager.showOneTimeTooltip(text, null, ComarIconLoader.load(ComarIconLoader.ERROR), "'" + property + "' no valido", TooltipWay.down);
            return null;
        }
        return strCode;
    }

    private String validateCode(WebTextField textCode) {
        String strCode = validateString(textCode, "Codigo");
        if (strCode == null) {
            return null;
        }

        ComarService service = ComarSystem.getInstance().getService();
        Permazen db = service.getDb();
        JTransaction jtx = db.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        try {
            ComarProduct product = ComarProductDb.getByCode(strCode);
            if (product != null) {
                TooltipManager.showOneTimeTooltip(textCode, null, ComarIconLoader.load(ComarIconLoader.ERROR), "El codigo ya existe", TooltipWay.down);
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jtx.rollback();
            JTransaction.setCurrent(null);
        }

        return strCode;
    }

    private Double validateDouble(WebTextField text, String property) {
        String strValue = validateString(text, "'" + property + "' no valido");
        if (strValue == null) {
            return null;
        }

        Double dblValue;
        try {
            dblValue = ComarUtils.parseDbl(strValue);
        } catch (ParseException ex) {
            TooltipManager.showOneTimeTooltip(text, null, ComarIconLoader.load(ComarIconLoader.ERROR), "'" + property + "' no valido", TooltipWay.down);
            return null;
        }

        if (dblValue < 0) {
            TooltipManager.showOneTimeTooltip(text, null, ComarIconLoader.load(ComarIconLoader.ERROR), "'" + property + "' menor que cero", TooltipWay.down);
            return null;
        }

        return dblValue;
    }
}
