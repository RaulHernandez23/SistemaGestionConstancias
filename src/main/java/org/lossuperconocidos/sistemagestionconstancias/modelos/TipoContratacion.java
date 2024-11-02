package org.lossuperconocidos.sistemagestionconstancias.modelos;

public class TipoContratacion {
    Integer idTipoContratacion;
    String nombreTipoContratacion;

    public TipoContratacion() {

    }

    public TipoContratacion(Integer idTipoContratacion, String nombreTipoContratacion) {
        this.idTipoContratacion = idTipoContratacion;
        this.nombreTipoContratacion = nombreTipoContratacion;
    }

    public Integer getIdTipoContratacion() {
        return idTipoContratacion;
    }

    public void setIdTipoContratacion(Integer idTipoContratacion) {
        this.idTipoContratacion = idTipoContratacion;
    }

    public String getNombreTipoContratacion() {
        return nombreTipoContratacion;
    }

    public void setNombreTipoContratacion(String nombreTipoContratacion) {
        this.nombreTipoContratacion = nombreTipoContratacion;
    }

    @Override
    public String toString() {
        return nombreTipoContratacion;
    }
}
