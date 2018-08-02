/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.pos;

import cl.rworks.comar.swing.main.ComarSystem;
import com.alee.laf.label.WebLabel;

/**
 *
 * @author aplik
 */
public class ComarPosLabel extends WebLabel {

    public ComarPosLabel() {
        this("");
    }

    public ComarPosLabel(String text) {
        this(text, ComarSystem.getInstance().getProperties().getFontSize());
    }

    public ComarPosLabel(String text, int fontSize) {
        super(text);
        setFontSize(fontSize);
    }

}
