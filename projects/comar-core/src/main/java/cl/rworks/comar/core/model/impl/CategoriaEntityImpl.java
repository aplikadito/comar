/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model.impl;

import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.util.BigDecimalUtils;
import java.math.BigDecimal;

/**
 *
 * @author aplik
 */
public class CategoriaEntityImpl implements CategoriaEntity {

    private byte[] id;
    private String nombre = "";
    private BigDecimal impuestoPrincipal = CategoriaEntity.DEFAULT_IMPUESTO_PRINCIPAL;
    private BigDecimal impuestoSecundario = BigDecimal.ZERO;
    private BigDecimal porcentajeGanancia = CategoriaEntity.DEFAULT_PORCENTAJE_GANANCIA;

    public CategoriaEntityImpl() {
    }

    public CategoriaEntityImpl(String name) {
        this(null, name);
    }

    public CategoriaEntityImpl(byte[] id, String name) {
        this.id = id;
        this.nombre = name;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public BigDecimal getImpuestoPrincipal() {
        return impuestoPrincipal;
    }

    public void setImpuestoPrincipal(BigDecimal impuestoPrincipal) {
        this.impuestoPrincipal = impuestoPrincipal;
    }

    public BigDecimal getImpuestoSecundario() {
        return impuestoSecundario;
    }

    public void setImpuestoSecundario(BigDecimal impuestoSecundario) {
        this.impuestoSecundario = impuestoSecundario;
    }

    public BigDecimal getPorcentajeGanancia() {
        return porcentajeGanancia;
    }

    public void setPorcentajeGanancia(BigDecimal porcentajeGanancia) {
        this.porcentajeGanancia = porcentajeGanancia;
    }

    @Override
    public String toString() {
        return "CategoriaEntityImpl{" + "id=" + UUIDUtils.toString(id) + ", nombre=" + nombre + ", impuestoPrincipal=" + impuestoPrincipal + ", impuestoSecundario=" + impuestoSecundario + ", porcentajeGanancia=" + porcentajeGanancia + '}';
    }
    
    public static CategoriaEntity create(ResultSet rs) throws SQLException {
        CategoriaEntityImpl c = new CategoriaEntityImpl();
        c.setId(rs.getBytes(1));
        c.setNombre(rs.getString(2));
        c.setImpuestoPrincipal(BigDecimalUtils.toBigDecimal(rs.getLong(3)));
        c.setImpuestoSecundario(BigDecimalUtils.toBigDecimal(rs.getLong(4)));
        c.setPorcentajeGanancia(BigDecimalUtils.toBigDecimal(rs.getLong(5)));
        return c;
    }

    public static CategoriaEntity create() {
        return create("");
    }

    static CategoriaEntity create(byte[] id) {
        return create(null, "");
    }

    public static CategoriaEntity create(String name) {
        return create(null, name);
    }

    public static CategoriaEntity create(byte[] id, String name) {
        return new CategoriaEntityImpl(id, name);
    }
    
    public static CategoriaEntity create(String name, BigDecimal tax1, BigDecimal tax2) {
        CategoriaEntityImpl c = new CategoriaEntityImpl(null, name);
        c.setImpuestoPrincipal(tax1);
        c.setImpuestoSecundario(tax2);
        return c;
    }
}
