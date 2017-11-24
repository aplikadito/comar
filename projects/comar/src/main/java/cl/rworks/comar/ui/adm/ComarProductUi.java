/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui.adm;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author rgonzalez
 */
public class ComarProductUi {

    private final SimpleStringProperty code;
    private final SimpleStringProperty name;

    public ComarProductUi(String fcode, String lName) {
        this.code = new SimpleStringProperty(fcode);
        this.name = new SimpleStringProperty(lName);
    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String fcode) {
        code.set(fcode);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String fname) {
        name.set(fname);
    }

}
