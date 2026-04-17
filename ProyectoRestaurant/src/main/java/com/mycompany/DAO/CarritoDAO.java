package com.mycompany.DAO;

    import com.mycompany.model.Carrito;
    import java.util.List;

public interface CarritoDAO {
    int insertar (Carrito carrito);
    int actualizar (Carrito carrito);
    int eliminar (int id);
    Carrito obtenerPorId(int id);
    List <Carrito> listarTodos();
    Carrito obtenerCarritoActivoPorUsuario(int idUsuario);
}
