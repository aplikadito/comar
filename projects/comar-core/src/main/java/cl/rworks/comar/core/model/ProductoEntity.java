/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model;

import java.math.BigDecimal;

/**
 *
 * @author rgonzalez
 */
public interface ProductoEntity {

    byte[] getId();

    void setId(byte[] id);

    String getCodigo();

    void setCodigo(String code);

    String getDescripcion();

    void setDescripcion(String description);
    
    public BigDecimal getPrecioVentaActual();

    public void setPrecioVentaActual(BigDecimal precioVentaActual);

    public BigDecimal getStockActual();

    public void setStockActual(BigDecimal stockActual);

    Metrica getMetrica();

    void setMetrica(Metrica metrica);

    byte[] getCategoriaId();

    void setCategoriaId(byte[] categoriaId);

}
