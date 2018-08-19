/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model;

import java.math.BigDecimal;

/**
 *
 * @author aplik
 */
public interface CategoriaEntity {

//    String DEFAULT_CATEGORY = "Varios";
    BigDecimal DEFAULT_IMPUESTO_PRINCIPAL = new BigDecimal("0.19");
    BigDecimal DEFAULT_PORCENTAJE_GANANCIA = new BigDecimal("0.25");
    BigDecimal MIN_PORCENTAJE = BigDecimal.ZERO;
    BigDecimal MAX_PORCENTAJE = BigDecimal.ONE;

    byte[] getId();

    void setId(byte[] id);

    String getNombre();

    void setNombre(String nombre);

    BigDecimal getImpuestoPrincipal();

    void setImpuestoPrincipal(BigDecimal impuestoPrincipal);
    
    BigDecimal getImpuestoSecundario();

    void setImpuestoSecundario(BigDecimal impuestoSecundario);
    
    BigDecimal getPorcentajeGanancia();

    void setPorcentajeGanancia(BigDecimal porcentajeGanancia);

}
