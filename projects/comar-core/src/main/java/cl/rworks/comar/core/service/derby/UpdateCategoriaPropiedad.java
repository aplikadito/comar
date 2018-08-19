/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.CategoriaEntity;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.BigDecimalUtils;
import cl.rworks.comar.core.util.ComarVerifierUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class UpdateCategoriaPropiedad {

    private Connection connection;

    public UpdateCategoriaPropiedad(Connection connection) {
        this.connection = connection;
    }

    public void execute(CategoriaEntity e, String propiedad, Object valor) throws ComarServiceException {
//        if (e.getNombre().equals(CategoriaEntity.DEFAULT_CATEGORY)) {
//            throw new ComarServiceException("Esta categoria no se puede editar: " + CategoriaEntity.DEFAULT_CATEGORY);
//        }

        if (propiedad.equals("NOMBRE")) {
            ComarVerifierUtils.checkString(propiedad, valor);
            String name = ((String) valor).trim();
//            if (name.equals(CategoriaEntity.DEFAULT_CATEGORY)) {
//                throw new ComarServiceException("Nombre de Categoria reservado por el sistema: " + CategoriaEntity.DEFAULT_CATEGORY);
//            }
            updateStringProperty(e, "CATEGORIA_NOMBRE", name);
        } else if (propiedad.equals("IMPUESTOPRINCIPAL")) {
            ComarVerifierUtils.checkBigDecimal(propiedad, valor);
            BigDecimal val = (BigDecimal) valor;
            ComarVerifierUtils.checkMin(propiedad, val, CategoriaEntity.MIN_PORCENTAJE);
            ComarVerifierUtils.checkMax(propiedad, val, CategoriaEntity.MAX_PORCENTAJE);
            updateBigDecimalProperty(e, "CATEGORIA_IMPUESTOPRINCIPAL", val);
        } else if (propiedad.equals("IMPUESTOSECUNDARIO")) {
            ComarVerifierUtils.checkBigDecimal(propiedad, valor);
            BigDecimal val = (BigDecimal) valor;
            ComarVerifierUtils.checkMin(propiedad, val, CategoriaEntity.MIN_PORCENTAJE);
            ComarVerifierUtils.checkMax(propiedad, val, CategoriaEntity.MAX_PORCENTAJE);
            updateBigDecimalProperty(e, "CATEGORIA_IMPUESTOSECUNDARIO", val);
        } else if (propiedad.equals("PORCENTAJEGANANCIA")) {
            ComarVerifierUtils.checkBigDecimal(propiedad, valor);
            BigDecimal val = (BigDecimal) valor;
            ComarVerifierUtils.checkMin(propiedad, val, CategoriaEntity.MIN_PORCENTAJE);
            updateBigDecimalProperty(e, "CATEGORIA_PORCENTAJEGANANCIA", val);
        } else {
            throw new ComarServiceException("Propiedad no soportada: " + propiedad);
        }
    }

    public void updateStringProperty(CategoriaEntity e, String dbProp, String valor) throws ComarServiceException {
        String sql = "UPDATE CATEGORIA SET " + dbProp + " = ? WHERE CATEGORIA_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            ps.setString(i++, valor);
            ps.setBytes(i++, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public void updateBigDecimalProperty(CategoriaEntity e, String dbProp, BigDecimal valor) throws ComarServiceException {
        String sql = "UPDATE CATEGORIA SET " + dbProp + " = ? WHERE CATEGORIA_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            ps.setLong(i++, BigDecimalUtils.toLong(valor));
            ps.setBytes(i++, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, CategoriaEntity categoria, String propiedad, Object valor) throws ComarServiceException {
        new UpdateCategoriaPropiedad(connection).execute(categoria, propiedad, valor);
    }
}
