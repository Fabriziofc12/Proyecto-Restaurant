
package com.mycompany.model;

import java.time.LocalDateTime;

public class Carrito {
    private int id;
    private Usuarios usuario;
    private EstadoCarrito estado;
    private LocalDateTime fechaCreacion;

    public Carrito(Usuarios usuario, EstadoCarrito estado, LocalDateTime fechaCreacion) {
        this.usuario = usuario;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the usuario
     */
    public Usuarios getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the estado
     */
    public EstadoCarrito getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoCarrito estado) {
        this.estado = estado;
    }

    /**
     * @return the fechaCreacion
     */
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    
}
