package com.mycompany.model;

public class Productos {
    private int id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private double precio;
    private Categoria categoria; 

    public Productos() { }

    public Productos(String nombre, String descripcion, 
                     String imagen, double precio, 
                     Categoria categoria) { 
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.imagen      = imagen;
        this.precio      = precio;
        this.categoria   = categoria;
    }

    public Productos(int id, String nombre, String descripcion,
                     String imagen, double precio,
                     Categoria categoria) {  
        this.id          = id;
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.imagen      = imagen;
        this.precio      = precio;
        this.categoria   = categoria;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public Categoria getCategoria() { return categoria; } 
    public void setCategoria(Categoria categoria) { this.categoria = categoria; } 
}