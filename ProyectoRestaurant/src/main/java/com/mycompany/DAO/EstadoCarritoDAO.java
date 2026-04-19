package com.mycompany.DAO;

import com.mycompany.model.EstadoCarrito;
import java.util.List;

public interface EstadoCarritoDAO {
    int insertar(EstadoCarrito estadoCarrito);
    int actualizar(EstadoCarrito estadoCarrito);
    int eliminar(int id);
    EstadoCarrito obtenerPorId(int id);
    List<EstadoCarrito> listarTodos();
}