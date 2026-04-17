package com.mycompany.DAO.impl;

import com.mycompany.config.Conexion;
import com.mycompany.DAO.DireccionesDAO;
import com.mycompany.model.Direcciones;
import com.mycompany.model.Usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DireccionesDAOImpl implements DireccionesDAO {

    @Override
    public int insertar(Direcciones direccion) {
        String sql = "INSERT INTO direcciones (id_usuario, direccion, principal) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, direccion.getUsuario().getId());
            ps.setString(2, direccion.getDireccion());
            ps.setBoolean(3, direccion.isPrincipal());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int actualizar(Direcciones direccion) {
        String sql = "UPDATE direcciones SET id_usuario=?, direccion=?, principal=? WHERE id_direccion=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, direccion.getUsuario().getId());
            ps.setString(2, direccion.getDireccion());
            ps.setBoolean(3, direccion.isPrincipal());
            ps.setInt(4, direccion.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String sql = "DELETE FROM direcciones WHERE id_direccion=?";
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
    public Direcciones obtenerPorId(int id) {
        String sql = "SELECT * FROM direcciones WHERE id_direccion=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuarios usuario = new Usuarios(null, null, null, null, null);
                usuario.setId(rs.getInt("id_usuario"));
                
                Direcciones direccion = new Direcciones(usuario,
                    rs.getString("direccion"), rs.getBoolean("principal"));
                direccion.setId(rs.getInt("id_direccion"));
                return direccion;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Direcciones> listarPorUsuario(int idUsuario) {
        List<Direcciones> lista = new ArrayList<>();
        String sql = "SELECT * FROM direcciones WHERE id_usuario=? ORDER BY principal DESC";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios usuario = new Usuarios(null, null, null, null, null);
                usuario.setId(rs.getInt("id_usuario"));
                
                Direcciones direccion = new Direcciones(usuario,
                    rs.getString("direccion"), rs.getBoolean("principal"));
                direccion.setId(rs.getInt("id_direccion"));
                lista.add(direccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Direcciones obtenerPrincipalPorUsuario(int idUsuario) {
        String sql = "SELECT * FROM direcciones WHERE id_usuario=? AND principal=true";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuarios usuario = new Usuarios(null, null, null, null, null);
                usuario.setId(rs.getInt("id_usuario"));
                
                Direcciones direccion = new Direcciones(usuario,
                    rs.getString("direccion"), rs.getBoolean("principal"));
                direccion.setId(rs.getInt("id_direccion"));
                return direccion;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
