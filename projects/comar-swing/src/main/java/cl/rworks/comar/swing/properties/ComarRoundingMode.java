/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.properties;

/**
 *
 * @author aplik
 */
public enum ComarRoundingMode {
    NONE, CHILE;

    public static ComarRoundingMode parse(String rm) {
        if (rm == null || rm.isEmpty()) {
            return null;
        }

        if (rm.equals(NONE.name())) {
            return NONE;
        }

        if (rm.equals(CHILE.name())) {
            return CHILE;
        }

        return null;
    }
}
