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
public class Test0 {

    public void test() throws Exception {
        ComarService service = new ComarServiceImpl();
        ComarProduct product = service.createProduct();
        product.setCode("0011");
        product.setName("mate_" + product.getCode());
        service.insertProduct(product);
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
