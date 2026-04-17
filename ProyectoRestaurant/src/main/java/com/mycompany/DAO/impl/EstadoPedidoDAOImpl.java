package com.mycompany.DAO.impl;

import com.mycompany.config.Conexion;
import com.mycompany.DAO.EstadoPedidoDAO;
import com.mycompany.model.EstadoPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoPedidoDAOImpl implements EstadoPedidoDAO {

    @Override
    public int insertar(EstadoPedido estado) {
        String sql = "INSERT INTO estado_pedido (estado) VALUES (?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado.getEstado());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int actualizar(EstadoPedido estado) {
        String sql = "UPDATE estado_pedido SET estado=? WHERE id_estado_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado.getEstado());
            ps.setInt(2, estado.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String sql = "DELETE FROM estado_pedido WHERE id_estado_pedido=?";
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
    public EstadoPedido obtenerPorId(int id) {
        String sql = "SELECT * FROM estado_pedido WHERE id_estado_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                EstadoPedido estado = new EstadoPedido(rs.getString("estado"));
                estado.setId(rs.getInt("id_estado_pedido"));
                return estado;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<EstadoPedido> listarTodos() {
        List<EstadoPedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM estado_pedido";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EstadoPedido estado = new EstadoPedido(rs.getString("estado"));
                estado.setId(rs.getInt("id_estado_pedido"));
                lista.add(estado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

