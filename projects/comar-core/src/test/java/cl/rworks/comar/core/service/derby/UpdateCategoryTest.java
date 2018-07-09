/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.service.derby.GetCategoryByName;
import cl.rworks.comar.core.service.derby.GetAllCategories;
import cl.rworks.comar.core.service.derby.UpdateCategory;
import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.model.ComarCategory;
import java.sql.Connection;

/**
 *
 * @author aplik
 */
public class UpdateCategoryTest {
    
    public static void main(String[] args) throws ComarServiceException {
        Connection connection = TestUtils.createConnection();
        
        GetAllCategories.serve(connection).stream().forEach(System.out::println);
        ComarCategory category = GetCategoryByName.serve(connection, "Maní");
        category.setName("Abarrotes");
        UpdateCategory.serve(connection, category);
        GetAllCategories.serve(connection).stream().forEach(System.out::println);
    }
    
}
