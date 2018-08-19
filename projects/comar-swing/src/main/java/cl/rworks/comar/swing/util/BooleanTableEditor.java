/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author aplik
 */
public class BooleanTableEditor extends DefaultCellEditor {

//    private static JCheckBox check;
    private static WebPanel panel;
    private static WebLabel label;
    private static WebCheckBox check;
    private static ImageIcon icon = new ImageIcon(ComarIconLoader.load("/icons/edit.png"));

    static {
        panel = new WebPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panel.setOpaque(false);

        label = new WebLabel();
        label.setOpaque(false);
        label.setIcon(icon);

        check = new WebCheckBox();
        check.setOpaque(false);

        panel.add(check);
        panel.add(label);
    }

    public BooleanTableEditor() {
        super(check);
//        super.editorComponent = panel;
    }

    /** Implements the <code>TableCellEditor</code> interface. */
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        delegate.setValue(value);
        if (editorComponent instanceof JCheckBox) {
            //in order to avoid a "flashing" effect when clicking a checkbox
            //in a table, it is important for the editor to have as a border
            //the same border that the renderer has, and have as the background
            //the same color as the renderer has. This is primarily only
            //needed for JCheckBox since this editor doesn't fill all the
            //visual space of the table cell, unlike a text field.
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component c = renderer.getTableCellRendererComponent(table, value,
                    isSelected, true, row, column);
            if (c != null) {
                panel.setOpaque(true);
                panel.setBackground(c.getBackground());
                if (c instanceof JComponent) {
                    panel.setBorder(((JComponent)c).getBorder());
                }
            } else {
                panel.setOpaque(false);
            }
        }
        return panel;
    }
}
