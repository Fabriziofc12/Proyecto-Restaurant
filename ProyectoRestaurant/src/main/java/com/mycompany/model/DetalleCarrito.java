
package com.mycompany.model;


public class DetalleCarrito {
    private int id;
    private Carrito carrito;
    private Productos producto;
    private int cantidad;
    private double precioUnitario;

    public DetalleCarrito(Carrito carrito, Productos producto, int cantidad, double precioUnitario) {
        this.carrito = carrito;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }
    
    
    
    
}
