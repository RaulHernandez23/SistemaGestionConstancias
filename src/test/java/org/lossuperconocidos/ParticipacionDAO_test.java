package org.lossuperconocidos;


import com.sun.jdi.connect.Connector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.lossuperconocidos.sistemagestionconstancias.daos.ParticipacionDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.ImparticionEE;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Jurado;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Pladea;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Proyecto;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.ConectorBD;

import java.sql.Connection;
import java.sql.Date;
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

    @Test
    void testRegistrarJuradoDocenteNoEncontrado() {
        Jurado jurado = new Jurado("D-111111",
                "Agosto - Enero 24/25",
                "Trabajo de jurado",
                Date.valueOf("2024-11-29"),
                "Tesis",
                "Miguel Ángel Morales Cruz, Raúl Hernández Olivares",
                "Aprobativo"
        );

        HashMap<String, Object> resultado = ParticipacionDAO.registrarJurado(jurado);
        assertTrue((Boolean) resultado.get("error"), "Se esperaba que el resultado tuviera errores");
    }

    @Test
    void testRegistrarJuradoPeriodoEscolarNoEncontrado() {
        Jurado jurado = new Jurado("D-010101",
                "Agosto - Agosto",
                "Trabajo de jurado",
                Date.valueOf("2024-11-29"),
                "Tesis",
                "Miguel Ángel Morales Cruz, Raúl Hernández Olivares",
                "Aprobativo"
        );

        HashMap<String, Object> resultado = ParticipacionDAO.registrarJurado(jurado);
        assertTrue((Boolean) resultado.get("error"), "Se esperaba que el resultado tuviera errores");
    }

    @Test
    void registrarJuradoDatosNulos() {
        Jurado jurado = new Jurado("D-010101",
                "Agosto - Enero 24/25",
                null,
                Date.valueOf("2024-11-29"),
                null,
                "Miguel Ángel Morales Cruz, Raúl Hernández Olivares",
                "Aprobativo"
        );

        HashMap<String, Object> resultado = ParticipacionDAO.registrarJurado(jurado);
        assertTrue((Boolean) resultado.get("error"), "Se esperaba que el resultado tuviera errores");
    }

    @Test
    void testRegistrarJuradoExitoso() {
        Jurado jurado = new Jurado("D-010101",
                "Agosto - Enero 24/25",
                "Trabajo de jurado",
                Date.valueOf("2024-11-29"),
                "Tesis",
                "Miguel Ángel Morales Cruz, Raúl Hernández Olivares",
                "Aprobativo"
                );

        HashMap<String, Object> resultado = ParticipacionDAO.registrarJurado(jurado);
        assertFalse((Boolean) resultado.get("error"), "Se esperaba que el resultado no tuviera errores");
        assertEquals("La participación se ha registrado exitosamente", resultado.get("mensaje"));
    }

    @Test
    void registrarPladeaDocenteNoEncontrado() {
        Pladea pladea = new Pladea("D-111111",
                "Agosto - Enero 24/25",
                "Mejora en el proceso",
                "Eje estrategico",
                "Metas del pladea",
                "Objetivos generales del pladea",
                "Programa estrategico del pladea"
        );

        HashMap<String, Object> resultado = ParticipacionDAO.registrarPladea(pladea);
        assertTrue((Boolean) resultado.get("error"), "Se esperaba que el resultado tuviera errores");
    }

    @Test
    void registrarPladeaPeriodoEscolarNoEncontrado() {
        Pladea pladea = new Pladea("D-010101",
                "Agosto - Agosto",
                "Mejora en el proceso",
                "Eje estrategico",
                "Metas del pladea",
                "Objetivos generales del pladea",
                "Programa estrategico del pladea"
        );

        HashMap<String, Object> resultado = ParticipacionDAO.registrarPladea(pladea);
        assertTrue((Boolean) resultado.get("error"), "Se esperaba que el resultado tuviera errores");
    }

    @Test
    void registrarPladeaDatosNulos() {
        Pladea pladea = new Pladea("D-010101",
                "Agosto - Enero 24/25",
                "Mejora en el proceso",
                "Eje estrategico",
                null,
                null,
                null
        );

        HashMap<String, Object> resultado = ParticipacionDAO.registrarPladea(pladea);
        assertTrue((Boolean) resultado.get("error"), "Se esperaba que el resultado tuviera errores");
    }

    @Test
    void testRegistrarPladeaExitoso() {
        Pladea pladea = new Pladea("D-010101",
                "Agosto - Enero 24/25",
                "Mejora en el proceso",
                "Eje estrategico",
                "Metas del pladea",
                "Objetivos generales del pladea",
                "Programa estrategico del pladea"
        );

        HashMap<String, Object> resultado = ParticipacionDAO.registrarPladea(pladea);
        assertFalse((Boolean) resultado.get("error"), "Se esperaba que el resultado no tuviera errores");
        assertEquals("La participación se ha registrado exitosamente", resultado.get("mensaje"));
    }

    @Test
    void testRegistrarProyectoDocenteNoEncontrado() {
        Proyecto proyecto = new Proyecto("D-111111",
                "Agosto - Enero 24/25",
                "Proyecto de investigación",
                "Impacto obtenido por el proyecto",
                "Lugar del proyecto",
                "Miguel Angel Morales Cruz, Raul Hernandez Olivares");

        HashMap<String, Object> resultado = ParticipacionDAO.registrarProyecto(proyecto);
        assertTrue((Boolean) resultado.get("error"), "Se esperaba que el resultado tuviera errores");
    }

    @Test
    void testRegistrarProyectoPeriodoEscolarNoEncontrado() {
        Proyecto proyecto = new Proyecto("D-010101",
                "Agosto - Agosto",
                "Proyecto de investigación",
                "Impacto obtenido por el proyecto",
                "Lugar del proyecto",
                "Miguel Angel Morales Cruz, Raul Hernandez Olivares");

        HashMap<String, Object> resultado = ParticipacionDAO.registrarProyecto(proyecto);
        assertTrue((Boolean) resultado.get("error"), "Se esperaba que el resultado tuviera errores");
    }

    @Test
    void testRegistrarProyectoDatosNulos() {
        Proyecto proyecto = new Proyecto("D-010101",
                "Agosto - Enero 24/25",
                "Proyecto de investigación",
                null,
                null,
                "Miguel Angel Morales Cruz, Raul Hernandez Olivares");

        HashMap<String, Object> resultado = ParticipacionDAO.registrarProyecto(proyecto);
        assertTrue((Boolean) resultado.get("error"), "Se esperaba que el resultado tuviera errores");
    }

    @Test
    void testRegistrarProyectoExitoso() {
        Proyecto proyecto = new Proyecto("D-010101",
                "Agosto - Enero 24/25",
                "Proyecto de investigación",
                "Impacto obtenido por el proyecto",
                "Lugar del proyecto",
                "Miguel Angel Morales Cruz, Raul Hernandez Olivares");

        HashMap<String, Object> resultado = ParticipacionDAO.registrarProyecto(proyecto);
        assertFalse((Boolean) resultado.get("error"), "Se esperaba que el resultado no tuviera errores");
        assertEquals("La participación se ha registrado exitosamente", resultado.get("mensaje"));
    }

}
