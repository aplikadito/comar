/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.text.WebTextField;
import java.awt.event.ActionListener;

/**
 *
 * @author aplik
 */
public class WebTextFieldFactory {

    private final WebTextField text;

    public WebTextFieldFactory() {
        this.text = new WebTextField();
    }

    public WebTextFieldFactory cols(int cols) {
        text.setColumns(cols);
        return this;
    }

    public WebTextFieldFactory actionListener(ActionListener l) {
        text.addActionListener(l);
        return this;
    }

    public WebTextField create() {
        return text;
    }
}
