package com.mycompany.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.mycompany.model.DetallePedido;
import com.mycompany.model.Pedidos;
import com.mycompany.model.Usuarios;
import com.mycompany.service.PedidoService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PedidoServlet", urlPatterns = {"/pedidos"})
public class PedidoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PedidoService pedidoService;

    @Override
    public void init() throws ServletException {
        pedidoService = new PedidoService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            res.sendRedirect("login");
            return;
        }

        Usuarios u     = (Usuarios) session.getAttribute("usuario");
        String   accion = req.getParameter("accion");
        boolean  esAdmin = "ADMIN".equals(u.getRol().getTrol());

        switch (accion != null ? accion : "mis-pedidos") {

            case "mis-pedidos":
                List<Pedidos> lista = esAdmin
                    ? pedidoService.listarTodos()
                    : pedidoService.listarPorUsuario(u.getId());
                req.setAttribute("pedidos", lista);
                req.getRequestDispatcher("/pedidos.jsp").forward(req, res);
                break;

            case "detalle":
                int id = Integer.parseInt(req.getParameter("id"));
                Pedidos pedido = pedidoService.obtenerPorId(id);
                List<DetallePedido> detalles = pedidoService.obtenerDetalles(id);
                req.setAttribute("pedido",    pedido);
                req.setAttribute("detalles",  detalles);
                req.getRequestDispatcher("/detallePedido.jsp").forward(req, res);
                break;

            case "cambiarEstado":
                if (!esAdmin) { res.sendRedirect("pedidos?accion=mis-pedidos"); break; }
                int idPed     = Integer.parseInt(req.getParameter("id"));
                int idEstado  = Integer.parseInt(req.getParameter("idEstado"));
                pedidoService.cambiarEstado(idPed, idEstado);
                res.sendRedirect("pedidos?accion=mis-pedidos");
                break;

            default:
                res.sendRedirect("pedidos?accion=mis-pedidos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            res.sendRedirect("login");
            return;
        }

        Usuarios u        = (Usuarios) session.getAttribute("usuario");
        String   direccion = req.getParameter("direccion");
        int      idTipo    = Integer.parseInt(req.getParameter("tipoPedido"));

        boolean ok = pedidoService.realizarPedido(u.getId(), direccion, idTipo);

        if (ok) {
            req.setAttribute("exito", "¡Pedido realizado con éxito!");
        } else {
            req.setAttribute("error", "El carrito está vacío.");
        }
        res.sendRedirect("pedidos?accion=mis-pedidos");
    }
}