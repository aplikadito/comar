/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import cl.rworks.comar.swing.main.ComarSystem;
import com.alee.laf.table.WebTable;
import java.math.BigDecimal;
import javax.swing.table.TableModel;

/**
 *
 * @author aplik
 */
public class ComarTable extends WebTable {

    public ComarTable() {
        this(null);
    }

    public ComarTable(TableModel model) {
        super(model);
        int normalFontSize = ComarSystem.getInstance().getProperties().getFontSize();
        getTableHeader().setFont(getTableHeader().getFont().deriveFont((float) normalFontSize));
        setFontSize(normalFontSize);
        setRowHeight(normalFontSize + 4);

        setDefaultRenderer(Double.class, new DoubleTableRenderer());
        setDefaultRenderer(BigDecimal.class, new BigDecimalTableRenderer());
    }

}
