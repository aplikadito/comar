/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.derby.DeleteCategory;
import cl.rworks.comar.core.service.derby.GetAllCategories;
import cl.rworks.comar.core.service.derby.InsertCategory;
import cl.rworks.comar.core.service.derby.RemoveCategoryByName;
import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.model.impl.ComarCategoryImpl;
import java.sql.Connection;

/**
 *
 * @author aplik
 */
public class DeleteCategoryTest {

    public static void main(String[] args) throws Exception {
        Connection connection = TestUtils.createConnection();
        ComarCategory c = ComarCategoryImpl.create("xxx");

        RemoveCategoryByName.serve(connection, "xxx");
        System.out.println("");
        GetAllCategories.serve(connection).stream().forEach(System.out::println);

        InsertCategory.serve(connection, c);
        System.out.println("");
        GetAllCategories.serve(connection).stream().forEach(System.out::println);

        DeleteCategory.serve(connection, c);
        System.out.println("");
        GetAllCategories.serve(connection).stream().forEach(System.out::println);
        
        connection.commit();
    }

}
