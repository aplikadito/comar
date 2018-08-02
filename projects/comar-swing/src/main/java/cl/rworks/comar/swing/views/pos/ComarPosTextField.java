/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.pos;

import cl.rworks.comar.swing.main.ComarSystem;
import com.alee.laf.text.WebTextField;

/**
 *
 * @author aplik
 */
public class ComarPosTextField extends WebTextField {

    public ComarPosTextField() {
        super();
        setFontSize(ComarSystem.getInstance().getProperties().getFontSize());
        setFocusable(true);
    }

    public ComarPosTextField(int columns) {
        super(columns);
        setFontSize(ComarSystem.getInstance().getProperties().getFontSize());
        setFocusable(true);
    }

}
