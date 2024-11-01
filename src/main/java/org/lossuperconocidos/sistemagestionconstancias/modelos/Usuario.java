package org.lossuperconocidos.sistemagestionconstancias.modelos;

public class Usuario {

    private String no_personal;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoElectronico;
    private String contrasena;
    private String tipoUsuario;
    private String categoria;
    private String tipoContratacion;

    public Usuario() {
    }

    public Usuario(
            String no_personal,
            String nombre,
            String apellidoPaterno,
            String apellidoMaterno,
            String correoElectronico,
            String contrasena,
            String tipoUsuario,
            String categoria,
            String tipoContratacion
    ) {
        this.no_personal = no_personal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.categoria = categoria;
        this.tipoContratacion = tipoContratacion;
    }

    public String getNo_personal() {
        return no_personal;
    }

    public void setNo_personal(String no_personal) {
        this.no_personal = no_personal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipoContratacion() {
        return tipoContratacion;
    }

    public void setTipoContratacion(String tipoContratacion) {
        this.tipoContratacion = tipoContratacion;
    }

}
