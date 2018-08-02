/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author aplik
 */
public class TestFast {

    public static void main(String[] args) {

        try (FileWriter fw = new FileWriter(new File("D:\\trabajo\\comar\\share\\gtin.csv"))) {

            fw.append("Codigo;Descripcion").append("\n");

            Path path = Paths.get("D:\\trabajo\\comar\\share\\gtin.txt");
            try (Stream<String> stream = Files.lines(path)) {
                stream.forEach(e -> {
                    String[] split = e.split(" ");
                    if (split.length > 2) {
                        try {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 2; i < split.length; i++) {
                                sb.append(split[i]).append(" ");
                            }
                            String description = sb.toString().trim();

//                        System.out.println(split[1] + ";" + description);
                            fw.append(split[1] + ";" + description).append("\n");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
