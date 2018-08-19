/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.label.WebLabel;
import com.alee.laf.table.renderers.WebTableCellRenderer;
import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JTable;

/**
 *
 * @author aplik
 */
public class LocalDateTableCellRenderer extends WebTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        WebLabel label = (WebLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
        label.setText(((LocalDate) value).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        return label;
    }

}
