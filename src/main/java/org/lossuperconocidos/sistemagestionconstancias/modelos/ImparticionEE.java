package org.lossuperconocidos.sistemagestionconstancias.modelos;

public class ImparticionEE extends Participacion {
    private String experienciaEducativa;
    private String programaEducativo;
    private String bloque;
    private int creditos;
    private int horas;
    private int meses;
    private int seccion;
    private int semanas;

    public ImparticionEE(String noPersonal,
                         String periodoEscolar,
                         String experienciaEducativa,
                         String programaEducativo,
                         String bloque,
                         int creditos,
                         int horas,
                         int meses,
                         int seccion,
                         int semanas) {
        super("Imparticion de Experiencia Educativa", noPersonal, periodoEscolar);
        this.experienciaEducativa = experienciaEducativa;
        this.programaEducativo = programaEducativo;
        this.bloque = bloque;
        this.creditos = creditos;
        this.horas = horas;
        this.meses = meses;
        this.seccion = seccion;
        this.semanas = semanas;
    }

    public String getExperienciaEducativa() {
        return experienciaEducativa;
    }

    public void setExperienciaEducativa(String experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
    }

    public String getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMeses() {
        return meses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    public int getSeccion() {
        return seccion;
    }

    public void setSeccion(int seccion) {
        this.seccion = seccion;
    }

    public int getSemanas() {
        return semanas;
    }

    public void setSemanas(int semanas) {
        this.semanas = semanas;
    }
}
