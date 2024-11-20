package org.lossuperconocidos.sistemagestionconstancias.modelos;

import org.lossuperconocidos.sistemagestionconstancias.utilidades.Plantilla;

import java.util.HashMap;
import java.util.Map;


public class PlantillaProyecto extends Plantilla {

    // Constructor privado para obligar a usar el Builder
    private PlantillaProyecto(Builder builder) {
        this.valores = builder.valores;
        this.setNombreArchivo(builder.nombreArchivo);
    }

    @Override
    protected void llenarValores() {
        // Implementa valores específicos para esta plantilla si es necesario
        if (valores.isEmpty()) {
            valores.put("ClaveEjemplo", "ValorEjemplo");
        }
    }

    // Builder específico para PlantillaProyecto
    public static class Builder extends Plantilla.Builder<PlantillaProyecto, Builder> {

        @Override
        public PlantillaProyecto build() {
            return new PlantillaProyecto(this);
        }
    }
}