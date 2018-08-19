/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author aplik
 */
public class RunSqlScript {

    private boolean debug = true;

    public RunSqlScript() {
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void run(DataSource ds, File file) throws RunSqlScriptException {
        try {
            run(ds, new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            throw new RunSqlScriptException("Archivo no encontrado: " + file);
        }
    }

    public void run(DataSource ds, InputStream is) throws RunSqlScriptException {
        try {
            String source = readInputStream(is);
            String[] split = source.split("/\\*sql-break\\*/");
            print("RUTINAS SQL DETECTADAS: " + split.length);

            for (String sql : split) {
                String sqlTrim = sql.trim();
                print("  SQL   : \"" + sqlTrim + "\"");
                executeSql(ds, sqlTrim);
            }
        } catch (IOException ex) {
            throw new RunSqlScriptException("Error: " + ex.getMessage(), ex);
        }
    }

    private String readInputStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line.trim()).append(" ");
        }
        return sb.toString();
    }

    private void executeSql(DataSource ds, String sql) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            print("  ESTADO: OK");
        } catch (SQLException e) {
            String sqlState = e.getSQLState();
            if (sqlState.equals("X0Y32")) {
                print("  ESTADO: La tabla ya existe, " + e.getMessage());
            } else {
                print("  ESTADO: " + e.getMessage());
            }
        }
        print(" ");
    }

    private void print(String msg) {
        if (debug) {
            System.out.println(msg);
        }
    }

}
