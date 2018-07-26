/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.combobox.WebComboBox;
import java.util.Collection;

/**
 *
 * @author jcarmi
 */
public class ComarComboBox extends WebComboBox {

    public ComarComboBox() {
    }
    
    public ComarComboBox(Collection<?> items) {
        super(items);
    }
}
