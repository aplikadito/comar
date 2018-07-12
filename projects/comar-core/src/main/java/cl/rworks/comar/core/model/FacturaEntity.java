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
public interface FacturaEntity {

    public byte[] getId();

    public void setId(byte[] id);

    public String getCodigo();

    public void setCodigo(String code);

    public LocalDate getFecha();

    public void setFecha(LocalDate fecha);

}
