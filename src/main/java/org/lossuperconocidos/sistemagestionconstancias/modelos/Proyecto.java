package org.lossuperconocidos.sistemagestionconstancias.modelos;

public class Proyecto extends Participacion {
    private String proyectoRealizado;
    private String impactoObtenido;
    private String lugar;
    private String nombreAlumnos;
    private String nombreDocente;

    public Proyecto(String noPersonal,
                    String periodoEscolar,
                    String proyectoRealizado,
                    String impactoObtenido,
                    String lugar,
                    String nombreAlumnos) {
        super(noPersonal, periodoEscolar);
        this.proyectoRealizado = proyectoRealizado;
        this.impactoObtenido = impactoObtenido;
        this.lugar = lugar;
        this.nombreAlumnos = nombreAlumnos;
    }

    public Proyecto(String noPersonal,
                    String nombreDocente,
                    String periodoEscolar,
                    String proyectoRealizado,
                    String impactoObtenido,
                    String lugar,
                    String nombreAlumnos) {
        super(noPersonal, periodoEscolar);
        this.proyectoRealizado = proyectoRealizado;
        this.impactoObtenido = impactoObtenido;
        this.lugar = lugar;
        this.nombreAlumnos = nombreAlumnos;
        this.nombreDocente = nombreDocente;
    }

    public String getProyectoRealizado() {
        return proyectoRealizado;
    }

    public void setProyectoRealizado(String proyectoRealizado) {
        this.proyectoRealizado = proyectoRealizado;
    }

    public String getImpactoObtenido() {
        return impactoObtenido;
    }

    public void setImpactoObtenido(String impactoObtenido) {
        this.impactoObtenido = impactoObtenido;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getNombreAlumnos() {
        return nombreAlumnos;
    }

    public void setNombreAlumnos(String nombreAlumnos) {
        this.nombreAlumnos = nombreAlumnos;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

}
