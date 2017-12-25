/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

/**
 *
 * @author aplik
 */
public final class ComarValidation {

    public static void validateText(String text, String msg) throws ComarValidationException {
        if (text == null || text.isEmpty()) {
            throw new ComarValidationException(msg);
        }
    }
}
