package org.lossuperconocidos;

import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;
import com.github.javafaker.Faker;

public class UtilidadTest {
    public static Usuario generarDocenteAleatorio() {
        Faker faker = new Faker(); // Crear instancia de Faker
        Usuario docente = new Usuario();

        docente.setNo_personal(faker.number().digits(5)); // Número de personal aleatorio
        docente.setNombre(faker.name().firstName()); // Nombre aleatorio
        docente.setApellidoPaterno(faker.name().lastName()); // Apellido paterno aleatorio
        docente.setApellidoMaterno(faker.name().lastName()); // Apellido materno aleatorio
        docente.setCorreoElectronico(faker.internet().emailAddress()); // Correo aleatorio
        docente.setContrasena(faker.internet().password()); // Contraseña aleatoria
        docente.setIdCategoria(faker.number().numberBetween(1, 4)); // ID de categoría aleatoria
        docente.setIdTipoContratacion(faker.number().numberBetween(1, 3)); // ID de tipo de contratación aleatoria

        return docente;
    }
}
