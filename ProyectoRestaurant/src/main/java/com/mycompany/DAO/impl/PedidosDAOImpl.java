package com.mycompany.DAO.impl;

import com.mycompany.config.Conexion;
import com.mycompany.DAO.EstadoPedidoDAO;
import com.mycompany.DAO.PedidosDAO;
import com.mycompany.DAO.TipoPedidoDAO;
import com.mycompany.DAO.UsuariosDAO;
import com.mycompany.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidosDAOImpl implements PedidosDAO {

    private final UsuariosDAO usuariosDAO = new UsuariosDAOImpl();
    private final EstadoPedidoDAO estadoPedidoDAO = new EstadoPedidoDAOImpl();
    private final TipoPedidoDAO tipoPedidoDAO = new TipoPedidoDAOImpl();

    @Override
    public int insertar(Pedidos pedido) {
        String sql = "INSERT INTO pedidos (id_usuario, total, fecha, direccion_entrega, id_estado_pedido, id_tipo_pedido) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pedido.getUsuario().getId());
            ps.setDouble(2, pedido.getTotal());
            ps.setDate(3, Date.valueOf(pedido.getFecha()));
            ps.setString(4, pedido.getDireccionEntrega());
            ps.setInt(5, pedido.getEstadoPedido().getId());
            ps.setInt(6, pedido.getTipoPedido().getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int actualizar(Pedidos pedido) {
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
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String sql = "DELETE FROM pedidos WHERE id_pedido=?";
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
    public Pedidos obtenerPorId(int id) {
        String sql = "SELECT p.*, u.nombre, u.correo, ep.estado as estado_nombre, tp.tipo as tipo_nombre " +
                     "FROM pedidos p " +
                     "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                     "JOIN estado_pedido ep ON p.id_estado_pedido = ep.id_estado_pedido " +
                     "JOIN tipo_pedido tp ON p.id_tipo_pedido = tp.id_tipo_pedido " +
                     "WHERE p.id_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuarios usuario = new Usuarios(
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    null, null, null
                );
                usuario.setId(rs.getInt("id_usuario"));
                
                EstadoPedido estado = new EstadoPedido(rs.getString("estado_nombre"));
                estado.setId(rs.getInt("id_estado_pedido"));
                
                TipoPedido tipo = new TipoPedido(rs.getString("tipo_nombre"));
                tipo.setId(rs.getInt("id_tipo_pedido"));
                
                Pedidos pedido = new Pedidos(usuario, rs.getDouble("total"),
                    rs.getDate("fecha").toLocalDate(), rs.getString("direccion_entrega"),
                    estado, tipo);
                pedido.setId(rs.getInt("id_pedido"));
                return pedido;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Pedidos> listarTodos() {
        List<Pedidos> lista = new ArrayList<>();
        String sql = "SELECT p.*, ep.estado, tp.tipo FROM pedidos p " +
                     "JOIN estado_pedido ep ON p.id_estado_pedido = ep.id_estado_pedido " +
                     "JOIN tipo_pedido tp ON p.id_tipo_pedido = tp.id_tipo_pedido " +
                     "ORDER BY p.fecha DESC";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuarios usuario = new Usuarios(null, null, null, null, null);
                usuario.setId(rs.getInt("id_usuario"));
                
                EstadoPedido estado = new EstadoPedido(rs.getString("estado"));
                estado.setId(rs.getInt("id_estado_pedido"));
                
                TipoPedido tipo = new TipoPedido(rs.getString("tipo"));
                tipo.setId(rs.getInt("id_tipo_pedido"));
                
                Pedidos pedido = new Pedidos(usuario, rs.getDouble("total"),
                    rs.getDate("fecha").toLocalDate(), rs.getString("direccion_entrega"),
                    estado, tipo);
                pedido.setId(rs.getInt("id_pedido"));
                lista.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Pedidos> listarPorUsuario(int idUsuario) {
        List<Pedidos> lista = new ArrayList<>();
        String sql = "SELECT p.*, ep.estado, tp.tipo FROM pedidos p " +
                     "JOIN estado_pedido ep ON p.id_estado_pedido = ep.id_estado_pedido " +
                     "JOIN tipo_pedido tp ON p.id_tipo_pedido = tp.id_tipo_pedido " +
                     "WHERE p.id_usuario=? ORDER BY p.fecha DESC";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios usuario = new Usuarios(null, null, null, null, null);
                usuario.setId(rs.getInt("id_usuario"));
                
                EstadoPedido estado = new EstadoPedido(rs.getString("estado"));
                estado.setId(rs.getInt("id_estado_pedido"));
                
                TipoPedido tipo = new TipoPedido(rs.getString("tipo"));
                tipo.setId(rs.getInt("id_tipo_pedido"));
                
                Pedidos pedido = new Pedidos(usuario, rs.getDouble("total"),
                    rs.getDate("fecha").toLocalDate(), rs.getString("direccion_entrega"),
                    estado, tipo);
                pedido.setId(rs.getInt("id_pedido"));
                lista.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

