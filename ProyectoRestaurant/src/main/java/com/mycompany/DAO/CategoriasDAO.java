package com.mycompany.DAO;

import com.mycompany.model.Categorias;
import java.util.List;

public interface CategoriasDAO {
    int insertar(Categorias categoria);
    int actualizar(Categorias categoria);
    int eliminar(int id);
    Categorias obtenerPorId(int id);
    List<Categorias> listarTodos();
}
