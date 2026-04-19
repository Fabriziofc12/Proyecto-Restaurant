package com.mycompany.DAO;

// 1. CORRECCIÓN: Importar en singular
import com.mycompany.model.Categoria; 
import java.util.List;

public interface CategoriasDAO {
    int insertar(Categoria categoria);
    int actualizar(Categoria categoria);
    int eliminar(int id);
    Categoria obtenerPorId(int id); 
    List<Categoria> listarTodos(); 
}