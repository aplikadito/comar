/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import cl.rworks.comar.swing.ComarSystem;
import com.alee.laf.button.WebButton;
import javax.swing.Action;

/**
 *
 * @author aplik
 */
public class ComarButton extends WebButton {

    public ComarButton() {
        this("");
    }

    public ComarButton(String text) {
        super(text);
        setFontSize(ComarSystem.getInstance().getProperties().getFontSize());
        setPreferredSize(ComarUtils.BUTTON_PREF_DIM);
    }

    public ComarButton(Action action) {
        super(action);
        setFontSize(ComarSystem.getInstance().getProperties().getFontSize());
        setPreferredSize(ComarUtils.BUTTON_PREF_DIM);
    }
    
    
}
