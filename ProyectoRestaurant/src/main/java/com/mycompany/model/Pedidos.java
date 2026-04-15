/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import java.time.LocalDate;

/**
 *
 * @author Fabrizio
 */
public class Pedidos {
    private int id;
    private Usuarios usuario;
    private double total;
    private LocalDate fecha;
    private String direccionEntrega;
    private EstadoPedido estadoPedido;
    private TipoPedido tipoPedido;

    public Pedidos(Usuarios usuario, double total, LocalDate fecha, String direccionEntrega, EstadoPedido estadoPedido, TipoPedido tipoPedido) {
        this.usuario = usuario;
        this.total = total;
        this.fecha = fecha;
        this.direccionEntrega = direccionEntrega;
        this.estadoPedido = estadoPedido;
        this.tipoPedido = tipoPedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(TipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }
    
    
    
}
