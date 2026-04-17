package com.mycompany.DAO.impl;

import com.mycompany.config.Conexion;
import com.mycompany.DAO.RolesDAO;
import com.mycompany.model.Roles;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolesDAOImpl implements RolesDAO {

    @Override
    public int insertar(Roles rol) {
        String sql = "INSERT INTO roles (rol) VALUES (?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rol.getTrol());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int actualizar(Roles rol) {
        String sql = "UPDATE roles SET rol=? WHERE id_rol=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rol.getTrol());
            ps.setInt(2, rol.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int eliminar(int id) {
        String sql = "DELETE FROM roles WHERE id_rol=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Roles obtenerPorId(int id) {
        String sql = "SELECT * FROM roles WHERE id_rol=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Roles rol = new Roles(rs.getString("rol"));
                rol.setId(rs.getInt("id_rol"));
                return rol;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Roles> listarTodos() {
        List<Roles> lista = new ArrayList<>();
        String sql = "SELECT * FROM roles";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Roles rol = new Roles(rs.getString("rol"));
                rol.setId(rs.getInt("id_rol"));
                lista.add(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
