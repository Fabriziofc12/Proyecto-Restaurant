package com.mycompany.DAO.impl;

import com.mycompany.config.Conexion;
import com.mycompany.DAO.DetalleCarritoDAO;
import com.mycompany.DAO.DetalleCarritoDAO;
import com.mycompany.DAO.ProductosDAO;
import com.mycompany.DAO.ProductosDAO;
import com.mycompany.model.Carrito;
import com.mycompany.model.DetalleCarrito;
import com.mycompany.model.Productos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleCarritoDAOImpl implements DetalleCarritoDAO {

    private final ProductosDAO productosDAO = new ProductoDAOImpl();

    @Override
    public int insertar(DetalleCarrito detalle) {
        String sql = "INSERT INTO detalle_carrito (id_carrito, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detalle.getCarrito().getId());
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
    public int actualizar(DetalleCarrito detalle) {
        String sql = "UPDATE detalle_carrito SET id_carrito=?, id_producto=?, cantidad=?, precio_unitario=? WHERE id_detalle_carrito=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detalle.getCarrito().getId());
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
        String sql = "DELETE FROM detalle_carrito WHERE id_detalle_carrito=?";
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
    public DetalleCarrito obtenerPorId(int id) {
        String sql = "SELECT dc.*, p.nombre, p.precio, p.descripcion, p.imagen, p.id_categoria " +
                     "FROM detalle_carrito dc " +
                     "JOIN productos p ON dc.id_producto = p.id_producto " +
                     "WHERE dc.id_detalle_carrito=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Carrito carrito = new Carrito(null, null, null);
                carrito.setId(rs.getInt("id_carrito"));
                
                Productos producto = new Productos();
                producto.setId(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setImagen(rs.getString("imagen"));
                producto.setPrecio(rs.getDouble("precio"));
                // Categoría se puede cargar completa si se necesita
                
                DetalleCarrito detalle = new DetalleCarrito(carrito, producto, 
                    rs.getInt("cantidad"), rs.getDouble("precio_unitario"));
                detalle.setId(rs.getInt("id_detalle_carrito"));
                return detalle;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DetalleCarrito> listarPorCarrito(int idCarrito) {
        List<DetalleCarrito> lista = new ArrayList<>();
        String sql = "SELECT dc.*, p.nombre, p.precio, p.descripcion, p.imagen " +
                     "FROM detalle_carrito dc " +
                     "JOIN productos p ON dc.id_producto = p.id_producto " +
                     "WHERE dc.id_carrito=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCarrito);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Carrito carrito = new Carrito(null, null, null);
                carrito.setId(rs.getInt("id_carrito"));
                
                Productos producto = new Productos();
                producto.setId(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setImagen(rs.getString("imagen"));
                producto.setPrecio(rs.getDouble("precio"));
                
                DetalleCarrito detalle = new DetalleCarrito(carrito, producto,
                    rs.getInt("cantidad"), rs.getDouble("precio_unitario"));
                detalle.setId(rs.getInt("id_detalle_carrito"));
                lista.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

