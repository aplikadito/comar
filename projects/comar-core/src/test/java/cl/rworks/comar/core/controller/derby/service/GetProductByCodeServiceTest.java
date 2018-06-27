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
public class GetProductByCodeServiceTest {

    public static void main(String[] args) {
        try (Connection connection = TestUtils.createConnection()) {
            System.out.println(GetProductByCodeService.serve(connection, "0001"));
            System.out.println(GetProductByCodeService.serve(connection, "0002"));
            System.out.println(GetProductByCodeService.serve(connection, "0003"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
