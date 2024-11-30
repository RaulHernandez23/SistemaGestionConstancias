package org.lossuperconocidos.sistemagestionconstancias.modelos;
import java.util.Date;

public class ParticipacionUsuario {
    private int id;
    private String tipoParticipacion;
    private int docenteId;
    private int periodoEscolarId;
    private String periodoEscolarNombre;
    private Date fechaInicio;
    private Date fechaFin;
    private String nombreUsuario;
    private String apellido_paterno;
    private String apellido_materno;

    public ParticipacionUsuario() {
    }

    public ParticipacionUsuario(int id, String tipoParticipacion, int docenteId, int periodoEscolarId, String periodoEscolarNombre, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.tipoParticipacion = tipoParticipacion;
        this.docenteId = docenteId;
        this.periodoEscolarId = periodoEscolarId;
        this.periodoEscolarNombre = periodoEscolarNombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public ParticipacionUsuario(int id, String tipoParticipacion, int docenteId, int periodoEscolarId, String periodoEscolarNombre, Date fechaInicio, Date fechaFin, String nombreUsuario, String apellido_paterno, String apellido_materno) {
        this.id = id;
        this.tipoParticipacion = tipoParticipacion;
        this.docenteId = docenteId;
        this.periodoEscolarId = periodoEscolarId;
        this.periodoEscolarNombre = periodoEscolarNombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nombreUsuario = nombreUsuario;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellidoMaterno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellidoPaterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoParticipacion() {
        return tipoParticipacion;
    }

    public void setTipoParticipacion(String tipoParticipacion) {
        this.tipoParticipacion = tipoParticipacion;
    }

    public int getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(int docenteId) {
        this.docenteId = docenteId;
    }

    public int getPeriodoEscolarId() {
        return periodoEscolarId;
    }

    public void setPeriodoEscolarId(int periodoEscolarId) {
        this.periodoEscolarId = periodoEscolarId;
    }

    public String getPeriodoEscolarNombre() {
        return periodoEscolarNombre;
    }

    public void setPeriodoEscolarNombre(String periodoEscolarNombre) {
        this.periodoEscolarNombre = periodoEscolarNombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNombreCompleto() {
        return nombreUsuario + " " + apellido_paterno + " " + apellido_materno;
    }

}
