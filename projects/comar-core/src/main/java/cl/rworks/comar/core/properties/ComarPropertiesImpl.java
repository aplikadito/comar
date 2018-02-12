/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.properties;

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
    private DecimalFormat dfIva = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));

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
    public ComarRedondeo getRedondeo() {
        String rm = properties.getProperty(REDONDEO);
        return ComarRedondeo.parse(rm);
    }

    @Override
    public void setRedondeo(ComarRedondeo redondeo) {
        properties.setProperty(REDONDEO, redondeo.name());
    }

    @Override
    public double getIva() {
        try {
            String strIva = properties.getProperty(IVA);
            return dfIva.parse(strIva).doubleValue();
        } catch (ParseException ex) {
            LOG.error("Error al parsear IVA", ex);
            return 0.19;
        }
    }

    @Override
    public void setIva(double iva) {
        properties.setProperty(IVA, dfIva.format(iva));
    }

    public static void main(String[] args) throws ParseException {
        ComarPropertiesImpl p = new ComarPropertiesImpl();
        System.out.println(p.getIva());
    }
}
