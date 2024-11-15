package org.lossuperconocidos.sistemagestionconstancias.utilidades;

import java.util.HashMap;
import java.util.Map;

//TODO: En base a los word rellenar esto
public abstract Plantilla {
    Map<String, String> valores;
    String nombreArchivo;

    public abstract String getNombreArchivo() {
        return nombreArchivo;
    }

    public abstract Map<String, String> generarValores(){
        return valores;
    };
}