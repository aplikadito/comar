/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import cl.rworks.comar.swing.ComarSystem;
import com.alee.laf.table.WebTable;

/**
 *
 * @author aplik
 */
public class ComarTable extends WebTable {

    public ComarTable() {
        int normalFontSize = ComarSystem.getInstance().getProperties().getNormalFontSize();
        getTableHeader().setFont(getTableHeader().getFont().deriveFont((float) normalFontSize));
        setFontSize(normalFontSize);
        setRowHeight(normalFontSize + 4);
    }

}
