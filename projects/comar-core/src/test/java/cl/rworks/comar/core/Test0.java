/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import cl.rworks.comar.core.model.ComarProduct;

/**
 *
 * @author rgonzalez
 */
public class Test0 {

    public void test() throws Exception {
        ComarService service = new ComarServiceImpl();
        
        ComarProduct product = service.createProduct();
        product.setCode("0001");
        product.setName("producto_" + product.getCode());
        
        service.insertProduct(product);
        System.out.println("size: " + service.getAllProducts().size());
        
        product.setName("elemento");
        service.updateProduct(product);
        
        ComarProduct fproduct = service.getByCodeProduct("0001");
        System.out.println("fproduct: " + fproduct.getName());
        
        service.deleteProduct(fproduct);
        System.out.println("size: " + service.getAllProducts().size());
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
