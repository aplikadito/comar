/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.permazen.service;

import cl.rworks.comar.core.model.ComarProductHistorial;
import io.permazen.JObject;
import io.permazen.JTransaction;
import io.permazen.annotation.JField;
import io.permazen.annotation.PermazenType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NavigableSet;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@PermazenType
public interface ComarProductHistorialPermazen extends JObject, ComarProductHistorial {

    @JField(indexed = true)
    public String getCode();

    public void setCode(String code);

    @JField(indexed = true)
    public LocalDateTime getDateTime();

    public void setDateTime(LocalDateTime now);

    public static ComarProductHistorialPermazen create(String code, LocalDateTime now, String action, String property, String oldValue, String newValue) {
        JTransaction jtx = JTransaction.getCurrent();
        ComarProductHistorialPermazen historial = jtx.create(ComarProductHistorialPermazen.class);
        historial.setCode(code);
        historial.setDateTime(now);
        historial.setAction(action);
        historial.setProperty(property);
        historial.setOldValue(oldValue);
        historial.setNewValue(newValue);
        return historial;
    }

    public static NavigableSet<ComarProductHistorialPermazen> getAll() {
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.getAll(ComarProductHistorialPermazen.class);
    }

    public static List<ComarProductHistorialPermazen> search(String strValue) {
        if (strValue == null) {
            return null;
        }

        JTransaction jtx = JTransaction.getCurrent();
        NavigableSet<ComarProductHistorialPermazen> all = jtx.getAll(ComarProductHistorialPermazen.class);
        if (!strValue.isEmpty()) {
            Pattern pattern = Pattern.compile(".*" + strValue + ".*");
            Predicate<ComarProductHistorialPermazen> filterCode = e -> pattern.matcher(e.getCode()).matches();
//            Predicate<ComarProduct> filterName = e -> pattern.matcher(e.getName()).matches();
//            Predicate<ComarProduct> filter = e -> filterCode.test(e) || filterName.test(e);
            return all.stream().filter(filterCode).collect(Collectors.toList());
        } else {
            return all.stream().collect(Collectors.toList());
        }
    }

}
