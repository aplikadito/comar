/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import cl.rworks.comar.core.model.Metrica;
import com.alee.laf.combobox.WebComboBox;
import javax.swing.DefaultCellEditor;

/**
 *
 * @author aplik
 */
public class MetricaTableEditor extends DefaultCellEditor {

    public MetricaTableEditor() {
        super(createComarMetricComboBox());
    }

    private static WebComboBox createComarMetricComboBox() {
        WebComboBox combo = new WebComboBox();
        Metrica[] metrics = Metrica.values();
        for (Metrica metric : metrics) {
            combo.addItem(metric);
        }
        return combo;
    }

}
