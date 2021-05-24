/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.pojo;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Gabriel
 */
public class Empleado {
    int id;
    String nombre;
    String apellidos;
    java.sql.Date contrato;
    java.sql.Date salida;
    double salario;
    int idTipoContrato;
    String mail;
    String pwd;
    String perfil;
    ArrayList<String> secciones;
    
    public Empleado(int id, String nombre, String apellidos, Date contrato, Date salida, double salario, int idTipoContrato, String mail, String pwd) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contrato = contrato;
        this.salida = salida;
        this.salario = salario;
        this.idTipoContrato = idTipoContrato;
        this.mail = mail;
        this.pwd = pwd;
    }

    public Empleado(String nombre, String apellidos, Date contrato, Date salida, double salario, int idTipoContrato, String mail, String pwd) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contrato = contrato;
        this.salida = salida;
        this.salario = salario;
        this.idTipoContrato = idTipoContrato;
        this.mail = mail;
        this.pwd = pwd;
    }
    
     public Empleado (int id, String nombre, String apellidos){
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
     
    public Empleado (int id, String nombre, String apellidos, String perfil){
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.perfil = perfil;
    }
    
    public Empleado (int id, String nombre, String apellidos, ArrayList<String> secciones){
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.secciones = secciones;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getContrato() {
        return contrato;
    }

    public void setContrato(Date contrato) {
        this.contrato = contrato;
    }

    public Date getSalida() {
        return salida;
    }

    public void setSalida(Date salida) {
        this.salida = salida;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getIdTipoContrato() {
        return idTipoContrato;
    }

    public void setIdTipoContrato(int idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public ArrayList<String> getSecciones() {
        return secciones;
    }

    public void setSecciones(ArrayList<String> secciones) {
        this.secciones = secciones;
    }
    
    
}
