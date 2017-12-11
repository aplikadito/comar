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
public class Test0 {

    public void test() throws Exception {
        ComarServiceProduct service = new ComarServiceImpl().getServiceProduct();
        
        ComarProduct product = service.create();
        product.setCode("0001");
        product.setName("producto_" + product.getCode());
        
        service.insert(product);
        System.out.println("size: " + service.getAll().size());
        
        product.setName("elemento");
        service.update(product);
        
        ComarProduct fproduct = service.getByCode("0001");
        System.out.println("fproduct: " + fproduct.getName());
        
        service.delete(fproduct);
        System.out.println("size: " + service.getAll().size());
    }

    public static void main(String[] args) {
        try {
            Test0 test = new Test0();
            test.test();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
