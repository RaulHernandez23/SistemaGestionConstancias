package org.lossuperconocidos.sistemagestionconstancias.daos;

import org.lossuperconocidos.sistemagestionconstancias.modelos.*;
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

    public  static HashMap<String, Object> recuperarParticipacionPorNoPerosnal(String numPersonal) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(ERROR_KEY, true);
        Connection conexion = ConectorBD.obtenerConexion();
        if (conexion != null) {
            try {
                String consulta = "SELECT " +
                                    "p.id AS participacion_id, " +
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
                ArrayList<ParticipacionUsuario> listaParticipaciones = new ArrayList<>();

                while (resultadoConsulta.next()) {
                    ParticipacionUsuario participacion = new ParticipacionUsuario();
                    participacion.setId(resultadoConsulta.getInt(1));
                    participacion.setTipoParticipacion(resultadoConsulta.getString(2));
                    participacion.setPeriodoEscolarNombre(resultadoConsulta.getString(3));
                    participacion.setFechaInicio(resultadoConsulta.getDate(4));
                    participacion.setFechaFin(resultadoConsulta.getDate(5));

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

    public static HashMap<String, Object> recuperarProgramas () {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(ERROR_KEY, true);

        Connection conexion = ConectorBD.obtenerConexion();

        if(conexion != null) {
            try {
                String consulta = "SELECT nombre FROM PROGRAMA_EDUCATIVO";
                ResultSet resultadoConsulta = conexion.createStatement().executeQuery(consulta);

                ArrayList<String> programasEducativos = new ArrayList<>();
                while (resultadoConsulta.next()) {
                    programasEducativos.add(resultadoConsulta.getString("nombre"));
                }
                respuesta.put(ERROR_KEY, false);
                respuesta.put("programas", programasEducativos);
            } catch (SQLException sqlEx) {
                respuesta.put(MESSAGE_KEY, "Error al obtener los programas: " + sqlEx.getMessage());
            } finally {
                ConectorBD.cerrarConexion(conexion);
            }
        } else {
            respuesta.put(MESSAGE_KEY, Constantes.MENSAJE_ERROR_DE_CONEXION);
        }
        return respuesta;
    }

    public static HashMap<String, Object> registrarImparticionEE(ImparticionEE imparticion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(ERROR_KEY, true);

        Connection conexion = ConectorBD.obtenerConexion();

        if(conexion != null) {
            try {
                String consulta = "{CALL SP_registrar_participacion_imparticion(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setString(1, imparticion.getNoPersonal());
                sentencia.setString(2, imparticion.getPeriodoEscolar());
                sentencia.setString(3, imparticion.getExperienciaEducativa());
                sentencia.setString(4, imparticion.getBloque());
                sentencia.setInt(5, imparticion.getCreditos());
                sentencia.setInt(6, imparticion.getHoras());
                sentencia.setInt(7, imparticion.getMeses());
                sentencia.setInt(8, imparticion.getSeccion());
                sentencia.setInt(9, imparticion.getSemanas());
                sentencia.setString(10, imparticion.getProgramaEducativo());

                sentencia.executeUpdate();

                respuesta.put(ERROR_KEY, false);
                respuesta.put(MESSAGE_KEY, "La participación se ha registrado exitosamente");
            } catch (SQLException sqlEx) {
                respuesta.put(MESSAGE_KEY, "Error al registrar la participación: " + sqlEx.getMessage());
            } finally {
                ConectorBD.cerrarConexion(conexion);
            }
        } else {
            respuesta.put(MESSAGE_KEY, Constantes.MENSAJE_ERROR_DE_CONEXION);
        }
        return respuesta;
    }

    public static HashMap<String, Object> registrarJurado(Jurado jurado) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(ERROR_KEY, true);

        Connection conexion = ConectorBD.obtenerConexion();

        if (conexion != null) {
            try {
                String consulta = "{CALL SP_registrar_participacion_jurado(?, ?, ?, ?, ?, ?, ?)}";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);

                sentencia.setString(1, jurado.getNoPersonal());
                sentencia.setString(2, jurado.getPeriodoEscolar());
                sentencia.setString(3, jurado.getTituloTrabajo());
                sentencia.setDate(4, jurado.getFechaPresentacion());
                sentencia.setString(5, jurado.getModalidad());
                sentencia.setString(6, jurado.getNombreAlumnos());
                sentencia.setString(7, jurado.getResultadoObtenido());

                sentencia.executeUpdate();

                respuesta.put(ERROR_KEY, false);
                respuesta.put(MESSAGE_KEY, "La participación se ha registrado exitosamente");
            } catch (SQLException sqlEx) {
                respuesta.put(MESSAGE_KEY, "Error al registrar la participación: " + sqlEx.getMessage());
            } finally {
                ConectorBD.cerrarConexion(conexion);
            }
        } else {
            respuesta.put(MESSAGE_KEY, Constantes.MENSAJE_ERROR_DE_CONEXION);
        }

        return respuesta;
    }

    public static HashMap<String, Object> registrarProyecto(Proyecto proyecto) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(ERROR_KEY, true);

        Connection conexion = ConectorBD.obtenerConexion();

        if (conexion != null) {
            try {
                String consulta = "{CALL SP_registrar_participacion_proyecto(?, ?, ?, ?, ?, ?)}";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);

                sentencia.setString(1, proyecto.getNoPersonal());
                sentencia.setString(2, proyecto.getPeriodoEscolar());
                sentencia.setString(3, proyecto.getProyectoRealizado());
                sentencia.setString(4, proyecto.getImpactoObtenido());
                sentencia.setString(5, proyecto.getLugar());
                sentencia.setString(6, proyecto.getNombreAlumnos());

                sentencia.executeUpdate();

                respuesta.put(ERROR_KEY, false);
                respuesta.put(MESSAGE_KEY, "La participación se ha registrado exitosamente");
            } catch (SQLException sqlEx) {
                respuesta.put(MESSAGE_KEY, "Error al registrar la participación: " + sqlEx.getMessage());
            } finally {
                ConectorBD.cerrarConexion(conexion);
            }
        } else {
            respuesta.put(MESSAGE_KEY, Constantes.MENSAJE_ERROR_DE_CONEXION);
        }

        return respuesta;
    }

    public static HashMap<String, Object> registrarPladea(Pladea pladea) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(ERROR_KEY, true);

        Connection conexion = ConectorBD.obtenerConexion();

        if (conexion != null) {
            try {
                String consulta = "{CALL SP_registrar_participacion_pladea(?, ?, ?, ?, ?, ?, ?)}";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);

                sentencia.setString(1, pladea.getNoPersonal());
                sentencia.setString(2, pladea.getPeriodoEscolar());
                sentencia.setString(3, pladea.getAcciones());
                sentencia.setString(4, pladea.getEjeEstrategico());
                sentencia.setString(5, pladea.getMetas());
                sentencia.setString(6, pladea.getObjetivosGenerales());
                sentencia.setString(7, pladea.getProgramaEstrategico());

                sentencia.executeUpdate();

                respuesta.put(ERROR_KEY, false);
                respuesta.put(MESSAGE_KEY, "La participación se ha registrado exitosamente");
            } catch (SQLException sqlEx) {
                respuesta.put(MESSAGE_KEY, "Error al registrar la participación: " + sqlEx.getMessage());
            } finally {
                ConectorBD.cerrarConexion(conexion);
            }
        } else {
            respuesta.put(MESSAGE_KEY, Constantes.MENSAJE_ERROR_DE_CONEXION);
        }

        return respuesta;
    }

    public static HashMap<String, Object> recuperarTodaParticipacion() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(ERROR_KEY, true);
        Connection conexion = ConectorBD.obtenerConexion();
        if (conexion != null) {
            try {
                String consulta = "SELECT " +
                        "p.id AS participacion_id, " +
                        "p.tipo_participacion, " +
                        "pe.nombre AS periodo_escolar, " +
                        "pe.fecha_inicio, " +
                        "pe.fecha_fin, " +
                        "u.nombre AS docente_nombre, " +
                        "u.apellido_paterno AS docente_apellido_paterno, " +
                        "u.apellido_materno AS docente_apellido_materno " +
                        "FROM PARTICIPACION p " +
                        "LEFT JOIN PERIODO_ESCOLAR pe ON p.periodo_escolar_id = pe.id " +
                        "LEFT JOIN USUARIO u ON p.docente_id = u.id";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);

                ResultSet resultadoConsulta = sentencia.executeQuery();
                ArrayList<ParticipacionUsuario> listaParticipaciones = new ArrayList<>();

                while (resultadoConsulta.next()) {
                    ParticipacionUsuario participacion = new ParticipacionUsuario();
                    participacion.setId(resultadoConsulta.getInt(1));
                    participacion.setTipoParticipacion(resultadoConsulta.getString(2));
                    participacion.setPeriodoEscolarNombre(resultadoConsulta.getString(3));
                    participacion.setFechaInicio(resultadoConsulta.getDate(4));
                    participacion.setFechaFin(resultadoConsulta.getDate(5));
                    participacion.setNombreUsuario(resultadoConsulta.getString(6));
                    participacion.setApellidoMaterno(resultadoConsulta.getString(7));
                    participacion.setApellidoPaterno(resultadoConsulta.getString(8));
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
