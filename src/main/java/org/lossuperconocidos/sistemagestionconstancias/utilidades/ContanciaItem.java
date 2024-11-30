package org.lossuperconocidos.sistemagestionconstancias.utilidades;

public class ContanciaItem {
    public String periodo;
    public String tipo;
    public String nombreCompleto;

    public ContanciaItem(String periodo, String tipo) {
        this.periodo = periodo;
        this.tipo = tipo;
    }

    public ContanciaItem(String periodo, String tipo, String nombreCompleto) {
        this.periodo = periodo;
        this.tipo = tipo;
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
