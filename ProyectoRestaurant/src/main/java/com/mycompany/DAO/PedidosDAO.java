package com.mycompany.DAO;

import com.mycompany.model.Pedidos;
import java.util.List;

public interface PedidosDAO {
    int insertar(Pedidos pedido);
    int actualizar(Pedidos pedido);
    int eliminar(int id);
    Pedidos obtenerPorId(int id);
    List<Pedidos> listarTodos();
    List<Pedidos> listarPorUsuario(int idUsuario);
    List<Pedidos> listarPorEstado(int idEstadoPedido);
}