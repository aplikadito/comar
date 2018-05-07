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
            setIva(IVA_DEFAULT);
            setFontSize(LETRA_DEFAULT);
            setHelpActive(AYUDA_DEFAULT == 1);
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

    public Properties getProperties() {
        return properties;
    }

    @Override
    public double getIva() {
        try {
            String strIva = properties.getProperty(IVA);
            return dfTax.parse(strIva).doubleValue();
        } catch (ParseException ex) {
            LOG.error("Error al parsear IVA", ex);
            return 0.19;
        }
    }

    @Override
    public void setIva(double iva) {
        properties.setProperty(IVA, dfTax.format(iva));
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
    public int getFontSize() {
        return getInt(LETRA, LETRA_DEFAULT);
    }

    @Override
    public void setFontSize(int size) {
        setInt(LETRA, size);
    }

    @Override
    public boolean isHelpActive() {
        return getInt(AYUDA, 1) == 1;
    }

    @Override
    public void setHelpActive(boolean helpActive) {
        setInt(AYUDA, helpActive ? 1 : 0);
    }

}
