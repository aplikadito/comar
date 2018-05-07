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
public interface ComarProperties {

    String IVA = "comar.iva";
    double IVA_DEFAULT = 0.19;
    //
    String LETRA = "comar.tamano.letra";
    int LETRA_DEFAULT = 20;
    //
    String AYUDA = "comar.ayuda.activa";
    int AYUDA_DEFAULT = 1;

    void load();

    void save();

    double getIva();

    void setIva(double iva);

    int getFontSize();

    void setFontSize(int size);

    boolean isHelpActive();

    void setHelpActive(boolean helpActive);
}
