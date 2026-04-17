package com.mycompany.DAO;

import com.mycompany.model.DetallePedido;
import java.util.List;

public interface DetallePedidoDAO {
    int insertar (DetallePedido detalle);
    int actualizar (DetallePedido detalle);
    int eliminar (int id);
    DetallePedido obtenerPorId (int id);
    List<DetallePedido> listarPorPedido(int idpedido);
}
