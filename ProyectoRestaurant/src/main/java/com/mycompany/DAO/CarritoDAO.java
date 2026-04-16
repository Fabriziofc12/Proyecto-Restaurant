/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.DAO;

import com.mycompany.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Fabrizio
 */
public class CarritoDAO {
    public Integer obtenerCarritoActivo(int usuarioId) {
        String sql = "SELECT id_carrito FROM carrito WHERE id_usuario = ? AND id_estado_carrito = 1";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_carrito");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
