package com.mycompany.DAO;

import com.mycompany.model.DetalleCarrito;
import java.util.List;

public interface DetalleCarritoDAO {
    int insertar(DetalleCarrito detalleCarrito);
    int actualizar(DetalleCarrito detalleCarrito);
    int eliminar(int id);
    DetalleCarrito obtenerPorId(int id);
    List<DetalleCarrito> listarTodos();
    List<DetalleCarrito> listarPorCarrito(int idCarrito);
}