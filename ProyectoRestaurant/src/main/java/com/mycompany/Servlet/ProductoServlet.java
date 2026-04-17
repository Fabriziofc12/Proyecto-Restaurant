package com.mycompany.servlet;

import com.mycompany.DAO.CategoriaDAO; // Asegúrate de que el paquete sea el correcto
import com.mycompany.DAO.ProductoDAO;
import com.mycompany.model.Categoria;
import com.mycompany.model.Producto;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
public class ProductoServlet extends HttpServlet {

    private final ProductoDAO prodDAO = new ProductoDAO();
    private final CategoriaDAO catDAO = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        accion = (accion == null) ? "listar" : accion;

        try {
            switch (accion) {
                case "listar":
                    // Vista para el Cliente: Ver todo el menú
                    request.setAttribute("productos", prodDAO.listarProductos());
                    request.setAttribute("categorias", catDAO.listar());
                    request.getRequestDispatcher("menu.jsp").forward(request, response);
                    break;

                case "filtrar":
                    // Vista para el Cliente: Ver por categoría
                    int idCat = Integer.parseInt(request.getParameter("idCat"));
                    request.setAttribute("productos", prodDAO.listarPorCategoria(idCat));
                    request.setAttribute("categorias", catDAO.listar());
                    request.getRequestDispatcher("menu.jsp").forward(request, response);
                    break;

                case "gestionar":
                    // Vista para el Admin: Tabla de mantenimiento
                    request.setAttribute("productos", prodDAO.listarProductos());
                    request.getRequestDispatcher("admin/productos.jsp").forward(request, response);
                    break;

                case "prepararCrear":
                    // Ir al formulario de nuevo producto (necesita categorías para el select)
                    request.setAttribute("categorias", catDAO.listar());
                    request.getRequestDispatcher("admin/formProducto.jsp").forward(request, response);
                    break;

                case "prepararEditar":
                    // Cargar datos actuales y categorías para editar
                    int idEdit = Integer.parseInt(request.getParameter("id"));
                    Producto p = prodDAO.buscarPorId(idEdit);
                    request.setAttribute("producto", p);
                    request.setAttribute("categorias", catDAO.listar());
                    request.getRequestDispatcher("admin/formProducto.jsp").forward(request, response);
                    break;

                case "eliminar":
                    // Acción rápida de eliminar
                    int idDel = Integer.parseInt(request.getParameter("id"));
                    prodDAO.eliminar(idDel);
                    response.sendRedirect("ProductoServlet?accion=gestionar");
                    break;
                    
                default:
                    response.sendRedirect("ProductoServlet?accion=listar");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("ProductoServlet?accion=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Capturamos los datos del formulario (funciona para Crear y Editar)
        String idStr = request.getParameter("id"); // Solo vendrá en edición
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String imagen = request.getParameter("imagen");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));

        // Construimos la categoría necesaria para el objeto Producto
        Categoria cat = new Categoria();
        cat.setId(idCategoria);

        Producto prod = new Producto();
        prod.setNombre(nombre);
        prod.setDescripcion(descripcion);
        prod.setImagen(imagen);
        prod.setPrecio(precio);
        prod.setCategoria(cat);

        if (idStr == null || idStr.isEmpty()) {
            // Es un nuevo registro
            prodDAO.insertar(prod);
        } else {
            // Es una actualización
            prod.setId(Integer.parseInt(idStr));
            prodDAO.actualizar(prod);
        }

        // Siempre redirigir después de un POST para evitar duplicados al refrescar
        response.sendRedirect("ProductoServlet?accion=gestionar");
    }
}