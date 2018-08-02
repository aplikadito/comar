/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.swing.views.products;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 *
 * @author aplik
 */
public class ComarPanelProdsCsvInsertControllerTest {

//    public void testReadCsvFile() {
//        try {
//            System.out.println("readCsvFile");
//            File selectedFile = new File("D:\\gtin.csv");
//            ComarPanelProdsCsvInsertController instance = new ComarPanelProdsCsvInsertController();
//            List<ComarProduct> products = instance.readCsvFile(selectedFile);
//            products.stream().forEach(System.out::println);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public void read() {
        String fileName = "D:\\gtin.csv";
        File selectedFile = new File(fileName);
        try (Stream<String> lines = Files.lines(Paths.get("D:\\gtin.csv"), Charset.defaultCharset())) {
            lines.forEach(System.out::println);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void asd() {
        String fileName = "D:\\gtin.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ComarPanelProdsCsvInsertControllerTest test = new ComarPanelProdsCsvInsertControllerTest();
        test.read();
    }

}
