package com.mycompany.DAO.impl;

import com.mycompany.config.Conexion;
import com.mycompany.DAO.RolesDAO;
import com.mycompany.DAO.UsuariosDAO;
import com.mycompany.model.Roles;
import com.mycompany.model.Usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAOImpl implements UsuariosDAO{
    private final RolesDAO rolesDAO = new RolesDAOImpl();

    @Override
    public int insertar(Usuarios usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, telefono, contrasenia, id_rol) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getContrasenia());
            ps.setInt(5, usuario.getRol().getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int actualizar(Usuarios usuario) {
        String sql = "UPDATE usuarios SET nombre=?, correo=?, telefono=?, contrasenia=?, id_rol=? WHERE id_usuario=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getContrasenia());
            ps.setInt(5, usuario.getRol().getId());
            ps.setInt(6, usuario.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";
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
    public Usuarios obtenerPorId(int id) {
        String sql = "SELECT u.*, r.rol as nombre_rol FROM usuarios u " +
                     "JOIN roles r ON u.id_rol = r.id_rol WHERE u.id_usuario=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Roles rol = new Roles(rs.getString("nombre_rol"));
                rol.setId(rs.getInt("id_rol"));
                
                Usuarios usuario = new Usuarios(
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("telefono"),
                    rs.getString("contrasenia"),
                    rol
                );
                usuario.setId(rs.getInt("id_usuario"));
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Usuarios> listarTodos() {
        List<Usuarios> lista = new ArrayList<>();
        String sql = "SELECT u.*, r.rol as nombre_rol FROM usuarios u JOIN roles r ON u.id_rol = r.id_rol";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Roles rol = new Roles(rs.getString("nombre_rol"));
                rol.setId(rs.getInt("id_rol"));
                
                Usuarios usuario = new Usuarios(
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("telefono"),
                    rs.getString("contrasenia"),
                    rol
                );
                usuario.setId(rs.getInt("id_usuario"));
                lista.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Usuarios obtenerPorCorreo(String correo) {
        String sql = "SELECT u.*, r.rol as nombre_rol FROM usuarios u " +
                     "JOIN roles r ON u.id_rol = r.id_rol WHERE u.correo=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Roles rol = new Roles(rs.getString("nombre_rol"));
                rol.setId(rs.getInt("id_rol"));
                
                Usuarios usuario = new Usuarios(
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("telefono"),
                    rs.getString("contrasenia"),
                    rol
                );
                usuario.setId(rs.getInt("id_usuario"));
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Usuarios autenticar(String correo, String contrasenia) {
        String sql = "SELECT u.*, r.rol as nombre_rol FROM usuarios u " +
                     "JOIN roles r ON u.id_rol = r.id_rol WHERE u.correo=? AND u.contrasenia=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, contrasenia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Roles rol = new Roles(rs.getString("nombre_rol"));
                rol.setId(rs.getInt("id_rol"));
                
                Usuarios usuario = new Usuarios(
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("telefono"),
                    rs.getString("contrasenia"),
                    rol
                );
                usuario.setId(rs.getInt("id_usuario"));
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
