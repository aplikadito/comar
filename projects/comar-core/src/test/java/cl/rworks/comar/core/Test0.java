/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import cl.rworks.comar.core.impl.ComarDaoServiceImpl;
import cl.rworks.comar.core.model.ComarProduct;
import cl.rworks.comar.core.service.ComarDaoFactory;
import cl.rworks.comar.core.service.ComarDaoProduct;
import cl.rworks.comar.core.service.ComarDaoService;

/**
 *
 * @author rgonzalez
 */
public class Test0 {

    public void test() throws Exception {
        ComarDaoService service = new ComarDaoServiceImpl();
        ComarDaoProduct dao = ComarDaoFactory.getDaoProduct();

        service.openTransaction();
        
        ComarProduct product = dao.create();
        product.setCode("0001");
        product.setName("producto_" + product.getCode());
        
        product.setName("elemento");
        dao.update(product);

        ComarProduct fproduct = dao.getByCode("0001");
        System.out.println("fproduct: " + fproduct.getName());

        dao.delete(fproduct);
        System.out.println("size: " + dao.getAll().size());
        
        service.closeTransaction();
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
