package com.mycompany.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.mycompany.model.Productos;
import com.mycompany.model.Categoria;
import com.mycompany.service.ProductoService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/productos"})
public class ProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductoService productoService;

    @Override
    public void init() throws ServletException {
        productoService = new ProductoService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar":
                listarProductos(req, res);   break;
            case "nuevo":
                mostrarFormNuevo(req, res);  break;
            case "editar":
                mostrarFormEditar(req, res); break;
            case "eliminar":
                eliminarProducto(req, res);  break;
            default:
                res.sendRedirect("productos?accion=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");

        if ("guardar".equals(accion)) {
            guardarProducto(req, res);
        } else if ("actualizar".equals(accion)) {
            actualizarProducto(req, res);
        } else {
            res.sendRedirect("productos?accion=listar");
        }
    }

    private void listarProductos(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String catParam = req.getParameter("idCategoria");
        List<Productos> lista;

        if (catParam != null && !catParam.isEmpty()) {
            int idCat = Integer.parseInt(catParam);
            lista = productoService.listarPorCategoria(idCat);
            req.setAttribute("categoriaFiltro", idCat);
        } else {
            lista = productoService.listarTodos();
        }

        List<Categoria> categorias = productoService.listarCategorias();
        req.setAttribute("productos", lista);
        req.setAttribute("categorias", categorias);
        req.getRequestDispatcher("/admin/productos.jsp").forward(req, res);
    }

    private void mostrarFormNuevo(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        List<Categoria> categorias = productoService.listarCategorias();
        req.setAttribute("categorias", categorias);
        req.getRequestDispatcher("/admin/formProducto.jsp").forward(req, res);
    }

    private void mostrarFormEditar(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        Productos producto = productoService.obtenerPorId(id);
        List<Categoria> categorias = productoService.listarCategorias();
        req.setAttribute("producto", producto);
        req.setAttribute("categorias", categorias);
        req.getRequestDispatcher("/admin/formProducto.jsp").forward(req, res);
    }

    private void guardarProducto(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String nombre  = req.getParameter("nombre");
        String desc    = req.getParameter("descripcion");
        String imagen  = req.getParameter("imagen");
        double precio  = Double.parseDouble(req.getParameter("precio"));
        int    idCat   = Integer.parseInt(req.getParameter("idCategoria"));

        productoService.guardar(nombre, desc, imagen, precio, idCat);
        res.sendRedirect("productos?accion=listar");
    }

    private void actualizarProducto(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String nombre = req.getParameter("nombre");
        String desc = req.getParameter("descripcion");
        String imagen = req.getParameter("imagen");
        double precio = Double.parseDouble(req.getParameter("precio"));
        int idCat = Integer.parseInt(req.getParameter("idCategoria"));

        productoService.actualizar(id, nombre, desc, imagen, precio, idCat);

        res.sendRedirect("productos?accion=listar");
    }

    private void eliminarProducto(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        productoService.eliminar(id);
        res.sendRedirect("productos?accion=listar");
    }
    
}