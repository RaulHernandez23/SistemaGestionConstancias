package org.lossuperconocidos;


import com.sun.jdi.connect.Connector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.lossuperconocidos.sistemagestionconstancias.daos.ParticipacionDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.ImparticionEE;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.ConectorBD;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ParticipacionDAO_test {

    private static Connection conexion = ConectorBD.obtenerConexion();

    @Test
    void testRecuperarProgramasExitoso() throws Exception {
        HashMap<String, Object> resultado = ParticipacionDAO.recuperarProgramas();

        assertFalse((Boolean) resultado.get("error"), "Se esperaba que el resultado no tuviera errores");

        assertFalse(((ArrayList<String>) resultado.get("programas")).isEmpty(), "La lista de programas no debe estar vacía");
    }

    @Test
    void testRegistrarImparticionEEDocenteNoEncontrado() {
        ImparticionEE imparticion = new ImparticionEE("D-111111",
                "Agosto - Enero 24/25",
                "Administración de proyectos",
                "Ingeniería de software",
                "Matutino",
                10,
                100,
                6,
                2,
                10);

        HashMap<String, Object> resultado = ParticipacionDAO.registrarImparticionEE(imparticion);
        assertTrue((Boolean) resultado.get("error"), "Se esperaba que el resultado tuviera errores");
    }

    @Test
    void registrarImparticionEEPeriodoEscolarNoEncontrado() {
        ImparticionEE imparticion = new ImparticionEE("D-010101",
                "Febrero - Febrero",
                "Administración de proyectos",
                "Ingeniería de software",
                "Matutino",
                10,
                100,
                6,
                2,
                10);

        HashMap<String, Object> resultado = ParticipacionDAO.registrarImparticionEE(imparticion);
        assertTrue((Boolean) resultado.get("error"), "Se esperaba que el resultado tuviera errores");
    }

    @Test
    void registrarImparticionEEProgramaEducativoNoEncontrado() {
        ImparticionEE imparticion = new ImparticionEE("D-010101",
                "Agosto - Enero 24/25",
                "Administración de proyectos",
                "Derecho",
                "Matutino",
                10,
                100,
                6,
                2,
                10);

        HashMap<String, Object> resultado = ParticipacionDAO.registrarImparticionEE(imparticion);
        assertTrue((Boolean) resultado.get("error"), "Se esperaba que el resultado tuviera errores");
    }

    @Test
    void registrarImparticionEEDatosNulos() {
        ImparticionEE imparticion = new ImparticionEE("D-010101",
                "Agosto - Enero 24/25",
                null,
                "Ingeniería de software",
                null,
                10,
                100,
                6,
                2,
                10);

        HashMap<String, Object> resultado = ParticipacionDAO.registrarImparticionEE(imparticion);
        assertTrue((Boolean) resultado.get("error"), "Se esperaba que el resultado tuviera errores");
    }

    @Test
    void testRegistrarImparticionEEExitoso() {
        ImparticionEE imparticion = new ImparticionEE("D-010101",
                "Agosto - Enero 24/25",
                "Administración de proyectos",
                "Ingeniería de software",
                "Matutino",
                10,
                100,
                6,
                2,
                10);

        HashMap<String, Object> resultado = ParticipacionDAO.registrarImparticionEE(imparticion);

        assertFalse((Boolean) resultado.get("error"), "Se esperaba que el resultado no tuviera errores");
        assertEquals("La participación se ha registrado exitosamente", resultado.get("mensaje"));
    }

}
