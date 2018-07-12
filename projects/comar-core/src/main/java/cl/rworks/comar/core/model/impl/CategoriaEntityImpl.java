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

/**
 *
 * @author aplik
 */
public class CategoriaEntityImpl implements CategoriaEntity {

    private byte[] id;
    private String nombre;

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

    @Override
    public String toString() {
        return "CategoriaImpl{" + "id=" + UUIDUtils.toString(id) + ", nombre=" + nombre + '}';
    }

    public static CategoriaEntity create(ResultSet rs) throws SQLException {
        CategoriaEntityImpl c = new CategoriaEntityImpl();
        c.setId(rs.getBytes(1));
        c.setNombre(rs.getString(2));
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
}
