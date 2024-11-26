package org.lossuperconocidos.sistemagestionconstancias.modelos;

public class Participacion {
    private String tipoParticipacion;
    private String no_personal;
    private String periodoEscolar;

    public Participacion(String tipoParticipacion, String no_personal, String periodoEscolar) {
        this.tipoParticipacion = tipoParticipacion;
        this.no_personal = no_personal;
        this.periodoEscolar = periodoEscolar;
    }

    public String getTipoParticipacion() {
        return tipoParticipacion;
    }

    public void setTipoParticipacion(String tipoParticipacion) {
        this.tipoParticipacion = tipoParticipacion;
    }

    public String getNoPersonal() {
        return no_personal;
    }

    public void setNoPersonal(String no_personal) {
        this.no_personal = no_personal;
    }

    public String getPeriodoEscolar() {
        return periodoEscolar;
    }

    public void setPeriodoEscolar(String periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }
}
