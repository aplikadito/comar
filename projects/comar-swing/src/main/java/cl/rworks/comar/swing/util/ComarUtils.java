/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebInnerNotification;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author rgonzalez
 */
public class ComarUtils {

    public static final Dimension BUTTON_PREF_DIM = new Dimension(195, 35);
    //
    private static final DecimalFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("#0.000");

    public static void showInfo(String msg) {
        WebInnerNotification not = new WebInnerNotification();
        not.setContent(msg);
        not.setDisplayTime(2000);
        not.setIcon(NotificationIcon.information.getIcon());
        NotificationManager.showInnerNotification(not);
    }
    
    public static void showInfo(JComponent parent, String msg) {
        WebOptionPane.showMessageDialog(parent, msg, "Informacion", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showWarn(JComponent parent, String msg) {
        WebOptionPane.showMessageDialog(parent, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    public static void showError(JComponent parent, String msg) {
        WebOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static int showYesNo(JComponent owner, String msg, String title) {
        return WebOptionPane.showConfirmDialog(owner, msg, title, WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE);
    }

    public static String formatDbl(double dblValue) {
        return DEFAULT_DECIMAL_FORMAT.format(dblValue);
    }

    public static double parseDbl(String strValue) throws ParseException {
        return DEFAULT_DECIMAL_FORMAT.parse(strValue).doubleValue();
    }
}
