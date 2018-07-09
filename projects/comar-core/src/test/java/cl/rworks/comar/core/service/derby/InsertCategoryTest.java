/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.impl.ComarCategoryImpl;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class InsertCategoryTest {

    public static void main(String[] args) throws ComarServiceException, SQLException {
        try (Connection conn = TestUtils.createConnection()) {
            ComarCategory category = new ComarCategoryImpl("ddd");
            InsertCategory.serve(conn, category);
            GetAllCategories.serve(conn).stream().forEach(System.out::println);
            conn.rollback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
