package com.mycompany.DAO.impl;

import com.mycompany.config.Conexion;
import com.mycompany.DAO.CategoriasDAO;
import com.mycompany.model.Categorias;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriasDAOImpl implements CategoriasDAO {

    @Override
    public int insertar(Categorias categoria) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int actualizar(Categorias categoria) {
        String sql = "UPDATE categorias SET nombre=? WHERE id_categoria=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setInt(2, categoria.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String sql = "DELETE FROM categorias WHERE id_categoria=?";
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
    public Categorias obtenerPorId(int id) {
        String sql = "SELECT * FROM categorias WHERE id_categoria=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Categorias cat = new Categorias(rs.getString("nombre"));
                cat.setId(rs.getInt("id_categoria"));
                return cat;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Categorias> listarTodos() {
        List<Categorias> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categorias cat = new Categorias(rs.getString("nombre"));
                cat.setId(rs.getInt("id_categoria"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }  

}
