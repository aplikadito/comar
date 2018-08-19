/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.properties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aplik
 */
public class ComarPropertiesImpl implements ComarProperties {

    private static final Logger LOG = LoggerFactory.getLogger(ComarPropertiesImpl.class);
    //
    private Properties properties;
    private File file = new File("comar.properties");

    public ComarPropertiesImpl() {
        load();
    }

    @Override
    public void load() {
        properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException ex) {
            loadDefaultValues();
            LOG.info("Cargando propiedades por defecto");
        }
    }

    @Override
    public void save() {
        if (properties == null) {
            LOG.error("Primero debe cargar las propiedades");
            return;
        }

        try {
            properties.store(new FileOutputStream(file), "");
        } catch (IOException ex) {
            LOG.error("Las propiedades no ha podido ser guardadas", ex);
        }
    }

    @Override
    public void loadDefaultValues() {
//        setPercentualIva(PERCENTUAL_IVA_DEFAULT);
        setFontSize(LETRA_DEFAULT);
//        setBannerColor(parseColor(BANNER_COLOR_DEFAULT));
//        setBackgroundColor(parseColor(BACKGROUND_COLOR_DEFAULT));
//        setHelpActive(AYUDA_DEFAULT == 1);
    }

//    public Properties getProperties() {
//        return properties;
//    }

//    @Override
//    public int getPercentualIva() {
//        try {
//            String strIva = properties.getProperty(PERCENTUAL_IVA);
//            return Integer.parseInt(strIva);
//        } catch (NumberFormatException ex) {
//            LOG.error("Error al parsear IVA", ex);
//            return PERCENTUAL_IVA_DEFAULT;
//        }
//    }
//
//    @Override
//    public void setPercentualIva(int iva) {
//        properties.setProperty(PERCENTUAL_IVA, Integer.toString(iva));
//    }

    private int getInt(String property, int defaultValue) {
        try {
            String strValue = properties.getProperty(property);
            return strValue != null ? Integer.parseInt(strValue) : defaultValue;
        } catch (NumberFormatException ex) {
            LOG.error("Error al parsear " + property, ex);
            return defaultValue;
        }
    }

    private void setInt(String property, int value) {
        String strValue = Integer.toString(value);
        properties.setProperty(property, strValue);
    }

    @Override
    public int getFontSize() {
        return getInt(LETRA, LETRA_DEFAULT);
    }

    @Override
    public void setFontSize(int size) {
        setInt(LETRA, size);
    }

//    @Override
//    public boolean isHelpActive() {
//        return getInt(AYUDA, 1) == 1;
//    }
//
//    @Override
//    public void setHelpActive(boolean helpActive) {
//        setInt(AYUDA, helpActive ? 1 : 0);
//    }

//    private Color parseColor(String strColor) {
//        String[] split = strColor.split(",");
//        int r = Integer.parseInt(split[0].trim());
//        int g = Integer.parseInt(split[1].trim());
//        int b = Integer.parseInt(split[2].trim());
//        return new Color(r, g, b);
//    }

//    private String formatColor(Color color) {
//        return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
//    }

//    @Override
//    public Color getBannerColor() {
//        String strValue = properties.getProperty(BANNER_COLOR);
//        if (strValue != null && !strValue.isEmpty()) {
//            String[] split = strValue.split(",");
//            if (split.length == 3) {
//                return parseColor(strValue);
//            } else {
//                return parseColor(BANNER_COLOR_DEFAULT);
//            }
//        } else {
//            return parseColor(BANNER_COLOR_DEFAULT);
//        }
//    }
//
//    @Override
//    public void setBannerColor(Color bannerColor) {
//        String strColor = formatColor(bannerColor);
//        properties.setProperty(BANNER_COLOR, strColor);
//    }
//
//    @Override
//    public Color getBackgroundColor() {
//        String strValue = properties.getProperty(BACKGROUND_COLOR);
//        if (strValue != null && !strValue.isEmpty()) {
//            String[] split = strValue.split(",");
//            if (split.length == 3) {
//                return parseColor(strValue);
//            } else {
//                return parseColor(BACKGROUND_COLOR_DEFAULT);
//            }
//        } else {
//            return parseColor(BACKGROUND_COLOR_DEFAULT);
//        }
//    }
//
//    @Override
//    public void setBackgroundColor(Color backgroundColor) {
//        String strColor = formatColor(backgroundColor);
//        properties.setProperty(BACKGROUND_COLOR, strColor);
//    }

}
