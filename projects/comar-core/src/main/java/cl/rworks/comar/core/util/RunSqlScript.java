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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class RunSqlScript {

    public RunSqlScript() {
    }

    public void run(File file) throws RunSqlScriptException {
        try {
            run(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            throw new RunSqlScriptException("Archivo no encontrado: " + file);
        }
    }

    public void run(InputStream is) throws RunSqlScriptException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            String source = readInputStream(is);
            String[] split = source.split("/\\*sql-break\\*/");
            System.out.println("RUTINAS SQL DETECTADAS: " + split.length);

            for (String sql : split) {
                String sqlTrim = sql.trim();
                System.out.println("  SQL   : \"" + sqlTrim + "\"");
                createTable(sqlTrim);
            }
        } catch (ClassNotFoundException ex) {
            throw new RunSqlScriptException("Error: " + ex.getMessage(), ex);
        } catch (InstantiationException ex) {
            throw new RunSqlScriptException("Error: " + ex.getMessage(), ex);
        } catch (IllegalAccessException ex) {
            throw new RunSqlScriptException("Error: " + ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new RunSqlScriptException("Error: " + ex.getMessage(), ex);
        }
    }

    private String readInputStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line.trim()).append(" ");
        }
        return sb.toString();
    }

    private void createTable(String sql) {
        try (Connection conn = DriverManager.getConnection("jdbc:derby:D:\\storage;create=true")) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("  ESTADO: OK");
        } catch (SQLException e) {
            String sqlState = e.getSQLState();
            if (sqlState.equals("X0Y32")) {
                System.err.println("  ESTADO: La tabla ya existe, " + e.getMessage());
            } else {
                System.err.println("  ESTADO: " + e.getMessage());
            }
        }
        System.out.println(" ");
    }

}
