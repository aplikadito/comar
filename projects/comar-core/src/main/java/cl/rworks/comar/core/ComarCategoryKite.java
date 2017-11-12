/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import io.permazen.JObject;
import io.permazen.annotation.JField;
import io.permazen.annotation.PermazenType;

/**
 *
 * @author aplik
 */
@PermazenType
public interface ComarCategoryKite extends JObject, ComarCategory {

    @JField(indexed = true, unique = true)
    String getName();
    
    void setName(String name);

}