/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.pojo;

import java.util.ArrayList;

/**
 *
 * @author Gabriel
 */
public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private double precioSinIVA;
    private double descuento;
    private String marca;
    private String referencia;
    private String modelo;
    private String color;
    private int stock;
    private double iva;
    private double coste;
    private int idMarca;
    private int idIva;
    private ArrayList<Integer> secciones;
    private String ivaStr;
    
    //Constructor EditProduct
    public Producto(int id, String nombre, String descripcion, double precioSinIVA, double descuento, String marca, String referencia, String modelo, String color, String ivaStr, double coste) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioSinIVA = precioSinIVA;
        this.descuento = descuento;
        this.marca = marca;
        this.referencia = referencia;
        this.modelo = modelo;
        this.color = color;
        this.ivaStr = ivaStr;
        this.coste = coste;
    }   
    //Constructor Insert Product
    public Producto(int id, String nombre, String descripcion, double precioSinIVA, double descuento, String marca, String referencia, String modelo, String color,int stock, String ivaStr, double coste, ArrayList<Integer> secciones) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioSinIVA = precioSinIVA;
        this.descuento = descuento;
        this.marca = marca;
        this.referencia = referencia;
        this.modelo = modelo;
        this.color = color;
        this.stock= stock;
        this.ivaStr = ivaStr;
        this.coste = coste;
        this.secciones = secciones;
    }   
        
    //Constructor updProduct query
    public Producto(int id, String nombre, String descripcion, double precioSinIVA, double descuento, int idMarca, String referencia, String modelo, String color, int idIva, double coste, ArrayList<Integer> secciones) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioSinIVA = precioSinIVA;
        this.descuento = descuento;
        this.idMarca = idMarca;
        this.referencia = referencia;
        this.modelo = modelo;
        this.color = color;
        this.idIva = idIva;
        this.coste = coste;
        this.secciones = secciones;
    }
    
    public Producto(String nombre, String descripcion, double precioSinIVA,int idIva, double descuento, String referencia, String modelo, String color, int stock, double coste, int idMarca, ArrayList<Integer> secciones) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioSinIVA = precioSinIVA;
        this.idIva= idIva;
        this.descuento = descuento;
        this.referencia = referencia;
        this.modelo = modelo;
        this.color = color;
        this.stock = stock;
        this.coste = coste;
        this.idMarca = idMarca;
        this.secciones = secciones;
    }

    public Producto(int id, String nombre, String descripcion, double precioSinIVA, double descuento, String marca, String referencia, String modelo, String color, int stock, double iva) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioSinIVA = precioSinIVA;
        this.descuento = descuento;
        this.marca = marca;
        this.referencia = referencia;
        this.modelo = modelo;
        this.color = color;
        this.stock = stock;
        this.iva = iva;
    }

    public int getIdIva() {
        return idIva;
    }

    public void setIdIva(int idIva) {
        this.idIva = idIva;
    }

    public String getIvaStr() {
        return ivaStr;
    }

    public void setIvaStr(String ivaStr) {
        this.ivaStr = ivaStr;
    }
    
    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public int getIdIVA() {
        return idIva;
    }

    public void setIdIVA(int idIVA) {
        this.idIva = idIVA;
    }

    public ArrayList<Integer> getSecciones() {
        return secciones;
    }

    public void setSecciones(ArrayList<Integer> secciones) {
        this.secciones = secciones;
    }
    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }
    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getIva() {
        return iva;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecioSinIVA(double precioSinIVA) {
        this.precioSinIVA = precioSinIVA;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecioSinIVA() {
        return precioSinIVA;
    }

    public double getDescuento() {
        return descuento;
    }

    public String getMarca() {
        return marca;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    public int getStock() {
        return stock;
    }
    
}
