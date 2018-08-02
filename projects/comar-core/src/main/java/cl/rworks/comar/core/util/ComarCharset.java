/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.util;

/**
 *
 * @author aplik
 */
public enum ComarCharset {
    WINDOWS("Cp1252"),
    UNIX("UTF-8");

    private String name;

    private ComarCharset(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
