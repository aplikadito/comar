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
 * @author rgonzalez
 */
@PermazenType
public interface ComarStockKite extends JObject, ComarStock {

    @JField(indexed = true)
    default Long getId() {
        return getObjId() != null ? getObjId().asLong() : null;
    }

    void setId(Long id);

    @JField(indexed = true)
    String getCode();

    void setCode(String code);
}
