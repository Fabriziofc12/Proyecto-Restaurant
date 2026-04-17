package com.mycompany.DAO;

import com.mycompany.model.Productos;
import java.util.List;

public interface ProductosDAO {
    int insertar(Productos producto);
    int actualizar(Productos producto);
    int eliminar(int id);
    Productos obtenerPorId(int id);
    List<Productos> listarTodos();
    List<Productos> listarPorCategoria(int idCategoria);
}
