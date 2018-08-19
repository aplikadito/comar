/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.service.derby;

import cl.rworks.comar.core.model.ProductoEntity;
import cl.rworks.comar.core.model.VentaEntity;
import cl.rworks.comar.core.model.VentaUnidadEntity;
import cl.rworks.comar.core.service.ComarServiceException;
import cl.rworks.comar.core.util.BigDecimalUtils;
import cl.rworks.comar.core.util.UUIDUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author aplik
 */
public class InsertVentaUnidad {

    private Connection connection;

    public InsertVentaUnidad(Connection connection) {
        this.connection = connection;
    }

    public void execute(VentaUnidadEntity unidad, VentaEntity venta, ProductoEntity producto) throws ComarServiceException {
        byte[] id = UUIDUtils.createId();
        String sql = "INSERT INTO VENTAUNIDAD VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            ps.setBytes(i++, id);
            ps.setBytes(i++, unidad.getIdProducto());
            ps.setLong(i++, BigDecimalUtils.toLong(unidad.getPrecioVenta()));
            ps.setLong(i++, BigDecimalUtils.toLong(unidad.getCantidad()));
            ps.setBytes(i++, venta.getId());
            ps.executeUpdate();

            unidad.setId(id);
            unidad.setIdVenta(venta.getId());
            unidad.setIdProducto(producto.getId());
        } catch (SQLException ex) {
            throw new ComarServiceException("Error", ex);
        }
    }

    public static void serve(Connection connection, VentaUnidadEntity unidad, VentaEntity venta, ProductoEntity producto) throws ComarServiceException {
        new InsertVentaUnidad(connection).execute(unidad, venta, producto);
    }
}
