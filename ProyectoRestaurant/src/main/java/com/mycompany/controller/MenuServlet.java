package com.mycompany.controller;

import com.mycompany.model.Categoria;
import com.mycompany.model.Productos;
import com.mycompany.service.ProductoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "MenuServlet", urlPatterns = {"/menu"})
public class MenuServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductoService productoService;

    @Override
    public void init() throws ServletException {
        productoService = new ProductoService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String idCategoriaParam = req.getParameter("idCategoria");

        List<Productos> productos;
        List<Categoria> categorias = productoService.listarCategorias();

        if (idCategoriaParam != null && !idCategoriaParam.isEmpty()) {
            int idCategoria = Integer.parseInt(idCategoriaParam);
            productos = productoService.listarPorCategoria(idCategoria);
            req.setAttribute("categoriaActiva", idCategoria);
        } else {
            productos = productoService.listarTodos();
        }

        req.setAttribute("productos", productos);
        req.setAttribute("categorias", categorias);

        req.getRequestDispatcher("/menu.jsp").forward(req, res);
    }
}