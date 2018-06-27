/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.derby.service;

import cl.rworks.comar.core.ComarCoreUtils;
import cl.rworks.comar.core.TestUtils;
import cl.rworks.comar.core.model.ComarMetric;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.impl.ComarProductImpl;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author aplik
 */
public class UpdateProductServiceTest {

    public static void main(String[] args) {
        try (Connection connection = TestUtils.createConnection()) {

            ComarProduct p1 = GetProductByCodeService.serve(connection, "0005");
            byte[] id = (byte[]) p1.getId();

            ComarProduct p2 = new ComarProductImpl();
            p2.setId(id);
            p2.setCode("0002");
            p2.setDescription("nectar de damasco andina");
            p2.setBuyPrice(ComarCoreUtils.create(150));
            p2.setTax(ComarCoreUtils.create(0.19));
            p2.setSellPrice(ComarCoreUtils.create(200));
            p2.setStock(ComarCoreUtils.create(1000));
            p2.setMetric(ComarMetric.CENTIMETROS_CUBICOS);

            UpdateProductService.serve(connection, p2);

            List<ComarProduct> products = GetAllProductsService.serve(connection);
            products.stream().forEach(System.out::println);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
