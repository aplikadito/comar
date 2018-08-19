/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.pos;

import cl.rworks.comar.swing.main.ComarSystem;
import cl.rworks.comar.swing.util.ComarUtils;
import com.alee.laf.button.WebButton;
import java.awt.event.ActionListener;
import javax.swing.Action;

/**
 *
 * @author aplik
 */
public class ComarPosButton extends WebButton {

    public ComarPosButton() {
        this("");
    }

    public ComarPosButton(String text) {
        super(text);
        setFontSize(ComarSystem.getInstance().getProperties().getFontSize());
//        setPreferredSize(ComarUtils.BUTTON_PREF_DIM);
    }

    public ComarPosButton(Action action) {
        super(action);
        setFontSize(ComarSystem.getInstance().getProperties().getFontSize());
//        setPreferredSize(ComarUtils.BUTTON_PREF_DIM);
    }

    public ComarPosButton(String text, ActionListener listener) {
        super(text, listener);
        setFontSize(ComarSystem.getInstance().getProperties().getFontSize());
//        setPreferredSize(ComarUtils.BUTTON_PREF_DIM);
    }

}
