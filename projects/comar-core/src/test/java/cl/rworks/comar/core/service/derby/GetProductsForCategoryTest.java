/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;

/**
 *
 * @author aplik
 */
public class GetProductsForCategoryTest {

    public static void main(String[] args) throws ComarServiceException {
        try (Connection conn = TestUtils.createConnection()) {
            GetAllProducts.serve(conn).stream().forEach(System.out::println);
            
            System.out.println("CATEGORIA = null");
            GetProductsForCategory.serve(conn, null).stream().forEach(System.out::println);
            conn.rollback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
