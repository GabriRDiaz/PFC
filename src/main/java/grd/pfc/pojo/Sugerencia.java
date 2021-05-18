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
public class Sugerencia {
    private int id;
    private String sugerencia;
    private java.sql.Date fecha;
    private int idSeccion;
    private int idEmpleado;
    

    public Sugerencia(String sugerencia, int idSeccion, int idEmpleado) {
        this.sugerencia = sugerencia;
        this.idSeccion = idSeccion;
        this.idEmpleado = idEmpleado;
    }

    public Sugerencia(int id, String sugerencia, Date fecha, int idSeccion, int idEmpleado) {
        this.id = id;
        this.sugerencia = sugerencia;
        this.fecha = fecha;
        this.idSeccion = idSeccion;
        this.idEmpleado = idEmpleado;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSugerencia() {
        return sugerencia;
    }

    public void setSugerencia(String sugerencia) {
        this.sugerencia = sugerencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }
    
}
