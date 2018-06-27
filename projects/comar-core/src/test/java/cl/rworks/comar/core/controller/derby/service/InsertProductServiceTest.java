/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.derby.service;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarProductFormatter;
import cl.rworks.comar.core.model.impl.ComarProductImpl;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author aplik
 */
public class InsertProductServiceTest {

    public static void main(String[] args) {
        try (Connection connection = TestUtils.createConnection()) {
            ComarProductImpl product = new ComarProductImpl();
            product.setCode("0001");
            product.setDescription("matesiwi");

            InsertProductService.serve(connection, product);

            List<ComarProduct> products = GetAllProductsService.serve(connection);
            products.stream().forEach(e -> {
                System.out.println(new ComarProductFormatter().format(e));
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
