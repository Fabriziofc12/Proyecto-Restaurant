package com.mycompany.DAO.impl;

import com.mycompany.config.Conexion;
import com.mycompany.DAO.CategoriasDAO;
import com.mycompany.model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriasDAOImpl implements CategoriasDAO {

    @Override
    public int insertar(Categoria categoria) {
        int resultado = 0;
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar categoría: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int actualizar(Categoria categoria) {
        int resultado = 0;
        String sql = "UPDATE categorias SET nombre=? WHERE id_categoria=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setInt(2, categoria.getId());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar categoría: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int eliminar(int id) {
        int resultado = 0;
        String sql = "DELETE FROM categorias WHERE id_categoria=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar categoría: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public Categoria obtenerPorId(int id) {
        Categoria categoria = null;
        String sql = "SELECT * FROM categorias WHERE id_categoria=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    categoria = new Categoria();
                    categoria.setId(rs.getInt("id_categoria"));
                    categoria.setNombre(rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener categoría: " + e.getMessage());
            e.printStackTrace();
        }
        return categoria;
    }

    @Override
    public List<Categoria> listarTodos() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias ORDER BY id_categoria";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id_categoria"));
                categoria.setNombre(rs.getString("nombre"));
                lista.add(categoria);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar categorías: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}