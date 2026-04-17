package com.mycompany.DAO.impl;

import com.mycompany.config.Conexion;

import com.mycompany.DAO.CategoriasDAO;
import com.mycompany.DAO.ProductosDAO;
import com.mycompany.model.Categorias;
import com.mycompany.model.Productos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductosDAO{
    private final CategoriasDAO categoriasDAO = new CategoriasDAOImpl();

    @Override
    public int insertar(Productos producto) {
        String sql = "INSERT INTO productos (nombre, descripcion, imagen, precio, id_categoria) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setString(3, producto.getImagen());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getCategoria().getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int actualizar(Productos producto) {
        String sql = "UPDATE productos SET nombre=?, descripcion=?, imagen=?, precio=?, id_categoria=? WHERE id_producto=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setString(3, producto.getImagen());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getCategoria().getId());
            ps.setInt(6, producto.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id_producto=?";
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
    public Productos obtenerPorId(int id) {
        String sql = "SELECT p.*, c.nombre as cat_nombre FROM productos p " +
                     "JOIN categorias c ON p.id_categoria = c.id_categoria WHERE p.id_producto=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Categorias cat = new Categorias(rs.getString("cat_nombre"));
                cat.setId(rs.getInt("id_categoria"));
                
                Productos prod = new Productos();
                prod.setId(rs.getInt("id_producto"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setImagen(rs.getString("imagen"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setCategoria(cat);
                return prod;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Productos> listarTodos() {
        List<Productos> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre as cat_nombre FROM productos p " +
                     "JOIN categorias c ON p.id_categoria = c.id_categoria";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categorias cat = new Categorias(rs.getString("cat_nombre"));
                cat.setId(rs.getInt("id_categoria"));
                
                Productos prod = new Productos();
                prod.setId(rs.getInt("id_producto"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setImagen(rs.getString("imagen"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setCategoria(cat);
                lista.add(prod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Productos> listarPorCategoria(int idCategoria) {
        List<Productos> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre as cat_nombre FROM productos p " +
                     "JOIN categorias c ON p.id_categoria = c.id_categoria WHERE p.id_categoria=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categorias cat = new Categorias(rs.getString("cat_nombre"));
                cat.setId(rs.getInt("id_categoria"));
                
                Productos prod = new Productos();
                prod.setId(rs.getInt("id_producto"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setImagen(rs.getString("imagen"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setCategoria(cat);
                lista.add(prod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

