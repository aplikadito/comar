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

    String ROUNDING_MODE = "ROUNDING_MODE";
    String DEFAULT_TAX = "DEFAULT_TAX";
    String NORMAL_FONT_SIZE = "NORMAL_FONT_SIZE";
    String MEDIUM_FONT_SIZE = "MEDIUM_FONT_SIZE";
    String LARGE_FONT_SIZE = "LARGE_FONT_SIZE";

    void load();

    void save();

    ComarRoundingMode getRoundingMode();

    void setRoundingMode(ComarRoundingMode roundingMode);

    double getDefaultTax();

    void setDefaultTax(double defaultTax);
    
    int getNormalFontSize();
    
    void setNormalFontSize(int size);
    
    int getMediumFontSize();
    
    void setMediumFontSize(int size);
    
    int getLargeFontSize();
    
    void setLargeFontSize(int size);
}
