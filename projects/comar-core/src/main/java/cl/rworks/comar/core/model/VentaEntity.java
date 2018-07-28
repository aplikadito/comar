/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model;

import java.time.LocalDate;

/**
 *
 * @author aplik
 */
public interface VentaEntity {

    byte[] getId();

    void setId(byte[] id);

    String getCodigo();

    void setCodigo(String code);

    LocalDate getFecha();

    void setFecha(LocalDate batchDate);
}
