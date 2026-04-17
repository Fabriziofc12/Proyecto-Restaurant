package com.mycompany.DAO.impl;

import com.mycompany.config.Conexion;
import com.mycompany.DAO.TipoPedidoDAO;
import com.mycompany.model.TipoPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoPedidoDAOImpl implements TipoPedidoDAO {

    @Override
    public int insertar(TipoPedido tipo) {
        String sql = "INSERT INTO tipo_pedido (tipo) VALUES (?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tipo.getTipo());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int actualizar(TipoPedido tipo) {
        String sql = "UPDATE tipo_pedido SET tipo=? WHERE id_tipo_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tipo.getTipo());
            ps.setInt(2, tipo.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int eliminar(int id) {
        String sql = "DELETE FROM tipo_pedido WHERE id_tipo_pedido=?";
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
    public TipoPedido obtenerPorId(int id) {
        String sql = "SELECT * FROM tipo_pedido WHERE id_tipo_pedido=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TipoPedido tipo = new TipoPedido(rs.getString("tipo"));
                tipo.setId(rs.getInt("id_tipo_pedido"));
                return tipo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TipoPedido> listarTodos() {
        List<TipoPedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_pedido";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TipoPedido tipo = new TipoPedido(rs.getString("tipo"));
                tipo.setId(rs.getInt("id_tipo_pedido"));
                lista.add(tipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

