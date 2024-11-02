package org.lossuperconocidos.sistemagestionconstancias.daos;

import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.ConectorBD;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Constantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class DocenteDAO {
    public static HashMap<String, Object> registrarDocente(Usuario docente) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);

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

                PreparedStatement sentenciaUsuario = conexionBD.prepareStatement(consulta);
                sentenciaUsuario.setString(1, docente.getNombre());
                sentenciaUsuario.setString(2, docente.getApellidoPaterno());
                sentenciaUsuario.setString(3, docente.getApellidoMaterno());
                sentenciaUsuario.setString(4, docente.getCorreoElectronico());
                sentenciaUsuario.setString(5, docente.getContrasena());
                sentenciaUsuario.setObject(6, docente.getIdCategoria());
                sentenciaUsuario.setObject(7, docente.getIdTipoContratacion());

                int filasAfectadas = sentenciaUsuario.executeUpdate();

                if (filasAfectadas > 0) {
                    conexionBD.commit();
                    respuesta.put("error", false);
                    respuesta.put("mensaje", "La información del docente " + docente.getNombre()
                            + " se ha registrado correctamente.");
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
                respuesta.put("mensaje", "Error en la operación de registro");
                e.printStackTrace();
            } finally {
                ConectorBD.cerrarConexion(conexionBD);
            }
        } else {
            respuesta.put("mensaje", Constantes.MENSAJE_ERROR_DE_CONEXION);
        }

        return respuesta;
    }
}
