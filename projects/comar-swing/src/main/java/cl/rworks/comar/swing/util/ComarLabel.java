/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import cl.rworks.comar.swing.ComarSystem;
import com.alee.laf.label.WebLabel;

/**
 *
 * @author aplik
 */
public class ComarLabel extends WebLabel {

    public ComarLabel() {
        this("");
    }

    public ComarLabel(String text) {
        this(text, ComarSystem.getInstance().getProperties().getFontSize());
    }

    public ComarLabel(String text, int fontSize) {
        super(text);
        setFontSize(fontSize);
    }

}
