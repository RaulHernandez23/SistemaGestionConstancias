package org.lossuperconocidos.sistemagestionconstancias.modelos;
import java.util.Date;

//TODO: Verificar
public class ParticipacionCorregido {
    private int id;
    private String tipoParticipacion;
    private int docenteId;
    private int periodoEscolarId;
    private String periodoEscolarNombre;
    private Date fechaInicio;
    private Date fechaFin;

    public ParticipacionCorregido() {
    }

    public ParticipacionCorregido(int id, String tipoParticipacion, int docenteId, int periodoEscolarId, String periodoEscolarNombre, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.tipoParticipacion = tipoParticipacion;
        this.docenteId = docenteId;
        this.periodoEscolarId = periodoEscolarId;
        this.periodoEscolarNombre = periodoEscolarNombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

}
