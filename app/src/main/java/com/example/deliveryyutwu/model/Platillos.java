package com.example.deliveryyutwu.model;

import java.io.Serializable;

public class Platillos implements Serializable {
    private Integer idProducto;
    private String nombreProducto;
    private Double precio;
    private Integer idCategoria;
    private String imagen;
    private int numeroCart;

    public Platillos(){

    }

    public Platillos(Integer idProducto, String nombreProducto, Double precio, Integer idCategoria, String imagen, int numeroCart) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.idCategoria = idCategoria;
        this.imagen = imagen;
        this.numeroCart = numeroCart;
    }

    public Platillos(Integer idProducto, String nombreProducto, Double precio, Integer idCategoria, String imagen) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.idCategoria = idCategoria;
        this.imagen = imagen;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getNumeroCart() {
        return numeroCart;
    }

    public void setNumeroCart(int numeroCart) {
        this.numeroCart = numeroCart;
    }
}
