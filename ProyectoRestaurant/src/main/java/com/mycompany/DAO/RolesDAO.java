package com.mycompany.DAO;

import com.mycompany.model.Roles;
import java.util.List;

public interface RolesDAO {
    int insertar(Roles rol);
    int actualizar(Roles rol);
    int eliminar(int id);
    Roles obtenerPorId(int id);
    List<Roles> listarTodos();
}