package com.mycompany.DAO.impl;

import com.mycompany.DAO.DetalleCarritoDAO;
import com.mycompany.model.DetalleCarrito;
import com.mycompany.model.Carrito;
import com.mycompany.model.Productos;
import com.mycompany.model.Categoria;
import com.mycompany.config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleCarritoDAOImpl implements DetalleCarritoDAO {

    @Override
    public int insertar(DetalleCarrito detalleCarrito) {
        int resultado = 0;
        String sql = "INSERT INTO detalle_carrito (id_carrito, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detalleCarrito.getCarrito().getId());
            ps.setInt(2, detalleCarrito.getProducto().getId());
            ps.setInt(3, detalleCarrito.getCantidad());
            ps.setDouble(4, detalleCarrito.getPrecioUnitario());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar detalle carrito: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int actualizar(DetalleCarrito detalleCarrito) {
        int resultado = 0;
        String sql = "UPDATE detalle_carrito SET id_carrito=?, id_producto=?, cantidad=?, precio_unitario=? WHERE id_detalle_carrito=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detalleCarrito.getCarrito().getId());
            ps.setInt(2, detalleCarrito.getProducto().getId());
            ps.setInt(3, detalleCarrito.getCantidad());
            ps.setDouble(4, detalleCarrito.getPrecioUnitario());
            ps.setInt(5, detalleCarrito.getId());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar detalle carrito: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int eliminar(int id) {
        int resultado = 0;
        String sql = "DELETE FROM detalle_carrito WHERE id_detalle_carrito=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar detalle carrito: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public DetalleCarrito obtenerPorId(int id) {
        DetalleCarrito detalle = null;
        String sql = "SELECT dc.*, p.nombre as producto_nombre, p.precio as producto_precio " +
                     "FROM detalle_carrito dc " +
                     "LEFT JOIN productos p ON dc.id_producto = p.id_producto " +
                     "WHERE dc.id_detalle_carrito=?";
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

                    Carrito carrito = new Carrito(null, null, null);
                    carrito.setId(rs.getInt("id_carrito"));

                    detalle = new DetalleCarrito(carrito, producto,
                        rs.getInt("cantidad"), rs.getDouble("precio_unitario"));
                    detalle.setId(rs.getInt("id_detalle_carrito"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalle carrito: " + e.getMessage());
            e.printStackTrace();
        }
        return detalle;
    }

    @Override
    public List<DetalleCarrito> listarTodos() {
        List<DetalleCarrito> lista = new ArrayList<>();
        String sql = "SELECT dc.*, p.nombre as producto_nombre, p.precio as producto_precio " +
                     "FROM detalle_carrito dc " +
                     "LEFT JOIN productos p ON dc.id_producto = p.id_producto " +
                     "ORDER BY dc.id_detalle_carrito";
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

                Carrito carrito = new Carrito(null, null, null);
                carrito.setId(rs.getInt("id_carrito"));

                DetalleCarrito detalle = new DetalleCarrito(carrito, producto,
                    rs.getInt("cantidad"), rs.getDouble("precio_unitario"));
                detalle.setId(rs.getInt("id_detalle_carrito"));
                lista.add(detalle);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar detalles carrito: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<DetalleCarrito> listarPorCarrito(int idCarrito) {
        List<DetalleCarrito> lista = new ArrayList<>();
        String sql = "SELECT dc.*, p.nombre as producto_nombre, p.descripcion, p.imagen, p.precio as producto_precio, " +
                     "p.id_categoria, cat.nombre as categoria_nombre " +
                     "FROM detalle_carrito dc " +
                     "LEFT JOIN productos p ON dc.id_producto = p.id_producto " +
                     "LEFT JOIN categorias cat ON p.id_categoria = cat.id_categoria " +
                     "WHERE dc.id_carrito=? ORDER BY dc.id_detalle_carrito";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCarrito);
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

                    Carrito carrito = new Carrito(null, null, null);
                    carrito.setId(rs.getInt("id_carrito"));

                    DetalleCarrito detalle = new DetalleCarrito(carrito, producto,
                        rs.getInt("cantidad"), rs.getDouble("precio_unitario"));
                    detalle.setId(rs.getInt("id_detalle_carrito"));
                    lista.add(detalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar detalles por carrito: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}