package org.lossuperconocidos;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.lossuperconocidos.sistemagestionconstancias.daos.PeriodoEscolarDAO;
import org.lossuperconocidos.sistemagestionconstancias.daos.UsuarioDAO;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Categoria;
import org.lossuperconocidos.sistemagestionconstancias.modelos.TipoContratacion;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Constantes;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static javafx.beans.binding.Bindings.when;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDAO_test {

    @Test
    void consultarTiposContratacion_Exitoso() {
        // Actúa
        HashMap<String, Object> resultado = UsuarioDAO.consultarTiposContratacion();

        // Verifica
        assertFalse((Boolean) resultado.get("error"), "No debería haber error");
        assertNotNull(resultado.get("contractTypes"), "La lista de tipos de contratación no debería ser nula");

        @SuppressWarnings("unchecked")
        List<TipoContratacion> tiposContratacion = (List<TipoContratacion>) resultado.get("contractTypes");
        assertFalse(tiposContratacion.isEmpty(), "La lista de tipos de contratación no debería estar vacía");

        tiposContratacion.forEach(tipo -> {
            assertNotNull(tipo.getIdTipoContratacion(), "El ID del tipo de contratación no debería ser nulo");
            assertNotNull(tipo.getNombreTipoContratacion(), "El nombre del tipo de contratación no debería ser nulo");
        });
    }
    @Test
    void iniciarSesion_FalloCredenciales() {
        // Datos de entrada incorrectos
        String noPersonal = "usuario_no_existente";
        String contrasena = "contrasena_erronea";

        // Actúa
        HashMap<String, Object> resultado = UsuarioDAO.iniciarSesion(noPersonal, contrasena);

        // Verifica
        assertTrue((Boolean) resultado.get(UsuarioDAO.ERROR_KEY), "Debería haber un error");
        assertEquals("Usuario y/o contraseña incorrectos. Por favor, verifique sus datos",
                resultado.get(UsuarioDAO.MESSAGE_KEY), "El mensaje debería indicar credenciales incorrectas");
        assertNull(resultado.get(UsuarioDAO.USER_KEY), "El usuario no debería estar presente en el resultado");
    }

    @Test
    void iniciarSesion_ErrorConexion() {
        // Simular un entorno donde no es posible obtener una conexión a la base de datos
        // Puedes desactivar la base de datos o alterar las credenciales de conexión

        // Datos de entrada
        String noPersonal = "usuario_cualquiera";
        String contrasena = "contrasena_cualquiera";

        // Actúa
        HashMap<String, Object> resultado = UsuarioDAO.iniciarSesion(noPersonal, contrasena);

        // Verifica
        assertTrue((Boolean) resultado.get(UsuarioDAO.ERROR_KEY), "Debería haber un error");
        assertEquals("No se pudo conectar a la red, por favor revise su conexión",
                resultado.get(UsuarioDAO.MESSAGE_KEY), "El mensaje debería indicar problemas de conexión");
        assertNull(resultado.get(UsuarioDAO.USER_KEY), "El usuario no debería estar presente en el resultado");
    }

    @Test
    void consultarCategorias_ErrorConexion() {
        // Simular desconexión de la base de datos para este caso

        // Actúa
        HashMap<String, Object> resultado = UsuarioDAO.consultarCategorias();

        // Verifica que hay error en la conexión
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertTrue((Boolean) resultado.get(UsuarioDAO.ERROR_KEY), "Debería haber un error en la conexión");
        assertEquals(Constantes.MENSAJE_ERROR_REGISTRO, resultado.get(UsuarioDAO.MESSAGE_KEY),
                "El mensaje debería indicar un problema de conexión");
        assertNull(resultado.get(UsuarioDAO.CATEGORIES_KEY), "La lista de categorías debería ser nula en caso de error");
    }
    @Test
    void consultarCategorias_SinDatos() {
        // Preparar base de datos sin categorías para esta prueba

        // Actúa
        HashMap<String, Object> resultado = UsuarioDAO.consultarCategorias();

        // Verifica que no hay error, pero la lista está vacía
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertFalse((Boolean) resultado.get(UsuarioDAO.ERROR_KEY), "No debería haber error en la consulta");
        Object categoriasObj = resultado.get(UsuarioDAO.CATEGORIES_KEY);
        assertNotNull(categoriasObj, "La lista de categorías no debería ser nula");
        assertTrue(categoriasObj instanceof List, "El resultado debería ser una lista de categorías");

        @SuppressWarnings("unchecked")
        List<Categoria> categorias = (List<Categoria>) categoriasObj;
        assertTrue(categorias.isEmpty(), "La lista de categorías debería estar vacía cuando no hay datos");
    }


}
