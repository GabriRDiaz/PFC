/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.pojo;

/**
 *
 * @author Gabriel
 */
public class LineaPedido {
    private int id;
    private String nombre;
    private int cantidad;
    //Var Desglose pedido
    private double precioSinIva;
    private double subtotal;
    private double iva;
    private double total;

    //Constructor desglose

    public LineaPedido(String nombre, int cantidad, double precioSinIva, double subtotal, double iva, double total) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioSinIva = precioSinIva;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
    }
    
    
    //Constructor creación pedido
    public LineaPedido(int id, String nombre, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    //Getters setters desglose
    public double getPrecioSinIva() {
        return precioSinIva;
    }

    public void setPrecioSinIva(double precioSinIva) {
        this.precioSinIva = precioSinIva;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
