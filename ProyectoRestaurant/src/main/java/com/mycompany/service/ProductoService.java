package com.mycompany.service;

import com.mycompany.DAO.ProductosDAO;
import com.mycompany.DAO.CategoriasDAO;
import com.mycompany.DAO.impl.ProductosDAOImpl;
import com.mycompany.DAO.impl.CategoriasDAOImpl;
import com.mycompany.model.Productos;
import com.mycompany.model.Categoria;
import java.util.List;

public class ProductoService {

    private final ProductosDAO  productoDao;
    private final CategoriasDAO categoriaDao;

    public ProductoService() {
        this.productoDao  = new ProductosDAOImpl();
        this.categoriaDao = new CategoriasDAOImpl();
    }

    public List<Productos> listarTodos() {
        return productoDao.listarTodos();
    }

    public List<Productos> listarPorCategoria(int idCategoria) {
        return productoDao.listarPorCategoria(idCategoria);
    }

    public List<Categoria> listarCategorias() {
        return categoriaDao.listarTodos();
    }

    public Productos obtenerPorId(int id) {
        return productoDao.obtenerPorId(id);
    }

    public void guardar(String nombre, String descripcion,
                        String imagen, double precio,
                        int idCategoria) {
        Categoria cat = new Categoria();
        cat.setId(idCategoria);
        Productos p = new Productos(nombre, descripcion, imagen, precio, cat);
        productoDao.insertar(p);
    }

    public void actualizar(int id, String nombre, String descripcion,
                       String imagen, double precio, int idCategoria) {
        Categoria cat = new Categoria();
        cat.setId(idCategoria);

        Productos p = new Productos(nombre, descripcion, imagen, precio, cat);
        p.setId(id);

        productoDao.actualizar(p);
    }

    public void eliminar(int id) {
        productoDao.eliminar(id);
    }
}