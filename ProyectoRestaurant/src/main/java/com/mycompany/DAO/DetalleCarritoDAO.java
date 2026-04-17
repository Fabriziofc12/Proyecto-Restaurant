package com.mycompany.DAO;

import com.mycompany.model.DetalleCarrito;
import java.util.List;

public interface DetalleCarritoDAO {
    int insertar(DetalleCarrito detalle);
    int actualizar(DetalleCarrito detalle);
    int eliminar(int id);
    DetalleCarrito obtenerPorId(int id);
    List<DetalleCarrito> listarPorCarrito(int idCarrito);
}
