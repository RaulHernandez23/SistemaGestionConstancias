package org.lossuperconocidos.sistemagestionconstancias.daos;

import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.ConectorBD;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Constantes;

import java.sql.*;
import java.util.HashMap;

public class DocenteDAO {
    public static final String ERROR_KEY = "error";
    public static final String MESSAGE_KEY = "mensaje";


    public static HashMap<String, Object> registrarDocente(Usuario docente) {
        validarUsuario(docente);
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(ERROR_KEY, true);

        Connection conexionBD = ConectorBD.obtenerConexion();

        if (conexionBD != null) {
            try {
                // Desactiva el auto-commit para gestionar la transacción
                conexionBD.setAutoCommit(false);

                // Consulta para insertar o actualizar el docente
                String consultaDocente = "INSERT INTO USUARIO (no_personal, nombre, apellido_paterno, apellido_materno, " +
                        "correo_electronico, password, categoria_id, tipo_contratacion_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "no_personal = VALUES(no_personal), " +
                        "nombre = VALUES(nombre), " +
                        "apellido_paterno = VALUES(apellido_paterno), " +
                        "apellido_materno = VALUES(apellido_materno), " +
                        "correo_electronico = VALUES(correo_electronico), " +
                        "password = VALUES(password), " +
                        "categoria_id = VALUES(categoria_id), " +
                        "tipo_contratacion_id = VALUES(tipo_contratacion_id)";

                PreparedStatement sentenciaUsuario = conexionBD.prepareStatement(consultaDocente, Statement.RETURN_GENERATED_KEYS);
                sentenciaUsuario.setString(1, docente.getNo_personal());
                sentenciaUsuario.setString(2, docente.getNombre());
                sentenciaUsuario.setString(3, docente.getApellidoPaterno());
                sentenciaUsuario.setString(4, docente.getApellidoMaterno());
                sentenciaUsuario.setString(5, docente.getCorreoElectronico());
                sentenciaUsuario.setString(6, docente.getContrasena());
                sentenciaUsuario.setObject(7, docente.getIdCategoria());
                sentenciaUsuario.setObject(8, docente.getIdTipoContratacion());

                int filasAfectadas = sentenciaUsuario.executeUpdate();

                if (filasAfectadas > 0) {
                    // ID del docente recién insertado o actualizado
                    ResultSet generatedKeys = sentenciaUsuario.getGeneratedKeys();
                    int idUsuario = 0;
                    if (generatedKeys.next()) {
                        idUsuario = generatedKeys.getInt(1);
                    }

                    //Inserta en USUARIO_TIPO_USUARIO
                    String consultaUsuarioTipo = "INSERT INTO USUARIO_TIPO_USUARIO (tipo_usuario_id, usuario_id) VALUES (?, ?)";
                    PreparedStatement sentenciaUsuarioTipo = conexionBD.prepareStatement(consultaUsuarioTipo);
                    sentenciaUsuarioTipo.setInt(1, 1); // Tipo de usuario "1"
                    sentenciaUsuarioTipo.setInt(2, idUsuario);
                    int filasAfectadasTipo = sentenciaUsuarioTipo.executeUpdate();

                    if (filasAfectadasTipo > 0) {
                        conexionBD.commit();
                        respuesta.put(ERROR_KEY, false);
                        respuesta.put(MESSAGE_KEY, "La información del docente " + docente.getNombre() +
                                " se ha registrado correctamente");
                    } else {
                        conexionBD.rollback();
                        respuesta.put(MESSAGE_KEY, "No se pudo registrar el tipo de usuario para el docente.");
                    }
                } else {
                    conexionBD.rollback();
                    respuesta.put(MESSAGE_KEY, "No se pudo registrar el docente.");
                }

            } catch (SQLException e) {
                try {
                    conexionBD.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                respuesta.put(MESSAGE_KEY, "Error en la operación de registro");
                e.printStackTrace();
            } finally {
                ConectorBD.cerrarConexion(conexionBD);
            }
        } else {
            respuesta.put(MESSAGE_KEY, Constantes.MENSAJE_ERROR_DE_CONEXION);
        }

        return respuesta;
    }


    public static HashMap<String, Object> verificarDocente(String noPersonal) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(ERROR_KEY, true);

        Connection conexion = ConectorBD.obtenerConexion();

        if (conexion != null) {
            try {
                String consulta = "SELECT * FROM USUARIO WHERE no_personal = ?";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setString(1, noPersonal);

                ResultSet resultadoConsulta = sentencia.executeQuery();

                if (resultadoConsulta.next()) {
                    respuesta.put(MESSAGE_KEY, "El docente ya se encuentra registrado.");
                } else {
                    respuesta.put(ERROR_KEY, false);
                    respuesta.put(MESSAGE_KEY, "El usuario no está registrado.");
                }
            } catch (SQLException sqlEx) {
                respuesta.put(MESSAGE_KEY, "Error: " + sqlEx.getMessage());
            } finally {
                ConectorBD.cerrarConexion(conexion);
            }
        } else {
            respuesta.put(MESSAGE_KEY, Constantes.MENSAJE_ERROR_DE_CONEXION);
        }

        return respuesta;
    }
    private static void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario proporcionado no puede ser nulo.");
        }

        if (usuario.getNo_personal() == null || usuario.getNo_personal().isEmpty() ||
                usuario.getNombre() == null || usuario.getNombre().isEmpty() ||
                usuario.getApellidoPaterno() == null || usuario.getApellidoPaterno().isEmpty() ||
                usuario.getApellidoMaterno() == null || usuario.getApellidoMaterno().isEmpty() ||
                usuario.getCorreoElectronico() == null || usuario.getCorreoElectronico().isEmpty() ||
                usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
            throw new IllegalArgumentException("El usuario contiene campos vacíos obligatorios.");
        }
    }
}
