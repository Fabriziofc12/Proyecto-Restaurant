package com.mycompany.DAO;

import com.mycompany.model.Usuarios;
import java.util.List;

public interface UsuariosDAO{
    int insertar(Usuarios usuario);
    int actualizar(Usuarios usuario);
    int eliminar(int id);
    Usuarios obtenerPorId(int id);
    Usuarios obtenerPorCorreo(String correo);
    Usuarios autenticar(String correo, String contrasenia);
    List<Usuarios> listarTodos();
}