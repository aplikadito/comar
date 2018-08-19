/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.text.WebTextField;
import java.awt.Component;
import java.math.BigDecimal;
import java.text.ParseException;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author aplik
 */
public class BigDecimalTableEditor extends AbstractCellEditor implements TableCellEditor {

    private WebTextField text;

    public BigDecimalTableEditor() {
        this.text = new WebTextField();
        this.text.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.text.setMargin(-5, -5, -5, -5);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//        JTextField text = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
        String strValue = ComarUtils.format((BigDecimal) value);
        text.setText(strValue);
        text.setHorizontalAlignment(SwingUtilities.RIGHT);
        text.selectAll();
        return text;
    }

    @Override
    public Object getCellEditorValue() {
        try {
            String str = text.getText();
            return ComarUtils.parse(str);
        } catch (ParseException ex) {
            return null;
        }
    }

}
