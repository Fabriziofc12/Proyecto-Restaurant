/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Fabrizio
 */
public class Conexion {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/restaurant_db";
    private static final String USER = "postgres";
    private static final String PASS = "admin";

    public static Connection getConnection() {
        java.sql.Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("¡ERROR: No se encontró el Driver de PostgreSQL!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("¡ERROR: Falló la conexión (Usuario, Password o DB incorrectos)!");
            e.printStackTrace();
        }
        return con;
    }
    
}
