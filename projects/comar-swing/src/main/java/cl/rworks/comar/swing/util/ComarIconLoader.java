/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author aplik
 */
public class ComarIconLoader {

    public static final String ERROR = "/com/alee/extended/style/icons/status/error.png";

    public static Icon load(String path) {
        URL url = ComarIconLoader.class.getResource(path);
        return url != null ? new ImageIcon(url) : null;
    }
}
