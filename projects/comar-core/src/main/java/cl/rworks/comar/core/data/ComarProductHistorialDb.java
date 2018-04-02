/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.data;

import cl.rworks.comar.core.model.ComarProductHistorial;
import io.permazen.JObject;
import io.permazen.JTransaction;
import io.permazen.annotation.JField;
import io.permazen.annotation.PermazenType;
import java.time.LocalDateTime;
import java.util.NavigableSet;

@PermazenType
public interface ComarProductHistorialDb extends JObject, ComarProductHistorial {

    @JField(indexed = true)
    public String getCode();

    public void setCode(String code);

    @JField(indexed = true)
    public LocalDateTime getTime();

    public void setTime(LocalDateTime now);

    public static ComarProductHistorialDb create(String code, LocalDateTime now, String action, String property, String oldValue, String newValue) {
        JTransaction jtx = JTransaction.getCurrent();
        ComarProductHistorialDb historial = jtx.create(ComarProductHistorialDb.class);
        historial.setCode(code);
        historial.setTime(now);
        historial.setAction(action);
        historial.setProperty(property);
        historial.setOldValue(oldValue);
        historial.setNewValue(newValue);
        return historial;
    }

    public static NavigableSet<ComarProductHistorialDb> getAll() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.getAll(ComarProductHistorialDb.class);
    }

}
