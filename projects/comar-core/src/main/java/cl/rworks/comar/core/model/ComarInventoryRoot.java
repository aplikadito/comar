/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model;

import java.util.Map;

/**
 *
 * @author aplik
 */
public interface ComarInventoryRoot {

    Map<String, ComarCategory> getCategories();

    Map<String, ComarProduct> getProducts();
}
