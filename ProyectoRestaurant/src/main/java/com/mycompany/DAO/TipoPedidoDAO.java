package com.mycompany.DAO;

import com.mycompany.model.TipoPedido;
import java.util.List;

public interface TipoPedidoDAO {
    int insertar(TipoPedido tipoPedido);
    int actualizar(TipoPedido tipoPedido);
    int eliminar(int id);
    TipoPedido obtenerPorId(int id);
    List<TipoPedido> listarTodos();
}