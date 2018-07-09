/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.derby.GetAllProducts;
import cl.rworks.comar.core.service.derby.DeleteProducts;
import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.ComarProduct;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author aplik
 */
public class DeleteProductsTest {

    public static void main(String[] args) throws Exception {
        Connection connection = TestUtils.createConnection();

        List<ComarProduct> products = GetAllProducts.serve(connection);
        products.stream().forEach(System.out::println);

        DeleteProducts.serve(connection, products);
        connection.commit();
        
        System.out.println("");
        GetAllProducts.serve(connection).stream().forEach(System.out::println);
    }
}
