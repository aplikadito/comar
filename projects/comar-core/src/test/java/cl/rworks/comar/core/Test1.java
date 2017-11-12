/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rgonzalez
 */
public class Test1 {

    public void test() throws Exception {
        ComarService service = new ComarServiceImpl();

        service.getAllProducts().stream().forEach(System.out::println);
        
        List<ComarProduct> allProducts = service.getAllProducts();
        for(ComarProduct p: allProducts){
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
            Logger.getLogger(Test1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
