package com.mycompany.DAO.impl;

import com.mycompany.DAO.PedidosDAO;
import com.mycompany.model.Pedidos;
import com.mycompany.model.Usuarios;
import com.mycompany.model.EstadoPedido;
import com.mycompany.model.TipoPedido;
import com.mycompany.config.Conexion;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidosDAOImpl implements PedidosDAO {

    @Override
    public int insertar(Pedidos pedido) {
        int resultado = 0;
        String sql = "INSERT INTO pedidos (id_usuario, total, fecha, direccion_entrega, id_estado_pedido, id_tipo_pedido) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pedido.getUsuario().getId());
            ps.setDouble(2, pedido.getTotal());
            ps.setDate(3, Date.valueOf(pedido.getFecha()));
            ps.setString(4, pedido.getDireccionEntrega());
            ps.setInt(5, pedido.getEstadoPedido().getId());
            ps.setInt(6, pedido.getTipoPedido().getId());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int actualizar(Pedidos pedido) {
        int resultado = 0;
        String sql = "UPDATE pedidos SET id_usuario=?, total=?, fecha=?, direccion_entrega=?, id_estado_pedido=?, id_tipo_pedido=? WHERE id_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pedido.getUsuario().getId());
            ps.setDouble(2, pedido.getTotal());
            ps.setDate(3, Date.valueOf(pedido.getFecha()));
            ps.setString(4, pedido.getDireccionEntrega());
            ps.setInt(5, pedido.getEstadoPedido().getId());
            ps.setInt(6, pedido.getTipoPedido().getId());
            ps.setInt(7, pedido.getId());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public int eliminar(int id) {
        int resultado = 0;
        String sql = "DELETE FROM pedidos WHERE id_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public Pedidos obtenerPorId(int id) {
        Pedidos pedido = null;
        String sql = "SELECT ped.*, u.nombre as usuario_nombre, ep.estado as estado_pedido, tp.tipo " +
                    "FROM pedidos ped " +
                    "LEFT JOIN usuarios u ON ped.id_usuario = u.id_usuario " +
                    "LEFT JOIN estado_pedido ep ON ped.id_estado_pedido = ep.id_estado_pedido " +
                    "LEFT JOIN tipo_pedido tp ON ped.id_tipo_pedido = tp.id_tipo_pedido " +
                    "WHERE ped.id_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuarios usuario = new Usuarios(
                        rs.getString("usuario_nombre"),
                        null, null, null, null
                    );
                    usuario.setId(rs.getInt("id_usuario"));
                    
                    EstadoPedido estado = new EstadoPedido(rs.getString("estado_pedido"));
                    estado.setId(rs.getInt("id_estado_pedido"));
                    
                    TipoPedido tipo = new TipoPedido(rs.getString("tipo"));
                    tipo.setId(rs.getInt("id_tipo_pedido"));
                    
                    pedido = new Pedidos(
                        usuario,
                        rs.getDouble("total"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("direccion_entrega"),
                        estado,
                        tipo
                    );
                    pedido.setId(rs.getInt("id_pedido"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pedido: " + e.getMessage());
            e.printStackTrace();
        }
        return pedido;
    }

    @Override
    public List<Pedidos> listarTodos() {
        List<Pedidos> lista = new ArrayList<>();
        String sql = "SELECT p.*, u.nombre as usuario_nombre, ep.estado as estado_pedido, tp.tipo " +
                     "FROM pedidos p " +
                     "LEFT JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                     "LEFT JOIN estado_pedido ep ON p.id_estado_pedido = ep.id_estado_pedido " +
                     "LEFT JOIN tipo_pedido tp ON p.id_tipo_pedido = tp.id_tipo_pedido " +
                     "ORDER BY p.id_pedido DESC";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuarios usuario = new Usuarios(
                    rs.getString("usuario_nombre"),
                    null, null, null, null
                );
                usuario.setId(rs.getInt("id_usuario"));
                
                EstadoPedido estado = new EstadoPedido(rs.getString("estado_pedido"));
                estado.setId(rs.getInt("id_estado_pedido"));
                
                TipoPedido tipo = new TipoPedido(rs.getString("tipo"));
                tipo.setId(rs.getInt("id_tipo_pedido"));
                
                Pedidos pedido = new Pedidos(
                    usuario,
                    rs.getDouble("total"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getString("direccion_entrega"),
                    estado,
                    tipo
                );
                pedido.setId(rs.getInt("id_pedido"));
                lista.add(pedido);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Pedidos> listarPorUsuario(int idUsuario) {
        List<Pedidos> lista = new ArrayList<>();
        String sql = "SELECT ped.*, u.nombre as usuario_nombre, ep.estado as estado_pedido, tp.tipo " +
                    "FROM pedidos ped " +
                    "LEFT JOIN usuarios u ON ped.id_usuario = u.id_usuario " +
                    "LEFT JOIN estado_pedido ep ON ped.id_estado_pedido = ep.id_estado_pedido " +
                    "LEFT JOIN tipo_pedido tp ON ped.id_tipo_pedido = tp.id_tipo_pedido " +
                    "WHERE ped.id_usuario=? ORDER BY ped.fecha DESC";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuarios usuario = new Usuarios(
                        rs.getString("usuario_nombre"),
                        null, null, null, null
                    );
                    usuario.setId(rs.getInt("id_usuario"));
                    
                    EstadoPedido estado = new EstadoPedido(rs.getString("estado_pedido"));
                    estado.setId(rs.getInt("id_estado_pedido"));
                    
                    TipoPedido tipo = new TipoPedido(rs.getString("tipo"));
                    tipo.setId(rs.getInt("id_tipo_pedido"));
                    
                    Pedidos pedido = new Pedidos(
                        usuario,
                        rs.getDouble("total"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("direccion_entrega"),
                        estado,
                        tipo
                    );
                    pedido.setId(rs.getInt("id_pedido"));
                    lista.add(pedido);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pedidos por usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Pedidos> listarPorEstado(int idEstadoPedido) {
        List<Pedidos> lista = new ArrayList<>();
        String sql = "SELECT p.*, u.nombre as usuario_nombre, ep.estado as estado_pedido, tp.tipo " +
                     "FROM pedidos p " +
                     "LEFT JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                     "LEFT JOIN estado_pedido ep ON p.id_estado_pedido = ep.id_estado_pedido " +
                     "LEFT JOIN tipo_pedido tp ON p.id_tipo_pedido = tp.id_tipo_pedido " +
                     "WHERE p.id_estado_pedido=? ORDER BY p.fecha DESC";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEstadoPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuarios usuario = new Usuarios(
                        rs.getString("usuario_nombre"),
                        null, null, null, null
                    );
                    usuario.setId(rs.getInt("id_usuario"));
                    
                    EstadoPedido estado = new EstadoPedido(rs.getString("estado_pedido"));
                    estado.setId(rs.getInt("id_estado_pedido"));
                    
                    TipoPedido tipo = new TipoPedido(rs.getString("tipo"));
                    tipo.setId(rs.getInt("id_tipo_pedido"));
                    
                    Pedidos pedido = new Pedidos(
                        usuario,
                        rs.getDouble("total"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("direccion_entrega"),
                        estado,
                        tipo
                    );
                    pedido.setId(rs.getInt("id_pedido"));
                    lista.add(pedido);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pedidos por estado: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}