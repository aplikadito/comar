/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.util;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author aplik
 */
public class UUIDUtils {

    public static String toString(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            Long high = byteBuffer.getLong();
            Long low = byteBuffer.getLong();
            UUID uuid = new UUID(high, low);
            return uuid.toString();
        } else {
            return "";
        }

    }

    public static byte[] toBytes(String strId) {
        if (strId == null || strId.isEmpty()) {
            return null;
        }

        UUID uuid = UUID.fromString(strId);
        return toBytes(uuid);
    }

    public static byte[] toBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static byte[] createId() {
        UUID uuid = UUID.randomUUID();
        return toBytes(uuid);
    }

    public static byte[] createId(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT unhex(replace(uuid(), '-', ''))")) {
            ResultSet rs = ps.executeQuery();
            rs.next();
            byte[] id = rs.getBytes(1);
            ps.close();
            return id;
        } catch (SQLException ex) {
            throw ex;
        }
    }
}
