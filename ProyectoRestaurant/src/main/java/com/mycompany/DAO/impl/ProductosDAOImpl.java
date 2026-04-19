package com.mycompany.DAO.impl;

import com.mycompany.DAO.ProductosDAO;
import com.mycompany.model.Productos;
import com.mycompany.model.Categoria;
import com.mycompany.config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAOImpl implements ProductosDAO {

    @Override
    public int insertar(Productos producto) {
        int resultado = 0;
        String sql = "INSERT INTO productos (nombre, descripcion, imagen, precio, id_categoria) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setString(3, producto.getImagen());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getCategoria().getId());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int actualizar(Productos producto) {
        int resultado = 0;
        String sql = "UPDATE productos SET nombre=?, descripcion=?, imagen=?, precio=?, id_categoria=? WHERE id_producto=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.println("=== DAO ACTUALIZAR ===");
            System.out.println("id:     " + producto.getId());
            System.out.println("nombre: " + producto.getNombre());
            System.out.println("desc:   " + producto.getDescripcion());
            System.out.println("precio: " + producto.getPrecio());
            System.out.println("idCat:  " + producto.getCategoria().getId());

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setString(3, producto.getImagen());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getCategoria().getId());
            ps.setInt(6, producto.getId());
            resultado = ps.executeUpdate();

            // DEBUG
            System.out.println("Filas afectadas: " + resultado);
            System.out.println("======================");

        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int eliminar(int id) {
        int resultado = 0;
        String sql = "DELETE FROM productos WHERE id_producto=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public Productos obtenerPorId(int id) {
        Productos producto = null;
        String sql = "SELECT p.*, c.nombre as categoria_nombre FROM productos p " +
                     "LEFT JOIN categorias c ON p.id_categoria = c.id_categoria " +
                     "WHERE p.id_producto=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // ✅ Categoria singular + constructor vacío + setters
                    Categoria categoria = null;
                    if (rs.getObject("id_categoria") != null) {
                        categoria = new Categoria();
                        categoria.setId(rs.getInt("id_categoria"));
                        categoria.setNombre(rs.getString("categoria_nombre"));
                    }
                    producto = new Productos(
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("imagen"),
                        rs.getDouble("precio"),
                        categoria
                    );
                    producto.setId(rs.getInt("id_producto"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener producto: " + e.getMessage());
            e.printStackTrace();
        }
        return producto;
    }

    @Override
    public List<Productos> listarTodos() {
        List<Productos> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre as categoria_nombre FROM productos p " +
                     "LEFT JOIN categorias c ON p.id_categoria = c.id_categoria " +
                     "ORDER BY p.id_producto";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // ✅ Categoria singular + constructor vacío + setters
                Categoria categoria = null;
                if (rs.getObject("id_categoria") != null) {
                    categoria = new Categoria();
                    categoria.setId(rs.getInt("id_categoria"));
                    categoria.setNombre(rs.getString("categoria_nombre"));
                }
                Productos producto = new Productos(
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("imagen"),
                    rs.getDouble("precio"),
                    categoria
                );
                producto.setId(rs.getInt("id_producto"));
                lista.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Productos> listarPorCategoria(int idCategoria) {
        List<Productos> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre as categoria_nombre FROM productos p " +
                     "LEFT JOIN categorias c ON p.id_categoria = c.id_categoria " +
                     "WHERE p.id_categoria=? ORDER BY p.id_producto";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // ✅ Categoria singular + constructor vacío + setters
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("id_categoria"));
                    categoria.setNombre(rs.getString("categoria_nombre"));
                    Productos producto = new Productos(
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("imagen"),
                        rs.getDouble("precio"),
                        categoria
                    );
                    producto.setId(rs.getInt("id_producto"));
                    lista.add(producto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos por categoría: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}