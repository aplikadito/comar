/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rgonzalez
 */
public class ComarProperties {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ComarDatabase.class);

    private final File pfile = new File("comar.properties");
    private Properties properties;

    public ComarProperties() {
        load(pfile);
    }

    private void load(File file) {
        if (file.exists()) {
            try {
                properties = new Properties();
                properties.load(new FileInputStream(file));
            } catch (Exception ex) {
                ex.printStackTrace();
                properties = loadDefault(file.getName());
                save();
            }
        } else {
            properties = loadDefault(file.getName());
            save();
        }
    }

    private Properties loadDefault(String propertyFile) {
        try {
            LOG.info("Cargando propiedades por defecto");

            InputStream is = getClass().getResourceAsStream("/" + propertyFile);
            Properties prs = new Properties();
            prs.load(is);
            return prs;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public final String get(String value) {
        return properties.getProperty(value);
    }

    public final double getDbl(String value) {
        return Double.parseDouble(properties.getProperty(value));
    }

    public final double getInt(String value) {
        return Integer.parseInt(properties.getProperty(value));
    }

    public List<String> keys() {
        return new ArrayList<String>(this.properties.stringPropertyNames());
    }

    public void save() {
        LOG.info("Guardando propiedades");
        try {
            properties.store(new FileOutputStream(pfile), "");
        } catch (FileNotFoundException ex) {
            LOG.error("Archivo de propiedades no encontrado", ex);
        } catch (IOException ex) {
            LOG.error("Error al guardar archivo de propiedades", ex);
        }
    }

}
