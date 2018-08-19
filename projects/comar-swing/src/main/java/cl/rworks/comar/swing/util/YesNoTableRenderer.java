/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author aplik
 */
public class YesNoTableRenderer extends DefaultTableCellRenderer {

    private ImageIcon yes = new ImageIcon(ComarIconLoader.load("/icons/yes.png"));
    private ImageIcon no = new ImageIcon(ComarIconLoader.load("/icons/no.png"));

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setText("");
        label.setHorizontalAlignment(SwingUtilities.CENTER);
        label.setIcon(((Boolean) value) ? yes : no);
        label.setToolTipText(((Boolean) value) ? "El producto se encuentra registrado en el sistema" : "El producto original de la factura no fue encontrado");
        return label;
    }

}
