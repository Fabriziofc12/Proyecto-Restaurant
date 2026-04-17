package com.mycompany.DAO.impl;

import com.mycompany.config.Conexion;
import com.mycompany.DAO.CarritoDAO;
import com.mycompany.DAO.EstadoCarritoDAO;
import com.mycompany.DAO.UsuariosDAO;
import com.mycompany.model.Carrito;
import com.mycompany.model.EstadoCarrito;
import com.mycompany.model.Usuarios;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAOImpl implements CarritoDAO {

    private final UsuariosDAO usuariosDAO = new UsuariosDAOImpl();
    private final EstadoCarritoDAO estadoCarritoDAO = new EstadoCarritoDAOImpl();

    @Override
    public int insertar(Carrito carrito) {
        String sql = "INSERT INTO carrito (id_usuario, id_estado, fecha_creacion) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carrito.getUsuario().getId());
            ps.setInt(2, carrito.getEstado().getId());
            ps.setTimestamp(3, Timestamp.valueOf(carrito.getFechaCreacion()));
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int actualizar(Carrito carrito) {
        String sql = "UPDATE carrito SET id_usuario=?, id_estado=?, fecha_creacion=? WHERE id_carrito=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carrito.getUsuario().getId());
            ps.setInt(2, carrito.getEstado().getId());
            ps.setTimestamp(3, Timestamp.valueOf(carrito.getFechaCreacion()));
            ps.setInt(4, carrito.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String sql = "DELETE FROM carrito WHERE id_carrito=?";
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
    public Carrito obtenerPorId(int id) {
        String sql = "SELECT c.*, u.nombre, u.correo, u.telefono, u.contrasenia, u.id_rol, " +
                     "e.estado as estado_nombre " +
                     "FROM carrito c " +
                     "JOIN usuarios u ON c.id_usuario = u.id_usuario " +
                     "JOIN estado_carrito e ON c.id_estado = e.id_estado_carrito " +
                     "WHERE c.id_carrito=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuarios usuario = new Usuarios(
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("telefono"),
                    rs.getString("contrasenia"),
                    null // Rol simplificado, se puede cargar completo si se necesita
                );
                usuario.setId(rs.getInt("id_usuario"));
                
                EstadoCarrito estado = new EstadoCarrito(rs.getString("estado_nombre"));
                estado.setId(rs.getInt("id_estado"));
                
                Carrito carrito = new Carrito(usuario, estado, rs.getTimestamp("fecha_creacion").toLocalDateTime());
                carrito.setId(rs.getInt("id_carrito"));
                return carrito;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Carrito> listarTodos() {
        List<Carrito> lista = new ArrayList<>();
        String sql = "SELECT c.*, e.estado as estado_nombre FROM carrito c " +
                     "JOIN estado_carrito e ON c.id_estado = e.id_estado_carrito";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Para listar todos, cargamos usuario simplificado
                Usuarios usuario = new Usuarios(null, null, null, null, null);
                usuario.setId(rs.getInt("id_usuario"));
                
                EstadoCarrito estado = new EstadoCarrito(rs.getString("estado_nombre"));
                estado.setId(rs.getInt("id_estado"));
                
                Carrito carrito = new Carrito(usuario, estado, rs.getTimestamp("fecha_creacion").toLocalDateTime());
                carrito.setId(rs.getInt("id_carrito"));
                lista.add(carrito);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Carrito obtenerCarritoActivoPorUsuario(int idUsuario) {
        String sql = "SELECT c.*, e.estado as estado_nombre FROM carrito c " +
                     "JOIN estado_carrito e ON c.id_estado = e.id_estado_carrito " +
                     "WHERE c.id_usuario=? AND e.estado='ACTIVO'";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuarios usuario = new Usuarios(null, null, null, null, null);
                usuario.setId(rs.getInt("id_usuario"));
                
                EstadoCarrito estado = new EstadoCarrito(rs.getString("estado_nombre"));
                estado.setId(rs.getInt("id_estado"));
                
                Carrito carrito = new Carrito(usuario, estado, rs.getTimestamp("fecha_creacion").toLocalDateTime());
                carrito.setId(rs.getInt("id_carrito"));
                return carrito;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

