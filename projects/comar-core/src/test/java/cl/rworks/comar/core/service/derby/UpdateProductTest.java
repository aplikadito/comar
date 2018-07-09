/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import java.sql.Connection;

/**
 *
 * @author aplik
 */
public class UpdateProductTest {

    public static void main(String[] args) {
        try (Connection conn = TestUtils.createConnection()) {

//            DeleteProductByCode.serve(conn, "xxxx");
//            DeleteProductByCode.serve(conn, "yyyy");
//            
//            ComarProductImpl product = new ComarProductImpl("xxxx", "mate");
//            InsertProduct.serve(conn, product);
//            GetAllProducts.serve(conn).stream().forEach(System.out::println);
//            
//            System.out.println("");
//            product.setCode("yyyy");
//            UpdateProduct.serve(conn, product);
//            GetAllProducts.serve(conn).stream().forEach(System.out::println);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
