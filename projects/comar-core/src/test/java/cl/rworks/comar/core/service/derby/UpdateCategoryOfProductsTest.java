/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.rservices.UUIDUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author aplik
 */
public class UpdateCategoryOfProductsTest {

    public static void main(String[] args) {
        try (Connection connection = TestUtils.createConnection()) {
            try {
                Map<String, ComarCategory> idToCat = GetAllCategories.serve(connection).stream().collect(Collectors.toMap(k -> UUIDUtils.toString(k.getId()), k -> k));
                GetAllProducts.serve(connection).stream().forEach(e -> {
                    String cname = idToCat.get(UUIDUtils.toString(e.getCategoryId())).getName();
                    System.out.println(e + "; catName=" + cname);
//                    System.out.println(cname);
                });

            } catch (ComarServiceException ex) {
                ex.printStackTrace();
            } finally {
                connection.rollback();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
