package org.lossuperconocidos.sistemagestionconstancias.utilidades;

public class ContanciaItem {
    public String periodo;
    public String tipo;

    public ContanciaItem(String periodo, String tipo) {
        this.periodo = periodo;
        this.tipo = tipo;
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
