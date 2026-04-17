package com.mycompany.DAO.impl;

import com.mycompany.config.Conexion;
import com.mycompany.DAO.DetallePedidoDAO;
import com.mycompany.model.DetallePedido;
import com.mycompany.model.Pedidos;
import com.mycompany.model.Productos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDAOImpl implements DetallePedidoDAO {

    @Override
    public int insertar(DetallePedido detalle) {
        String sql = "INSERT INTO detalle_pedidos (id_pedido, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detalle.getPedido().getId());
            ps.setInt(2, detalle.getProducto().getId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int actualizar(DetallePedido detalle) {
        String sql = "UPDATE detalle_pedidos SET id_pedido=?, id_producto=?, cantidad=?, precio_unitario=? WHERE id_detalle_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detalle.getPedido().getId());
            ps.setInt(2, detalle.getProducto().getId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setInt(5, detalle.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String sql = "DELETE FROM detalle_pedidos WHERE id_detalle_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public DetallePedido obtenerPorId(int id) {
        String sql = "SELECT dp.*, p.nombre, p.precio FROM detalle_pedidos dp " +
                     "JOIN productos p ON dp.id_producto = p.id_producto " +
                     "WHERE dp.id_detalle_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pedidos pedido = new Pedidos(null, 0, null, null, null, null);
                pedido.setId(rs.getInt("id_pedido"));
                
                Productos producto = new Productos();
                producto.setId(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                
                DetallePedido detalle = new DetallePedido(pedido, producto,
                    rs.getInt("cantidad"), rs.getDouble("precio_unitario"));
                detalle.setId(rs.getInt("id_detalle_pedido"));
                return detalle;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DetallePedido> listarPorPedido(int idPedido) {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "SELECT dp.*, p.nombre, p.precio, p.descripcion, p.imagen FROM detalle_pedidos dp " +
                     "JOIN productos p ON dp.id_producto = p.id_producto " +
                     "WHERE dp.id_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pedidos pedido = new Pedidos(null, 0, null, null, null, null);
                pedido.setId(rs.getInt("id_pedido"));
                
                Productos producto = new Productos();
                producto.setId(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setImagen(rs.getString("imagen"));
                producto.setPrecio(rs.getDouble("precio"));
                
                DetallePedido detalle = new DetallePedido(pedido, producto,
                    rs.getInt("cantidad"), rs.getDouble("precio_unitario"));
                detalle.setId(rs.getInt("id_detalle_pedido"));
                lista.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

