/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.properties;

import java.awt.Color;

/**
 *
 * @author aplik
 */
public interface ComarProperties {

    String PERCENTUAL_IVA = "comar.iva";
    int PERCENTUAL_IVA_DEFAULT = 19;
    //
    String LETRA = "comar.tamano.letra";
    int LETRA_DEFAULT = 20;
    //
    String AYUDA = "comar.ayuda.activa";
    int AYUDA_DEFAULT = 1;
    //
//    String BANNER_COLOR = "comar.banner.color";
//    String BANNER_COLOR_DEFAULT = "35,80,35";
//    //
//    String BACKGROUND_COLOR = "comar.background.color";
//    String BACKGROUND_COLOR_DEFAULT = "240,240,240";

    void load();

    void save();

    int getPercentualIva();

    void setPercentualIva(int iva);

    int getFontSize();

    void setFontSize(int size);

    boolean isHelpActive();

    void setHelpActive(boolean helpActive);
    
//    Color getBannerColor();
//
//    void setBannerColor(Color bannerColor);
//    
//    Color getBackgroundColor();
//
//    void setBackgroundColor(Color backgroundColor);

    public void loadDefaultValues();
}
