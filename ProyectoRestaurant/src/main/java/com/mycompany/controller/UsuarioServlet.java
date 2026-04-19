package com.mycompany.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.mycompany.model.Usuarios;
import com.mycompany.service.UsuarioService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuarios", "/login", "/registro", "/logout"})
public class UsuarioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String path = req.getServletPath();

        switch (path) {
            case "/login":
                req.getRequestDispatcher("/login.jsp").forward(req, res);
                break;
            case "/registro":
                req.getRequestDispatcher("/registro.jsp").forward(req, res);
                break;
            case "/logout":
                cerrarSesion(req, res);
                break;
            case "/usuarios":
                listarUsuarios(req, res);
                break;
            default:
                res.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        switch (path) {
            case "/login":
                iniciarSesion(req, res);
                break;
            case "/registro":
                registrarUsuario(req, res);
                break;
            default:
                res.sendRedirect("login");
        }
    }


    private void iniciarSesion(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException {

        String correo = req.getParameter("correo");
        String pass   = req.getParameter("contrasenia");

        Usuarios usuario = usuarioService.autenticar(correo, pass);

        if (usuario == null) {
            req.setAttribute("error", "Correo o contraseña incorrectos.");
            req.getRequestDispatcher("/login.jsp").forward(req, res);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("usuario", usuario);
        session.setAttribute("rol", usuario.getRol().getTrol());

        if ("ADMIN".equalsIgnoreCase(usuario.getRol().getTrol())) {
            res.sendRedirect(req.getContextPath() + "/productos?accion=listar");
            return;
        }

        res.sendRedirect(req.getContextPath() + "/menu");
    }

    private void registrarUsuario(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        String nombre   = req.getParameter("nombre");
        String correo   = req.getParameter("correo");
        String telefono = req.getParameter("telefono");
        String pass     = req.getParameter("contrasenia");

        boolean ok = usuarioService.registrar(nombre, correo, telefono, pass);

        if (ok) {
            req.setAttribute("exito", "Cuenta creada. Ya puedes iniciar sesión.");
            req.getRequestDispatcher("/login.jsp").forward(req, res);
        } else {
            req.setAttribute("error", "El correo ya está registrado.");
            req.getRequestDispatcher("/registro.jsp").forward(req, res);
        }
    }

    private void cerrarSesion(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        res.sendRedirect("login");
    }

    private void listarUsuarios(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        List<Usuarios> lista = usuarioService.listarTodos();
        req.setAttribute("usuarios", lista);
        req.getRequestDispatcher("/admin/usuarios.jsp").forward(req, res);
    }
}