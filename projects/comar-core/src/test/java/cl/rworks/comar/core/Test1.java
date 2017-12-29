/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import cl.rworks.comar.core.impl.ComarDatabaseServiceImpl;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.model.ComarCategory;
import java.util.List;
import cl.rworks.comar.core.service.ComarDatabaseServiceProduct;

/**
 *
 * @author rgonzalez
 */
public class Test1 {

    public void test() throws Exception {
        ComarDatabaseServiceProduct service = new ComarDatabaseServiceImpl().getServiceProduct();

        service.getAll().stream().forEach(System.out::println);

        List<ComarProduct> allProducts = service.getAll();
        for (ComarProduct p : allProducts) {
            ComarCategory cate = p.getCategory();
        }

//            System.out.println(String.format("[%s %s %s %s %s]", e.getId(), e.getCode(), e.getName(), e.getDecimalFormat(), e.getUnit()));
//        });
    }

    public static void main(String[] args) {
        try {
            Test1 test = new Test1();
            test.test();
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

}
