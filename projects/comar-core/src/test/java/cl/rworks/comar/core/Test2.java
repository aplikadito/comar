/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import cl.rworks.comar.core.impl.ComarServiceImpl;
import cl.rworks.comar.core.model.ComarProduct;

/**
 *
 * @author rgonzalez
 */
public class Test2 {

    public void test() throws Exception {
        ComarServiceProduct service = new ComarServiceImpl().getServiceProduct();

//        long id = -244252732922459347L;
        String code = "0010";

        ComarProduct pp = service.getByCode(code);
        System.out.println(pp.getName());

    }

    public static void main(String[] args) {
        try {
            Test2 test = new Test2();
            test.test();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
