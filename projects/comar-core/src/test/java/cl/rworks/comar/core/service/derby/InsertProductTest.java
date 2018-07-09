/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.impl.ComarProductImpl;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class InsertProductTest {

    public static void main(String[] args) {
        Connection conn = TestUtils.createConnection();
        try {
            ComarCategory c = GetCategoryByName.serve(conn, "notfound");
            InsertProduct.serve(conn, ComarProductImpl.create("rrr"), c);
            GetAllProducts.serve(conn).stream().forEach(System.out::println);
            conn.rollback();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex1) {
            }
        }

    }
}
