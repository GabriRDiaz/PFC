/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.pojo;

import java.sql.Date;

/**
 *
 * @author Gabriel
 */
public class Pedido {
    int id;
    String cliente;
    java.sql.Date fechaExp;
    java.sql.Date fechaEnvio;
    String estado;
    String destinatario;
    String direccion;
    String telefono;
    String pais;

    public Pedido(int id, String cliente, Date fechaExp, Date fechaEnvio, String estado, String destinatario, String direccion, String telefono, String pais) {
        this.id = id;
        this.cliente = cliente;
        this.fechaExp = fechaExp;
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
        this.destinatario = destinatario;
        this.direccion = direccion;
        this.telefono = telefono;
        this.pais = pais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Date getFechaExp() {
        return fechaExp;
    }

    public void setFechaExp(Date fechaExp) {
        this.fechaExp = fechaExp;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    
}
