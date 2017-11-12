/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

/**
 *
 * @author rgonzalez
 */
public class Test {

    public void test() throws Exception {
        
        ComarService service = new ComarServiceImpl();
        
        ComarProduct product = service.createProduct();
        System.out.println(product.getId());
        
        product.setCode("0011");
        product.setName("mate_" + product.getCode());
        service.insertProduct(product);
        
        System.out.println(product.getId());
        System.out.println(product.getDecimalFormat());
    }

    public static void main(String[] args) {
        try {
            Test test = new Test();
            test.test();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
