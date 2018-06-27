/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.derby.service;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarProductFormatter;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author aplik
 */
public class GetAllProductsServiceTest {

    public static void main(String[] args) {
        try (Connection connection = TestUtils.createConnection()) {
            List<ComarProduct> products = GetAllProductsService.serve(connection);
            System.out.println("===========================");
            System.out.println("PRODUCTOS: " + products.size());
            System.out.println("===========================");
            products.stream().forEach(e -> {
                String format = new ComarProductFormatter().format(e);
                System.out.println(format + "  " + format.length());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
