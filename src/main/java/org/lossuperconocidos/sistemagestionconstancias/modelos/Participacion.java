package org.lossuperconocidos.sistemagestionconstancias.modelos;

import java.util.Date;
//FIXME: Actualizar acorde a la nueva BD o escoger la correcion ya hecha
public class Participacion {

    private String constatacion;
    private Date fechaInicio;
    private Date fechaFin;
    private String tipoParticipacion;
    private int idDocente;
    private String docente;

    public Participacion() {
    }

    public Participacion(
            String constatacion,
            Date fechaInicio,
            Date fechaFin,
            String tipoParticipacion,
            int idDocente,
            String docente
    ) {
        this.constatacion = constatacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoParticipacion = tipoParticipacion;
        this.idDocente = idDocente;
        this.docente = docente;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getConstatacion() {
        return constatacion;
    }

    public void setConstatacion(String constatacion) {
        this.constatacion = constatacion;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipoParticipacion() {
        return tipoParticipacion;
    }

    public void setTipoParticipacion(String tipoParticipacion) {
        this.tipoParticipacion = tipoParticipacion;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }
}
