package org.lossuperconocidos.sistemagestionconstancias.modelos;

public class Pladea extends Participacion {
    private String acciones;
    private String ejeEstrategico;
    private String metas;
    private String objetivosGenerales;
    private String programaEstrategico;

    public Pladea(String noPersonal,
                  String periodoEscolar,
                  String acciones,
                  String ejeEstrategico,
                  String metas,
                  String objetivosGenerales,
                  String programaEstrategico) {
        super(noPersonal, periodoEscolar);
        this.acciones = acciones;
        this.ejeEstrategico = ejeEstrategico;
        this.metas = metas;
        this.objetivosGenerales = objetivosGenerales;
        this.programaEstrategico = programaEstrategico;
    }

    public String getAcciones() {
        return acciones;
    }

    public void setAcciones(String acciones) {
        this.acciones = acciones;
    }

    public String getEjeEstrategico() {
        return ejeEstrategico;
    }

    public void setEjeEstrategico(String ejeEstrategico) {
        this.ejeEstrategico = ejeEstrategico;
    }

    public String getMetas() {
        return metas;
    }

    public void setMetas(String metas) {
        this.metas = metas;
    }

    public String getObjetivosGenerales() {
        return objetivosGenerales;
    }

    public void setObjetivosGenerales(String objetivosGenerales) {
        this.objetivosGenerales = objetivosGenerales;
    }

    public String getProgramaEstrategico() {
        return programaEstrategico;
    }

    public void setProgramaEstrategico(String programaEstrategico) {
        this.programaEstrategico = programaEstrategico;
    }
}
