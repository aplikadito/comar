/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.upater;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author aplik
 */
public class Downloader {

    
    private static class ProgressListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // e.getSource() gives you the object of DownloadCountingOutputStream
            // because you set it in the overriden method, afterWrite().
            System.out.println("Downloaded bytes : " + ((DownloadCountingOutputStream) e.getSource()).getByteCount());
        }
    }

    public static void main(String[] args) {
        URL dl = null;
        File fl = null;
        String x = null;
        OutputStream os = null;
        InputStream is = null;
        ProgressListener progressListener = new ProgressListener();
        try {
            fl = new File("screenshots.zip");
            dl = new URL("http://rworks.ddns.net:8080/instaladores/comar_full.zip");
            
            os = new FileOutputStream(fl);
            is = dl.openStream();

            DownloadCountingOutputStream dcount = new DownloadCountingOutputStream(os);
            dcount.setListener(progressListener);

            // this line give you the total length of source stream as a String.
            // you may want to convert to integer and store this value to
            // calculate percentage of the progression.
            dl.openConnection().getHeaderField("Content-Length");

            // begin transfer by writing to dcount, not os.
            IOUtils.copy(is, dcount);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        }
    }
}
