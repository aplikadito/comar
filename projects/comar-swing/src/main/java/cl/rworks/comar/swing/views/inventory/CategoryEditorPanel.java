/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.inventory;

import cl.rworks.comar.core.model.ComarCategory;
import com.alee.extended.layout.FormLayout;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;

/**
 *
 * @author aplik
 */
public class CategoryEditorPanel extends WebPanel {

    private WebTextField textName = new WebTextField();

    public CategoryEditorPanel() {
        setLayout(new FormLayout(10, 10));
        add(new WebLabel("Nombre"));
        add(textName);
    }

    public void updateForm(ComarCategory c) {
        this.textName.setText(c.getName());
    }

    public WebTextField getTextName() {
        return textName;
    }

}
