package com.modelos.II.ud.distrital.api_productos.models;

import java.io.Serializable;

public class Producto implements Serializable {
    
    private static final long serialVersionUID = 1L;  // Para asegurar la compatibilidad de versiones

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer cantidad;

    // Constructor por defecto
    public Producto() {}

    // Constructor con parámetros
    public Producto(Long id, String nombre, String descripcion, Double precio, Integer cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    // Método toString para mostrar los detalles del producto de forma legible
    @Override
    public String toString() {
        return "Producto{" + 
                "id=" + id + 
                ", nombre='" + nombre + '\'' + 
                ", descripcion='" + descripcion + '\'' + 
                ", precio=" + precio + 
                ", cantidad=" + cantidad + 
                '}';
    }
}
