package com.mycompany.DAO.impl;

import com.mycompany.DAO.UsuariosDAO;
import com.mycompany.model.Usuarios;
import com.mycompany.model.Roles;
import com.mycompany.config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAOImpl implements UsuariosDAO {

    @Override
    public int insertar(Usuarios usuario) {
        int resultado = 0;
        String sql = "INSERT INTO usuarios (nombre, correo, telefono, contrasenia, id_rol) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getContrasenia());
            ps.setInt(5, usuario.getRol().getId());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int actualizar(Usuarios usuario) {
        int resultado = 0;
        String sql = "UPDATE usuarios SET nombre=?, correo=?, telefono=?, contrasenia=?, id_rol=? WHERE id_usuario=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getContrasenia());
            ps.setInt(5, usuario.getRol().getId());
            ps.setInt(6, usuario.getId());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int eliminar(int id) {
        int resultado = 0;
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public Usuarios obtenerPorId(int id) {
        Usuarios usuario = null;
        String sql = "SELECT u.*, r.rol FROM usuarios u " +
                     "LEFT JOIN roles r ON u.id_rol = r.id_rol " +
                     "WHERE u.id_usuario=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Roles rol = new Roles(rs.getString("rol"));
                    rol.setId(rs.getInt("id_rol"));
                    
                    usuario = new Usuarios(
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("contrasenia"),
                        rol
                    );
                    usuario.setId(rs.getInt("id_usuario"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public List<Usuarios> listarTodos() {
        List<Usuarios> lista = new ArrayList<>();
        String sql = "SELECT u.*, r.rol FROM usuarios u " +
                     "LEFT JOIN roles r ON u.id_rol = r.id_rol " +
                     "ORDER BY u.id_usuario";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Roles rol = new Roles(rs.getString("rol"));
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
            System.err.println("Error al listar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Usuarios obtenerPorCorreo(String correo) {
        Usuarios usuario = null;
        String sql = "SELECT u.*, r.rol FROM usuarios u " +
                     "LEFT JOIN roles r ON u.id_rol = r.id_rol " +
                     "WHERE u.correo=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Roles rol = new Roles(rs.getString("rol"));
                    rol.setId(rs.getInt("id_rol"));
                    
                    usuario = new Usuarios(
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("contrasenia"),
                        rol
                    );
                    usuario.setId(rs.getInt("id_usuario"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario por correo: " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public Usuarios autenticar(String correo, String contrasenia) {
        Usuarios usuario = null;
        String sql = "SELECT u.*, r.rol FROM usuarios u " +
                     "LEFT JOIN roles r ON u.id_rol = r.id_rol " +
                     "WHERE u.correo=? AND u.contrasenia=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, contrasenia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Roles rol = new Roles(rs.getString("rol"));
                    rol.setId(rs.getInt("id_rol"));
                    
                    usuario = new Usuarios(
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("contrasenia"),
                        rol
                    );
                    usuario.setId(rs.getInt("id_usuario"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }
}