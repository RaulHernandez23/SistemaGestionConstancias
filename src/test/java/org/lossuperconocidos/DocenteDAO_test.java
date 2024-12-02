package org.lossuperconocidos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.lossuperconocidos.sistemagestionconstancias.daos.DocenteDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;

import java.util.HashMap;

public class DocenteDAO_test {

    //#region registrarDocente
    @Test
    void registrarDocente_UsuarioConCamposVacios_LanzaExcepcion() {
        // Preparación
        Usuario usuarioConCamposVacios = new Usuario();
        usuarioConCamposVacios.setNo_personal(""); // Campo vacío
        usuarioConCamposVacios.setNombre("Juan");
        usuarioConCamposVacios.setApellidoPaterno("Pérez");
        usuarioConCamposVacios.setApellidoMaterno("López");
        usuarioConCamposVacios.setCorreoElectronico("juan.perez@correo.com");
        usuarioConCamposVacios.setContrasena("password123");
        usuarioConCamposVacios.setIdCategoria(1);
        usuarioConCamposVacios.setIdTipoContratacion(2);

        // Actúa y verifica
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            DocenteDAO.registrarDocente(usuarioConCamposVacios);
        });
    }
    @Test
    void registrarDocente_UsuarioNulo_LanzaExcepcion() {
        // Actúa y Verifica
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            DocenteDAO.registrarDocente(null);
        });
    }
    @Test
    void registrarDocente_DocenteAleatorio() {
        // Arrange
        Usuario docente = Utilidad.generarDocenteAleatorio();

        // Actúa
        HashMap<String, Object> resultado = DocenteDAO.registrarDocente(docente);

        // Verifica
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertFalse((Boolean) resultado.get(DocenteDAO.ERROR_KEY), "No debería haber error en el registro");
    }

    @Test
    void registrarDocente_RegistroDuplicado() {
        // Arrange
        Usuario docente = Utilidad.generarDocenteAleatorio();
        DocenteDAO.registrarDocente(docente); // Primer registro

        // Actúa
        HashMap<String, Object> resultado = DocenteDAO.registrarDocente(docente); // Segundo registro duplicado

        // Verifica
        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertFalse((Boolean) resultado.get(DocenteDAO.ERROR_KEY), "No debería haber error en el registro duplicado");

    }
    //endregion

    //#region verificarDocente
    @Test
    void verificarDocente_DocenteExiste() {
        //PRECONDCION: Debe existir el docente
        // Actúa
        HashMap<String, Object> resultado = DocenteDAO.verificarDocente("66666");

        // Verifica
        assertTrue((Boolean) resultado.get(DocenteDAO.ERROR_KEY), "No debería haber error");
        assertEquals("El docente ya se encuentra registrado.", resultado.get(DocenteDAO.MESSAGE_KEY));
    }

    @Test
    void verificarDocente_DocenteNoExiste() {
        //PRECONDCION: No debe existir el docente
        // Actúa
        HashMap<String, Object> resultado = DocenteDAO.verificarDocente("99999");

        // Verifica
        assertFalse((Boolean) resultado.get(DocenteDAO.ERROR_KEY), "Debería haber error");
        assertEquals("El usuario no está registrado.", resultado.get(DocenteDAO.MESSAGE_KEY));
    }
    //#endregion
}
