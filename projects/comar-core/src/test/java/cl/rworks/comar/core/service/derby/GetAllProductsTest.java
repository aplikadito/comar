/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarProductFormatter;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author aplik
 */
public class GetAllProductsTest {

    public static void main(String[] args) {
        try (Connection conn = TestUtils.createConnection()) {
            List<ComarProduct> products = GetAllProducts.serve(conn);
            System.out.println("===========================");
            System.out.println("PRODUCTOS: " + products.size());
            System.out.println("===========================");
            products.stream().forEach(System.out::println);
            conn.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
