package org.lossuperconocidos.sistemagestionconstancias.utilidades;

import java.util.HashMap;
import java.util.Map;

public abstract class Plantilla {
    protected Map<String, String> valores = new HashMap<>();
    private String nombreArchivo;

    // Método abstracto para inicializar valores específicos
    protected abstract void llenarValores();

    public Map<String, String> getValores() {
        return valores;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    // Clase genérica Builder
    public static abstract class Builder<T extends Plantilla, B extends Builder<T, B>> {
        public Map<String, String> valores = new HashMap<>();
        public String nombreArchivo;

        // Método para agregar un valor al mapa
        public B agregarValor(String clave, String valor) {
            valores.put(clave, valor);
            return (B) this; // Retorna el Builder para encadenar
        }

        // Método para configurar el nombre del archivo
        public B setNombreArchivo(String nombreArchivo) {
            this.nombreArchivo = nombreArchivo;
            return (B) this;
        }

        // Método abstracto que cada subclase deberá implementar para construir la instancia concreta
        public abstract T build();
    }
}