/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import java.awt.Component;
import java.math.BigDecimal;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author aplik
 */
public class BigDecimalTableRenderer extends DefaultTableCellRenderer {

    private ImageIcon icon = new ImageIcon(ComarIconLoader.load("/icons/edit.png"));

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        String strValue = "";
        if (table.getModel() instanceof PercentualTableModel) {
            PercentualTableModel pmodel = (PercentualTableModel) table.getModel();
            if (pmodel.isPercentual(row, column)) {
                strValue = ComarUtils.formatPercentual((BigDecimal) value);
            } else {
                strValue = ComarUtils.format((BigDecimal) value);
            }
        } else {
            strValue = ComarUtils.format((BigDecimal) value);
        }

        label.setText(strValue);
        label.setHorizontalAlignment(SwingUtilities.RIGHT);
        label.setHorizontalTextPosition(SwingUtilities.LEADING);

        int mrow = table.convertRowIndexToModel(row);
        int mcol = table.convertColumnIndexToModel(column);
        if (table.getModel().isCellEditable(mrow, mcol)) {
            label.setIcon(icon);
//            label.setHorizontalTextPosition(BOTTOM);
        } else {
            label.setIcon(null);
        }
        return label;
    }

}
