package org.lossuperconocidos.sistemagestionconstancias.daos;

import org.lossuperconocidos.sistemagestionconstancias.modelos.Participacion;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.ConectorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class ParticipacionDAO {

    public static HashMap<String, Object> registrarParticipacion(Participacion nuevaParticipacion) {

        HashMap<String, Object> respuesta = new HashMap<>();

        respuesta.put("error", true);

        Connection conexion = ConectorBD.obtenerConexion();

        if (conexion != null) {

            try {

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

                    respuesta.put("error", false);
                    respuesta.put("mensaje", "Participación registrada correctamente");

                } else {

                    respuesta.put("mensaje", "No se pudo registrar la participación");
                }
                
            } catch (SQLException sqlEx) {

                respuesta.put("mensaje", "Error: " + sqlEx.getMessage());

            } finally {

                ConectorBD.cerrarConexion(conexion);

            }

        } else {

            respuesta.put("mensaje", "No se pudo establecer la conexión con la base de datos");
        }

        return respuesta;

    }
}
