package com.mycompany.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL  = "jdbc:postgresql://localhost:5432/restaurant_db";
    private static final String USER = "postgres";
    private static final String PASS = "Tupapideimons20";

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver PostgreSQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No se encontró el driver de PostgreSQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(URL, USER, PASS);
        System.out.println("Conexión exitosa a PostgreSQL");
        return con;
    }
}