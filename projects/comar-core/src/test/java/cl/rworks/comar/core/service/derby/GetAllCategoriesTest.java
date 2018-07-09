/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.derby.GetAllCategories;
import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.service.ComarServiceException;
import java.sql.Connection;

/**
 *
 * @author aplik
 */
public class GetAllCategoriesTest {

    public static void main(String[] args) throws ComarServiceException {
        Connection conn = TestUtils.createConnection();
        GetAllCategories.serve(conn).stream().forEach(System.out::println);
    }
    
}
