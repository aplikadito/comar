/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.cgs.websuite.webcomar.db;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author rgonzalez
 */
public class Test {
 
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("comar-pu").createEntityManager();
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
    }
}
