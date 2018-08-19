/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.util;

import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.model.Metrica;
import cl.rworks.comar.core.service.ComarServiceException;
import java.math.BigDecimal;

/**
 *
 * @author aplik
 */
public final class ComarVerifierUtils {

    public static void checkString(String property, Object value) throws ComarServiceException {
        if (!(value instanceof String)) {
            throw new ComarServiceException("El valor para la propiedad '" + property + "' debe ser alfanumerico");
        }
    }

    public static void checkBigDecimal(String property, Object value) throws ComarServiceException {
        if (!(value instanceof BigDecimal)) {
            throw new ComarServiceException("El valor para la propiedad '" + property + "' debe ser numerico");
        }
    }

    public static void checkMetrica(String property, Object value) throws ComarServiceException {
        if (!(value instanceof Metrica)) {
            throw new ComarServiceException("El valor para la propiedad '" + property + "' debe ser de tipo Metrica");
        }
    }

    public static void checkBoolean(String property, Object value) throws ComarServiceException {
        if (!(value instanceof Boolean)) {
            throw new ComarServiceException("El valor para la propiedad '" + property + "' debe ser de tipo booleano");
        }
    }

    public static void checkMin(String propiedad, BigDecimal val, BigDecimal min) throws ComarServiceException {
        if (val.compareTo(min) < 0) {
            throw new ComarServiceException(propiedad + " debe ser mayor que " + min);
        }
    }

    public static void checkMax(String propiedad, BigDecimal val, BigDecimal max) throws ComarServiceException {
        if (val.compareTo(max) > 0) {
            throw new ComarServiceException(propiedad + " debe ser menor que " + max);
        }
    }
}
