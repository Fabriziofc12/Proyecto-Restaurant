package com.mycompany.DAO.impl;

import com.mycompany.config.Conexion;
import com.mycompany.DAO.EstadoCarritoDAO;
import com.mycompany.model.EstadoCarrito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoCarritoDAOImpl implements EstadoCarritoDAO {

    @Override
    public int insertar(EstadoCarrito estado) {
        String sql = "INSERT INTO estado_carrito (estado) VALUES (?)";
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
    public int actualizar(EstadoCarrito estado) {
        String sql = "UPDATE estado_carrito SET estado=? WHERE id_estado_carrito=?";
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
        String sql = "DELETE FROM estado_carrito WHERE id_estado_carrito=?";
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
    public EstadoCarrito obtenerPorId(int id) {
        String sql = "SELECT * FROM estado_carrito WHERE id_estado_carrito=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                EstadoCarrito estado = new EstadoCarrito(rs.getString("estado"));
                estado.setId(rs.getInt("id_estado_carrito"));
                return estado;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<EstadoCarrito> listarTodos() {
        List<EstadoCarrito> lista = new ArrayList<>();
        String sql = "SELECT * FROM estado_carrito";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EstadoCarrito estado = new EstadoCarrito(rs.getString("estado"));
                estado.setId(rs.getInt("id_estado_carrito"));
                lista.add(estado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}