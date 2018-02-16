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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
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
    private DecimalFormat dfTax = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));

    public ComarPropertiesImpl() {
        load();
    }

    @Override
    public void load() {
        properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException ex) {
            try {
                properties.load(getClass().getResourceAsStream("/comar.properties"));
            } catch (IOException ex1) {
                LOG.error("comar.properties no fue encontrado en 'resources'");
            }
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

    public Properties getProperties() {
        return properties;
    }

    @Override
    public ComarRoundingMode getRoundingMode() {
        String rm = properties.getProperty(ROUNDING_MODE);
        return ComarRoundingMode.parse(rm);
    }

    @Override
    public void setRoundingMode(ComarRoundingMode redondeo) {
        properties.setProperty(ROUNDING_MODE, redondeo.name());
    }

    @Override
    public double getDefaultTax() {
        try {
            String strIva = properties.getProperty(DEFAULT_TAX);
            return dfTax.parse(strIva).doubleValue();
        } catch (ParseException ex) {
            LOG.error("Error al parsear IVA", ex);
            return 0.19;
        }
    }

    @Override
    public void setDefaultTax(double iva) {
        properties.setProperty(DEFAULT_TAX, dfTax.format(iva));
    }

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
    public int getNormalFontSize() {
        return getInt(NORMAL_FONT_SIZE, 16);
    }

    @Override
    public void setNormalFontSize(int size) {
        setInt(NORMAL_FONT_SIZE, size);
    }

    @Override
    public int getMediumFontSize() {
        return getInt(MEDIUM_FONT_SIZE, 18);
    }

    @Override
    public void setMediumFontSize(int size) {
        setInt(MEDIUM_FONT_SIZE, size);
    }

    @Override
    public int getLargeFontSize() {
        return getInt(LARGE_FONT_SIZE, 20);
    }

    @Override
    public void setLargeFontSize(int size) {
        setInt(LARGE_FONT_SIZE, size);
    }
}
