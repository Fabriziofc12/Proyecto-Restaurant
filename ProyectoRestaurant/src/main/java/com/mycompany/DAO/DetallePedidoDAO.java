package com.mycompany.DAO;

import com.mycompany.model.DetallePedido;
import java.util.List;

public interface DetallePedidoDAO {
    int insertar(DetallePedido detallePedido);
    int actualizar(DetallePedido detallePedido);
    int eliminar(int id);
    DetallePedido obtenerPorId(int id);
    List<DetallePedido> listarTodos();
    List<DetallePedido> listarPorPedido(int idPedido);
}