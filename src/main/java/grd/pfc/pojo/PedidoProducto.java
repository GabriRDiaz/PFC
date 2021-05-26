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
public class PedidoProducto {
        private int id;
    private String nombre;
    private int cantidad;
    
    public PedidoProducto(Producto producto, int cantidad){
        this.id = producto.getId();
        this.nombre = producto.getNombre();
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
    
}
