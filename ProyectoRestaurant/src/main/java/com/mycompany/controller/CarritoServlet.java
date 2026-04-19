package com.mycompany.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.mycompany.model.Carrito;
import com.mycompany.model.DetalleCarrito;
import com.mycompany.model.Usuarios;
import com.mycompany.service.CarritoService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CarritoServlet", urlPatterns = {"/carrito"})
public class CarritoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CarritoService carritoService;

    @Override
    public void init() throws ServletException {
        carritoService = new CarritoService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            res.sendRedirect("login");
            return;
        }

        Usuarios u = (Usuarios) session.getAttribute("usuario");
        String accion = req.getParameter("accion");

        switch (accion != null ? accion : "ver") {
            case "ver":
                verCarrito(req, res, u); break;
            case "eliminarItem":
                int idDetalle = Integer.parseInt(req.getParameter("idDetalle"));
                carritoService.eliminarDetalle(idDetalle);
                res.sendRedirect("carrito?accion=ver");
                break;
            case "vaciar":
                carritoService.vaciarCarrito(u.getId());
                res.sendRedirect("carrito?accion=ver");
                break;
            default:
                verCarrito(req, res, u);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            res.sendRedirect("login");
            return;
        }

        Usuarios u        = (Usuarios) session.getAttribute("usuario");
        String   accion    = req.getParameter("accion");
        int      idProd    = Integer.parseInt(req.getParameter("idProducto"));
        int      cantidad  = Integer.parseInt(req.getParameter("cantidad"));

        if ("agregar".equals(accion)) {
            carritoService.agregarProducto(u.getId(), idProd, cantidad);
        } else if ("actualizar".equals(accion)) {
            int idDetalle = Integer.parseInt(req.getParameter("idDetalle"));
            carritoService.actualizarCantidad(idDetalle, cantidad);
        }
        res.sendRedirect("carrito?accion=ver");
    }

    private void verCarrito(HttpServletRequest req, HttpServletResponse res,
                             Usuarios usuario)
            throws ServletException, IOException {

        Carrito carrito = carritoService.obtenerCarritoActivo(usuario.getId());
        List<DetalleCarrito> detalles = carritoService.obtenerDetalles(carrito.getId());
        double total = carritoService.calcularTotal(detalles);

        req.setAttribute("carrito",  carrito);
        req.setAttribute("detalles", detalles);
        req.setAttribute("total",    total);
        req.getRequestDispatcher("/carrito.jsp").forward(req, res);
    }
}