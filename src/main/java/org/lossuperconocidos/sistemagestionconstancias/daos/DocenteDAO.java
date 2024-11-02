package org.lossuperconocidos.sistemagestionconstancias.daos;

import org.lossuperconocidos.sistemagestionconstancias.utilidades.ConectorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class DocenteDAO {
    public static HashMap<String, Object> registrarDocente(String nombre, String apellidoPaterno,
                                                           String apellidoMaterno, String correoElectronico,
                                                           String password, Integer idCategoria,
                                                           Integer idTipoContratacion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);

        // Obtención de la conexión a la base de datos
        Connection conexionBD = ConectorBD.obtenerConexion();

        if (conexionBD != null) {
            try {
                // Desactiva el auto-commit para gestionar la transacción
                conexionBD.setAutoCommit(false);

                String consulta = "INSERT INTO USUARIO (nombre, apellido_paterno, apellido_materno," +
                        " correo_electronico, password, id_tipo_usuario, id_categoria, id_tipo_contratacion) " +
                        "VALUES (?, ?, ?, ?, ?, 2, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "nombre = VALUES(nombre), " +
                        "apellido_paterno = VALUES(apellido_paterno), " +
                        "apellido_materno = VALUES(apellido_materno), " +
                        "correo_electronico = VALUES(correo_electronico), " +
                        "password = VALUES(password), " +
                        "id_categoria = VALUES(id_categoria), " +
                        "id_tipo_contratacion = VALUES(id_tipo_contratacion)";

                // Preparación de la sentencia
                PreparedStatement sentenciaUsuario = conexionBD.prepareStatement(consulta);
                sentenciaUsuario.setString(1, nombre);
                sentenciaUsuario.setString(2, apellidoPaterno);
                sentenciaUsuario.setString(3, apellidoMaterno);
                sentenciaUsuario.setString(4, correoElectronico);
                sentenciaUsuario.setString(5, password);
                sentenciaUsuario.setObject(6, idCategoria);
                sentenciaUsuario.setObject(7, idTipoContratacion);

                int filasAfectadas = sentenciaUsuario.executeUpdate();

                if (filasAfectadas > 0) {
                    conexionBD.commit();
                    respuesta.put("error", false);
                    respuesta.put("mensaje", "Docente registrado correctamente.");
                } else {
                    conexionBD.rollback();
                    respuesta.put("mensaje", "No se pudo registrar el docente.");
                }

            } catch (SQLException e) {
                try {
                    conexionBD.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                respuesta.put("mensaje", "Error en la operación de registro: " + e.getMessage());
                e.printStackTrace();
            } finally {
                ConectorBD.cerrarConexion(conexionBD);
            }
        } else {
            respuesta.put("mensaje", "Error al conectar con la base de datos.");
        }

        return respuesta;
    }
}
