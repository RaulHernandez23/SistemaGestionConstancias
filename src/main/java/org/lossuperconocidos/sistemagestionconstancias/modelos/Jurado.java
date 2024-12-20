package org.lossuperconocidos.sistemagestionconstancias.modelos;

import java.sql.Date;

public class Jurado extends Participacion{
    private String tituloTrabajo;
    private Date fechaPresentacion;
    private String modalidad;
    private String nombreAlumnos;
    private String resultadoObtenido;
    private String nombreDocente;
    private String programaEducativo;

    public Jurado(String noPersonal,
                  String periodoEscolar,
                  String tituloTrabajo,
                  Date fechaPresentacion,
                  String modalidad,
                  String nombreAlumnos,
                  String resultadoObtenido) {
        super(noPersonal, periodoEscolar);
        this.tituloTrabajo = tituloTrabajo;
        this.fechaPresentacion = fechaPresentacion;
        this.modalidad = modalidad;
        this.nombreAlumnos = nombreAlumnos;
        this.resultadoObtenido = resultadoObtenido;
    }

    public Jurado(String noPersonal,
                  String nombreDocente,
                  String periodoEscolar,
                  String tituloTrabajo,
                  Date fechaPresentacion,
                  String modalidad,
                  String nombreAlumnos,
                  String resultadoObtenido) {
        super(noPersonal, periodoEscolar);
        this.tituloTrabajo = tituloTrabajo;
        this.fechaPresentacion = fechaPresentacion;
        this.modalidad = modalidad;
        this.nombreAlumnos = nombreAlumnos;
        this.resultadoObtenido = resultadoObtenido;
        this.nombreDocente = nombreDocente;
    }

    public String getTituloTrabajo() {
        return tituloTrabajo;
    }

    public void setTituloTrabajo(String tituloTrabajo) {
        this.tituloTrabajo = tituloTrabajo;
    }

    public Date getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getNombreAlumnos() {
        return nombreAlumnos;
    }

    public void setNombreAlumnos(String nombreAlumnos) {
        this.nombreAlumnos = nombreAlumnos;
    }

    public String getResultadoObtenido() {
        return resultadoObtenido;
    }

    public void setResultadoObtenido(String resultadoObtenido) {
        this.resultadoObtenido = resultadoObtenido;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

}
