/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core;

import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.service.derby.GetAllMetrics;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class TestFast {

    public static void main(String[] args) {
//        MathContext MATH_CTX = new MathContext(3, RoundingMode.HALF_UP);
//        double tercio = 1.0 / 3.0;
//        System.out.println("tercio: " + tercio);
//
//        String str = Double.toString(tercio);
//        System.out.println("str: " + str);
//        BigDecimal bd = new BigDecimal(str, MATH_CTX);
//        System.out.println(bd.toString());

        try (Connection conn = TestUtils.createConnection()) {
            String sql = "INSERT INTO COMAR_METRIC VALUES (5, 'rené', '[r]')";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
