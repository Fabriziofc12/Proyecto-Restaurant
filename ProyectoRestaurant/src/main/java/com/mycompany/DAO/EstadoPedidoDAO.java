package com.mycompany.DAO;

import com.mycompany.model.EstadoPedido;
import java.util.List;

public interface EstadoPedidoDAO {
    int insertar(EstadoPedido estadoPedido);
    int actualizar(EstadoPedido estadoPedido);
    int eliminar(int id);
    EstadoPedido obtenerPorId(int id);
    List<EstadoPedido> listarTodos();
}