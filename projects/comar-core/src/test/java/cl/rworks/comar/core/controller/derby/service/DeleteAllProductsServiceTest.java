/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.derby.service;

import cl.rworks.comar.core.TestUtils;
import java.sql.Connection;

/**
 *
 * @author aplik
 */
public class DeleteAllProductsServiceTest {

    public static void main(String[] args) {
        try (Connection connection = TestUtils.createConnection()) {
            DeleteAllProductsService.serve(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}