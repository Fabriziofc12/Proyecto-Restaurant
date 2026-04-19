package com.mycompany.DAO.impl;

import com.mycompany.DAO.CarritoDAO;
import com.mycompany.model.Carrito;
import com.mycompany.model.Usuarios;
import com.mycompany.model.EstadoCarrito;
import com.mycompany.config.Conexion;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAOImpl implements CarritoDAO {

    @Override
    public int insertar(Carrito carrito) {
        int resultado = 0;
        String sql = "INSERT INTO carrito (id_usuario, id_estado, fecha_creacion) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carrito.getUsuario().getId());
            ps.setInt(2, carrito.getEstado().getId());
            ps.setTimestamp(3, Timestamp.valueOf(carrito.getFechaCreacion()));
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar carrito: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int actualizar(Carrito carrito) {
        int resultado = 0;
        String sql = "UPDATE carrito SET id_usuario=?, id_estado=?, fecha_creacion=? WHERE id_carrito=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carrito.getUsuario().getId());
            ps.setInt(2, carrito.getEstado().getId());
            ps.setTimestamp(3, Timestamp.valueOf(carrito.getFechaCreacion()));
            ps.setInt(4, carrito.getId());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar carrito: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int eliminar(int id) {
        int resultado = 0;
        String sql = "DELETE FROM carrito WHERE id_carrito=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar carrito: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public Carrito obtenerPorId(int id) {
        Carrito carrito = null;
        String sql = "SELECT c.*, u.nombre as usuario_nombre, u.correo, ec.estado " +
                     "FROM carrito c " +
                     "LEFT JOIN usuarios u ON c.id_usuario = u.id_usuario " +
                     "LEFT JOIN estado_carrito ec ON c.id_estado = ec.id_estado_carrito " +
                     "WHERE c.id_carrito=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuarios usuario = new Usuarios(
                        rs.getString("usuario_nombre"),
                        rs.getString("correo"),
                        null, 
                        null,
                        null  
                    );
                    usuario.setId(rs.getInt("id_usuario"));
                    
                    EstadoCarrito estado = new EstadoCarrito(rs.getString("estado"));
                    estado.setId(rs.getInt("id_estado"));
                    
                    carrito = new Carrito(usuario, estado, 
                        rs.getTimestamp("fecha_creacion").toLocalDateTime());
                    carrito.setId(rs.getInt("id_carrito"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener carrito: " + e.getMessage());
            e.printStackTrace();
        }
        return carrito;
    }

    @Override
    public List<Carrito> listarTodos() {
        List<Carrito> lista = new ArrayList<>();
        String sql = "SELECT c.*, u.nombre as usuario_nombre, u.correo, ec.estado " +
                     "FROM carrito c " +
                     "LEFT JOIN usuarios u ON c.id_usuario = u.id_usuario " +
                     "LEFT JOIN estado_carrito ec ON c.id_estado = ec.id_estado_carrito " +
                     "ORDER BY c.id_carrito";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuarios usuario = new Usuarios(
                    rs.getString("usuario_nombre"),
                    rs.getString("correo"),
                    null, null, null
                );
                usuario.setId(rs.getInt("id_usuario"));
                
                EstadoCarrito estado = new EstadoCarrito(rs.getString("estado"));
                estado.setId(rs.getInt("id_estado"));
                
                Carrito carrito = new Carrito(usuario, estado, 
                    rs.getTimestamp("fecha_creacion").toLocalDateTime());
                carrito.setId(rs.getInt("id_carrito"));
                lista.add(carrito);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar carritos: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Carrito obtenerCarritoActivoPorUsuario(int idUsuario) {
        Carrito carrito = null;
        String sql = "SELECT c.*, u.nombre as usuario_nombre, u.correo, ec.estado " +
                     "FROM carrito c " +
                     "LEFT JOIN usuarios u ON c.id_usuario = u.id_usuario " +
                     "LEFT JOIN estado_carrito ec ON c.id_estado = ec.id_estado_carrito " +
                     "WHERE c.id_usuario=? AND ec.estado='ACTIVO'";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuarios usuario = new Usuarios(
                        rs.getString("usuario_nombre"),
                        rs.getString("correo"),
                        null, null, null
                    );
                    usuario.setId(rs.getInt("id_usuario"));
                    
                    EstadoCarrito estado = new EstadoCarrito(rs.getString("estado"));
                    estado.setId(rs.getInt("id_estado"));
                    
                    carrito = new Carrito(usuario, estado, 
                        rs.getTimestamp("fecha_creacion").toLocalDateTime());
                    carrito.setId(rs.getInt("id_carrito"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener carrito activo: " + e.getMessage());
            e.printStackTrace();
        }
        return carrito;
    }

    @Override
    public List<Carrito> listarPorUsuario(int idUsuario) {
        List<Carrito> lista = new ArrayList<>();
        String sql = "SELECT c.*, u.nombre as usuario_nombre, u.correo, ec.estado " +
                     "FROM carrito c " +
                     "LEFT JOIN usuarios u ON c.id_usuario = u.id_usuario " +
                     "LEFT JOIN estado_carrito ec ON c.id_estado = ec.id_estado_carrito " +
                     "WHERE c.id_usuario=? ORDER BY c.fecha_creacion DESC";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuarios usuario = new Usuarios(
                        rs.getString("usuario_nombre"),
                        rs.getString("correo"),
                        null, null, null
                    );
                    usuario.setId(rs.getInt("id_usuario"));
                    
                    EstadoCarrito estado = new EstadoCarrito(rs.getString("estado"));
                    estado.setId(rs.getInt("id_estado"));
                    
                    Carrito carrito = new Carrito(usuario, estado, 
                        rs.getTimestamp("fecha_creacion").toLocalDateTime());
                    carrito.setId(rs.getInt("id_carrito"));
                    lista.add(carrito);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar carritos por usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}