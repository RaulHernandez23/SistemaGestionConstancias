package org.lossuperconocidos.sistemagestionconstancias.daos;

import org.lossuperconocidos.sistemagestionconstancias.modelos.Participacion;
import org.lossuperconocidos.sistemagestionconstancias.modelos.ParticipacionCorregido;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.ConectorBD;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Constantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ParticipacionDAO {
    public static final String ERROR_KEY = "error";
    public static final String MESSAGE_KEY = "mensaje";
    public static final String PARTICIPACIONES_KEY = "participaciones";
    private static final String MENSAJE_PARTICIPACION_REGISTRADA = "Participación registrada correctamente";
    private static final String MENSAJE_PARTICIPACION_NO_REGISTRADA = "No se pudo registrar la participación";
    private static final String MENSAJE_ERROR_CONEXION = Constantes.MENSAJE_ERROR_DE_CONEXION;
    public static HashMap<String, Object> registrarParticipacion(Participacion nuevaParticipacion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(ERROR_KEY, true);

        Connection conexion = ConectorBD.obtenerConexion();

        if (conexion != null) {
            try {
                //FIXME: Actualizar acorde a la nueva BD
                String consulta = "INSERT INTO participacion (constatacion, fecha_inicio, fecha_fin, tipo_participacion, id_docente) " +
                        "VALUES (?, ?, ?, ?, ?)";

                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setString(1, nuevaParticipacion.getConstatacion());
                sentencia.setDate(2, new java.sql.Date(nuevaParticipacion.getFechaInicio().getTime()));
                sentencia.setDate(3, new java.sql.Date(nuevaParticipacion.getFechaFin().getTime()));
                sentencia.setString(4, nuevaParticipacion.getTipoParticipacion());
                sentencia.setInt(5, nuevaParticipacion.getIdDocente());

                int resultadoConsulta = sentencia.executeUpdate();

                if (resultadoConsulta > 0) {
                    respuesta.put(ERROR_KEY, false);
                    respuesta.put(MESSAGE_KEY, MENSAJE_PARTICIPACION_REGISTRADA);
                } else {
                    respuesta.put(MESSAGE_KEY, MENSAJE_PARTICIPACION_NO_REGISTRADA);
                }
            } catch (SQLException sqlEx) {
                respuesta.put(MESSAGE_KEY, "Error: " + sqlEx.getMessage());
            } finally {
                ConectorBD.cerrarConexion(conexion);
            }
        } else {
            respuesta.put(MESSAGE_KEY, MENSAJE_ERROR_CONEXION);
        }
        return respuesta;
    }

    public  static HashMap<String, Object> recuperarParticipacionPorNoPerosnal(String numPersonal) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(ERROR_KEY, true);

        Connection conexion = ConectorBD.obtenerConexion();

        if (conexion != null) {
            try {
//                String consulta = "SELECT p.id, p.constatacion, p.tipo_participacion, pe.nombre AS periodo_escolar, " +
//                        "pe.fecha_inicio, pe.fecha_fin " +
//                        "FROM PARTICIPACION p " +
//                        "LEFT JOIN PERIODO_ESCOLAR pe ON p.periodo_escolar_id = pe.id " +
//                        "WHERE p.docente_id = ?";
                String consulta = "SELECT " +
                                    "p.id AS participacion_id, " +
                                    "p.constatacion, " +
                                    "p.tipo_participacion, " +
                                    "pe.nombre AS periodo_escolar, " +
                                    "pe.fecha_inicio, " +
                                    "pe.fecha_fin " +
                                    "FROM PARTICIPACION p " +
                                    "LEFT JOIN PERIODO_ESCOLAR pe ON p.periodo_escolar_id = pe.id " +
                                    "WHERE p.docente_id = (SELECT id FROM USUARIO WHERE no_personal = ?)";

                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setString(1, numPersonal);

                ResultSet resultadoConsulta = sentencia.executeQuery();
                ArrayList<ParticipacionCorregido> listaParticipaciones = new ArrayList<>();

                while (resultadoConsulta.next()) {
                    ParticipacionCorregido participacion = new ParticipacionCorregido();
                    participacion.setId(resultadoConsulta.getInt(1));
                    participacion.setConstatacion(resultadoConsulta.getString(2));
                    participacion.setTipoParticipacion(resultadoConsulta.getString(3));
                    participacion.setPeriodoEscolarNombre(resultadoConsulta.getString(4));
                    participacion.setFechaInicio(resultadoConsulta.getDate(5));
                    participacion.setFechaFin(resultadoConsulta.getDate(6));

                    listaParticipaciones.add(participacion);
                }

                respuesta.put(ERROR_KEY, false);
                respuesta.put(MESSAGE_KEY, "Participaciones recuperadas exitosamente.");
                respuesta.put(PARTICIPACIONES_KEY, listaParticipaciones);

            } catch (SQLException sqlEx) {
                respuesta.put(MESSAGE_KEY, "Error al obtener las participaciones: " + sqlEx.getMessage());
            } finally {
                ConectorBD.cerrarConexion(conexion);
            }
        } else {
            respuesta.put(MESSAGE_KEY, MENSAJE_ERROR_CONEXION);
        }

        return respuesta;
    }

}
