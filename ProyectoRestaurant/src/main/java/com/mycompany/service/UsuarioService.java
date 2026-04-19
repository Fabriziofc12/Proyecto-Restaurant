package com.mycompany.service;

import com.mycompany.DAO.UsuariosDAO;
import com.mycompany.DAO.impl.UsuariosDAOImpl;
import com.mycompany.model.Roles;
import com.mycompany.model.Usuarios;

import java.util.List;

public class UsuarioService {

    private final UsuariosDAO dao;

    public UsuarioService() {
        this.dao = new UsuariosDAOImpl();
    }

    public Usuarios autenticar(String correo, String contrasenia) {
        return dao.autenticar(correo, contrasenia);
    }

    
    public boolean registrar(String nombre, String correo,
                              String telefono, String contrasenia) {

        if (dao.obtenerPorCorreo(correo) != null) {
            return false;
        }
        Roles rolCliente = new Roles("CLIENTE");
        rolCliente.setId(2);
        Usuarios u = new Usuarios(nombre, correo, telefono, contrasenia, rolCliente);
        return dao.insertar(u) > 0;
    }

    /** Devuelve todos los usuarios (solo para el rol ADMIN) */
    public List<Usuarios> listarTodos() {
        return dao.listarTodos();
    }

    /** Busca un usuario por su ID */
    public Usuarios obtenerPorId(int id) {
        return dao.obtenerPorId(id);
    }

    /** Busca un usuario por su correo electrónico */
    public Usuarios obtenerPorCorreo(String correo) {
        return dao.obtenerPorCorreo(correo);
    }

    /** Actualiza los datos de un usuario existente */
    public void actualizar(Usuarios usuario) {
        dao.actualizar(usuario);
    }

    /** Elimina un usuario por su ID */
    public void eliminar(int id) {
        dao.eliminar(id);
    }
}