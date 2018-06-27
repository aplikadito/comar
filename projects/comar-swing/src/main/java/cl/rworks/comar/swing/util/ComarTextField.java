/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import cl.rworks.comar.swing.ComarSystem;
import com.alee.laf.text.WebTextField;

/**
 *
 * @author aplik
 */
public class ComarTextField extends WebTextField {

    public ComarTextField() {
        super();
        setFontSize(ComarSystem.getInstance().getProperties().getFontSize());
        setFocusable(true);
    }

    public ComarTextField(int columns) {
        super(columns);
        setFontSize(ComarSystem.getInstance().getProperties().getFontSize());
        setFocusable(true);
    }

}
