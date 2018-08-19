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

    void setPrecioVentaActual(BigDecimal precioVentaActual);

    public boolean isIncluirEnBoleta();

    public void setIncluirEnBoleta(boolean incluirEnBoleta);
    
    public boolean isPrecioVentaFijo();

    public void setPrecioVentaFijo(boolean precioVentaFijo);

    BigDecimal getStockComprado();

    void setStockComprado(BigDecimal stockComprado);

    BigDecimal getStockVendido();

    void setStockVendido(BigDecimal stockVendido);

    Metrica getMetrica();

    void setMetrica(Metrica metrica);

    byte[] getCategoriaId();

    void setCategoriaId(byte[] categoriaId);

}
