/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharsetUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(CharsetUtils.class);

    public static String getEncoding() {
        final byte[] bytes = {'D'};
        final InputStream inputStream = new ByteArrayInputStream(bytes);
        final InputStreamReader reader = new InputStreamReader(inputStream);
        final String encoding = reader.getEncoding();
        return encoding;
    }

    public static void resume() {
        LOG.info("Default Locale:   " + Locale.getDefault());
        LOG.info("Default Charset:  " + Charset.defaultCharset());
        LOG.info("file.encoding;    " + System.getProperty("file.encoding"));
        LOG.info("sun.jnu.encoding: " + System.getProperty("sun.jnu.encoding"));
        LOG.info("Default Encoding: " + getEncoding());
    }
}
