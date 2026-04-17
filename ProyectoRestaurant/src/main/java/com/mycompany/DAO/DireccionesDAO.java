package com.mycompany.DAO;

import com.mycompany.model.Direcciones;
import java.util.List;

public interface DireccionesDAO {
    int insertar (Direcciones direccion);
    int actualizar (Direcciones direccion);
    int eliminar (int id);
    Direcciones obtenerPorId (int id);
    Direcciones obtenerPrincipalPorUsuario(int idUsuario);
    List<Direcciones> listarPorUsuario(int idUsuario);
}
