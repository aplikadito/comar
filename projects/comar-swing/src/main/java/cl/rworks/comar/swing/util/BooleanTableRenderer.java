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
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author aplik
 */
public class BooleanTableRenderer extends DefaultTableCellRenderer {

    private WebPanel panel;
    private WebLabel label;
    private WebCheckBox check;
    private ImageIcon icon = new ImageIcon(ComarIconLoader.load("/icons/edit.png"));

    public BooleanTableRenderer() {
        this.panel = new WebPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panel.setOpaque(false);
        
        this.label = new WebLabel();
        label.setOpaque(false);
        
        this.check = new WebCheckBox();
        check.setOpaque(false);

        panel.add(check);
        panel.add(label);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Boolean bool = (Boolean) value;
        check.setSelected(bool);
        check.setHorizontalAlignment(SwingUtilities.RIGHT);
//        check.setHorizontalTextPosition(SwingUtilities.LEADING);

        int mrow = table.convertRowIndexToModel(row);
        int mcol = table.convertColumnIndexToModel(column);
        if (table.getModel().isCellEditable(mrow, mcol)) {
            label.setIcon(icon);
        } else {
            label.setIcon(null);
        }
        return panel;
    }

}
