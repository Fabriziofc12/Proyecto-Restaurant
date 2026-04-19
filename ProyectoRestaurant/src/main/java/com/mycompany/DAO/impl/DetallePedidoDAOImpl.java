package com.mycompany.DAO.impl;

import com.mycompany.DAO.DetallePedidoDAO;
import com.mycompany.model.DetallePedido;
import com.mycompany.model.Pedidos;
import com.mycompany.model.Productos;
import com.mycompany.model.Categoria;
import com.mycompany.config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDAOImpl implements DetallePedidoDAO {

    @Override
    public int insertar(DetallePedido detallePedido) {
        int resultado = 0;
        String sql = "INSERT INTO detalle_pedidos (id_pedido, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detallePedido.getPedido().getId());
            ps.setInt(2, detallePedido.getProducto().getId());
            ps.setInt(3, detallePedido.getCantidad());
            ps.setDouble(4, detallePedido.getPrecioUnitario());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar detalle pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int actualizar(DetallePedido detallePedido) {
        int resultado = 0;
        String sql = "UPDATE detalle_pedidos SET id_pedido=?, id_producto=?, cantidad=?, precio_unitario=? WHERE id_detalle_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detallePedido.getPedido().getId());
            ps.setInt(2, detallePedido.getProducto().getId());
            ps.setInt(3, detallePedido.getCantidad());
            ps.setDouble(4, detallePedido.getPrecioUnitario());
            ps.setInt(5, detallePedido.getId());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar detalle pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int eliminar(int id) {
        int resultado = 0;
        String sql = "DELETE FROM detalle_pedidos WHERE id_detalle_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar detalle pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public DetallePedido obtenerPorId(int id) {
        DetallePedido detalle = null;
        String sql = "SELECT dp.*, p.nombre as producto_nombre, p.precio as producto_precio " +
                     "FROM detalle_pedidos dp " +
                     "LEFT JOIN productos p ON dp.id_producto = p.id_producto " +
                     "WHERE dp.id_detalle_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Productos producto = new Productos(
                        rs.getString("producto_nombre"),
                        null, null,
                        rs.getDouble("producto_precio"),
                        null 
                    );
                    producto.setId(rs.getInt("id_producto"));

                    Pedidos pedido = new Pedidos(null, 0, null, null, null, null);
                    pedido.setId(rs.getInt("id_pedido"));

                    detalle = new DetallePedido(pedido, producto,
                        rs.getInt("cantidad"), rs.getDouble("precio_unitario"));
                    detalle.setId(rs.getInt("id_detalle_pedido"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalle pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return detalle;
    }

    @Override
    public List<DetallePedido> listarTodos() {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "SELECT dp.*, p.nombre as producto_nombre, p.precio as producto_precio " +
                     "FROM detalle_pedidos dp " +
                     "LEFT JOIN productos p ON dp.id_producto = p.id_producto " +
                     "ORDER BY dp.id_detalle_pedido";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Productos producto = new Productos(
                    rs.getString("producto_nombre"),
                    null, null,
                    rs.getDouble("producto_precio"),
                    null 
                );
                producto.setId(rs.getInt("id_producto"));

                Pedidos pedido = new Pedidos(null, 0, null, null, null, null);
                pedido.setId(rs.getInt("id_pedido"));

                DetallePedido detalle = new DetallePedido(pedido, producto,
                    rs.getInt("cantidad"), rs.getDouble("precio_unitario"));
                detalle.setId(rs.getInt("id_detalle_pedido"));
                lista.add(detalle);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar detalles pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<DetallePedido> listarPorPedido(int idPedido) {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "SELECT dp.*, p.nombre as producto_nombre, p.descripcion, p.imagen, p.precio as producto_precio, " +
                    "cat.id_categoria, cat.nombre as categoria_nombre " +
                    "FROM detalle_pedidos dp " +
                    "LEFT JOIN productos p ON dp.id_producto = p.id_producto " +
                    "LEFT JOIN categorias cat ON p.id_categoria = cat.id_categoria " +
                    "WHERE dp.id_pedido=? ORDER BY dp.id_detalle_pedido";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Categoria categoria = null;
                    if (rs.getObject("id_categoria") != null) {
                        categoria = new Categoria();
                        categoria.setId(rs.getInt("id_categoria"));
                        categoria.setNombre(rs.getString("categoria_nombre"));
                    }

                    Productos producto = new Productos(
                        rs.getString("producto_nombre"),
                        rs.getString("descripcion"),
                        rs.getString("imagen"),
                        rs.getDouble("producto_precio"),
                        categoria
                    );
                    producto.setId(rs.getInt("id_producto"));

                    Pedidos pedido = new Pedidos(null, 0, null, null, null, null);
                    pedido.setId(rs.getInt("id_pedido"));

                    DetallePedido detalle = new DetallePedido(pedido, producto,
                        rs.getInt("cantidad"), rs.getDouble("precio_unitario"));
                    detalle.setId(rs.getInt("id_detalle_pedido"));
                    lista.add(detalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar detalles por pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}