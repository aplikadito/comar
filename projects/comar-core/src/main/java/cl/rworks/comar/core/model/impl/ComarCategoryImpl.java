/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.model.impl;

import cl.rworks.comar.core.model.ComarCategory;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class ComarCategoryImpl implements ComarCategory {

    private byte[] id;
    private String name;

    public ComarCategoryImpl() {
    }

    public ComarCategoryImpl(String name) {
        this(null, name);
    }

    public ComarCategoryImpl(byte[] id, String name) {
        this.id = id;
        this.name = name;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return String.format("%s=%s, %s=%s", "id", UUIDUtils.toString(id), "name", name);
    }

    public static ComarCategory create(ResultSet rs) throws SQLException {
        ComarCategoryImpl c = new ComarCategoryImpl();
        c.setId(rs.getBytes(1));
        c.setName(rs.getString(2));
        return c;
    }

//    public static ComarCategory create(JSONObject jrequest) {
//        ComarCategoryImpl c = new ComarCategoryImpl();
//        c.setId(UUIDUtils.toBytes(jrequest.getString("id")));
//        c.setName(jrequest.getString("name"));
//        return c;
//    }

    public static ComarCategory create() {
        return create("");
    }

    static ComarCategory create(byte[] id) {
        return create(null, "");
    }

    public static ComarCategory create(String name) {
        return create(null, name);
    }

    public static ComarCategory create(byte[] id, String name) {
        return new ComarCategoryImpl(id, name);
    }
}
