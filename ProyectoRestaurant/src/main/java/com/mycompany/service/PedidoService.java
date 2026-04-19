package com.mycompany.service;

import com.mycompany.DAO.PedidosDAO;
import com.mycompany.DAO.DetallePedidoDAO;
import com.mycompany.DAO.CarritoDAO;
import com.mycompany.DAO.DetalleCarritoDAO;
import com.mycompany.DAO.impl.PedidosDAOImpl;
import com.mycompany.DAO.impl.DetallePedidoDAOImpl;
import com.mycompany.DAO.impl.CarritoDAOImpl;
import com.mycompany.DAO.impl.DetalleCarritoDAOImpl;
import com.mycompany.model.Carrito;
import com.mycompany.model.DetalleCarrito;
import com.mycompany.model.DetallePedido;
import com.mycompany.model.EstadoCarrito;
import com.mycompany.model.EstadoPedido;
import com.mycompany.model.Pedidos;
import com.mycompany.model.TipoPedido;
import com.mycompany.model.Usuarios;

import java.time.LocalDate;
import java.util.List;

public class PedidoService {

    private final PedidosDAO       pedidoDao;
    private final DetallePedidoDAO  detalleDao;
    private final CarritoDAO        carritoDao;
    private final DetalleCarritoDAO detalleCarritoDao;

    public PedidoService() {
        this.pedidoDao         = new PedidosDAOImpl();
        this.detalleDao        = new DetallePedidoDAOImpl();
        this.carritoDao        = new CarritoDAOImpl();
        this.detalleCarritoDao = new DetalleCarritoDAOImpl();
    }

    public boolean realizarPedido(int idUsuario, String direccion, int idTipo) {

        Carrito carrito = carritoDao.obtenerCarritoActivoPorUsuario(idUsuario);
        if (carrito == null) return false;

        List<DetalleCarrito> items = detalleCarritoDao.listarPorCarrito(carrito.getId());
        if (items.isEmpty()) return false;

        double total = items.stream()
                .mapToDouble(i -> i.getPrecioUnitario() * i.getCantidad())
                .sum();

        Usuarios    u      = new Usuarios(null, null, null, null, null);
        u.setId(idUsuario);
        EstadoPedido est  = new EstadoPedido("PENDIENTE");
        est.setId(1);
        TipoPedido   tipo  = new TipoPedido("");
        tipo.setId(idTipo);

        Pedidos pedido = new Pedidos(u, total, LocalDate.now(), direccion, est, tipo);
        pedidoDao.insertar(pedido);

        List<Pedidos> lista = pedidoDao.listarPorUsuario(idUsuario);
        Pedidos nuevo = lista.get(lista.size() - 1);

        for (DetalleCarrito item : items) {
            DetallePedido dp = new DetallePedido(
                nuevo,
                item.getProducto(),
                item.getCantidad(),
                item.getPrecioUnitario()
            );
            detalleDao.insertar(dp);
        }

        EstadoCarrito cerrado = new EstadoCarrito("FINALIZADO");
        cerrado.setId(2);
        carrito.setEstado(cerrado);
        carritoDao.actualizar(carrito);

        return true;
    }

    public List<Pedidos> listarPorUsuario(int idUsuario) {
        return pedidoDao.listarPorUsuario(idUsuario);
    }

    public List<Pedidos> listarTodos() {
        return pedidoDao.listarTodos();
    }

    public Pedidos obtenerPorId(int id) {
        return pedidoDao.obtenerPorId(id);
    }

    public List<DetallePedido> obtenerDetalles(int idPedido) {
        return detalleDao.listarPorPedido(idPedido);
    }

    public void cambiarEstado(int idPedido, int idEstado) {
        Pedidos pedido = pedidoDao.obtenerPorId(idPedido);
        if (pedido != null) {
            EstadoPedido est = new EstadoPedido("");
            est.setId(idEstado);
            pedido.setEstadoPedido(est);
            pedidoDao.actualizar(pedido);
        }
    }
}