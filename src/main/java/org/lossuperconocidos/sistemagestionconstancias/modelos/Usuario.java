package org.lossuperconocidos.sistemagestionconstancias.modelos;

import java.util.ArrayList;
import java.util.Arrays;

public class Usuario {
    public static final String SEPARADOR_TIPO_USUARIO = ",";
    public static final String FILTRO_PERSONAL_ADMINISTRATIVO = "ersona";
    public static final String FILTRO_DOCENTE = "ocente";
    public static final String FILTRO_ADMINISTRADOR = "dministrador";
    private String no_personal;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoElectronico;
    private String contrasena;
    private String tipoUsuario;
    private String categoria;
    private String tipoContratacion;
    private Integer idCategoria;
    private Integer idTipoUsuario;
    private Integer idTipoContratacion;

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
            String tipoContratacion,
            Integer idCategoria,
            Integer idTipoUsuario,
            Integer idTipoContratacion

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
        this.idCategoria = idCategoria;
        this.idTipoUsuario = idTipoUsuario;
        this.idTipoContratacion = idTipoContratacion;
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

    public Integer getIdCategoria() { return idCategoria; }

    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public Integer getIdTipoUsuario() { return idTipoUsuario; }

    public void setIdTipoUsuario(Integer idTipoUsuario) { this.idTipoUsuario = idTipoUsuario; }

    public Integer getIdTipoContratacion() { return idTipoContratacion; }

    public void setIdTipoContratacion(Integer idTipoContratacion) { this.idTipoContratacion = idTipoContratacion; }

    @Override
    public String toString() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }

    /*
    * Retorna una lista de privilegios del usuario
    * */
    public ArrayList<String> separaTiposUsuarios(){
        ArrayList<String> tipoUsuarioList = new ArrayList<>();
        if (tipoUsuario == null || tipoUsuario.isEmpty()) {
            return tipoUsuarioList;
        }
        if (tipoUsuario.contains(SEPARADOR_TIPO_USUARIO)) {
            tipoUsuarioList = new ArrayList<>(Arrays.asList(tipoUsuario.split(SEPARADOR_TIPO_USUARIO)));
        } else {
            tipoUsuarioList = new ArrayList<>();
            tipoUsuarioList.add(tipoUsuario);
        }
        return tipoUsuarioList;
    }
}
