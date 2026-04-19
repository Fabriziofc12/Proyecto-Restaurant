package com.mycompany.service;

import com.mycompany.DAO.CarritoDAO;
import com.mycompany.DAO.DetalleCarritoDAO;
import com.mycompany.DAO.ProductosDAO;
import com.mycompany.DAO.impl.CarritoDAOImpl;
import com.mycompany.DAO.impl.DetalleCarritoDAOImpl;
import com.mycompany.DAO.impl.ProductosDAOImpl;
import com.mycompany.model.Carrito;
import com.mycompany.model.DetalleCarrito;
import com.mycompany.model.EstadoCarrito;
import com.mycompany.model.Productos;
import com.mycompany.model.Usuarios;

import java.time.LocalDateTime;
import java.util.List;

public class CarritoService {

    private final CarritoDAO        carritoDao;
    private final DetalleCarritoDAO detalleDao;
    private final ProductosDAO      productoDao;

    public CarritoService() {
        this.carritoDao  = new CarritoDAOImpl();
        this.detalleDao  = new DetalleCarritoDAOImpl();
        this.productoDao = new ProductosDAOImpl();
    }

    /** Obtiene el carrito activo del usuario; si no existe, lo crea */
    public Carrito obtenerCarritoActivo(int idUsuario) {
        Carrito carrito = carritoDao.obtenerCarritoActivoPorUsuario(idUsuario);
        if (carrito == null) {
            Usuarios     u      = new Usuarios(null, null, null, null, null);
            u.setId(idUsuario);
            EstadoCarrito est  = new EstadoCarrito("ACTIVO");
            est.setId(1);
            carrito = new Carrito(u, est, LocalDateTime.now());
            carritoDao.insertar(carrito);
            carrito = carritoDao.obtenerCarritoActivoPorUsuario(idUsuario);
        }
        return carrito;
    }

    /** Devuelve los detalles (ítems) de un carrito */
    public List<DetalleCarrito> obtenerDetalles(int idCarrito) {
        return detalleDao.listarPorCarrito(idCarrito);
    }

    public void agregarProducto(int idUsuario, int idProducto, int cantidad) {
        Carrito  carrito  = obtenerCarritoActivo(idUsuario);
        Productos prod   = productoDao.obtenerPorId(idProducto);

        List<DetalleCarrito> detalles = detalleDao.listarPorCarrito(carrito.getId());

        for (DetalleCarrito d : detalles) {
            if (d.getProducto().getId() == idProducto) {
                d.setCantidad(d.getCantidad() + cantidad);
                detalleDao.actualizar(d);
                return;
            }
        }

        DetalleCarrito det = new DetalleCarrito(carrito, prod, cantidad, prod.getPrecio());
        detalleDao.insertar(det);
    }

    public void actualizarCantidad(int idDetalle, int nuevaCantidad) {
        DetalleCarrito det = detalleDao.obtenerPorId(idDetalle);
        if (det != null) {
            det.setCantidad(nuevaCantidad);
            detalleDao.actualizar(det);
        }
    }

    /** Elimina un ítem del carrito por su ID de detalle */
    public void eliminarDetalle(int idDetalle) {
        detalleDao.eliminar(idDetalle);
    }

    /** Vacía todos los ítems del carrito activo del usuario */
    public void vaciarCarrito(int idUsuario) {
        Carrito carrito = obtenerCarritoActivo(idUsuario);
        List<DetalleCarrito> detalles = detalleDao.listarPorCarrito(carrito.getId());
        for (DetalleCarrito d : detalles) {
            detalleDao.eliminar(d.getId());
        }
    }

    /** Calcula el total a pagar sumando precio * cantidad de cada ítem */
    public double calcularTotal(List<DetalleCarrito> detalles) {
        return detalles.stream()
                .mapToDouble(d -> d.getPrecioUnitario() * d.getCantidad())
                .sum();
    }
}