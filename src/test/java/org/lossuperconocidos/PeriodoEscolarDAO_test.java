package org.lossuperconocidos;

import org.junit.jupiter.api.Test;
import org.lossuperconocidos.sistemagestionconstancias.daos.PeriodoEscolarDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.PeriodoEscolar;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;

public class PeriodoEscolarDAO_test {
    //#region recuperarPeriodosEscolares
    @Test
    void recuperarPeriodosEscolares_Exitoso() {
        // Actúa
        HashMap<String, Object> resultado = PeriodoEscolarDAO.recuperarPeriodosEscolares();

        // Verifica
        assertFalse((Boolean) resultado.get(PeriodoEscolarDAO.ERROR_KEY), "No debería haber error");
        assertNotNull(resultado.get(PeriodoEscolarDAO.PERIODOS_KEY), "La lista de periodos no debería ser nula");

        @SuppressWarnings("unchecked")
        List<PeriodoEscolar> periodos = (List<PeriodoEscolar>) resultado.get(PeriodoEscolarDAO.PERIODOS_KEY);
        assertFalse(periodos.isEmpty(), "La lista de periodos no debería estar vacía");

        periodos.forEach(periodo -> {
            assertNotNull(periodo.getId(), "El ID del periodo no debería ser nulo");
            assertNotNull(periodo.getNombre(), "El nombre del periodo no debería ser nulo");
            assertNotNull(periodo.getFechaInicio(), "La fecha de inicio del periodo no debería ser nula");
            assertNotNull(periodo.getFechaFin(), "La fecha de fin del periodo no debería ser nula");
        });
    }
    //#endregion recuperarPeriodosEscolares

}
