package org.lossuperconocidos.sistemagestionconstancias.daos;

import org.lossuperconocidos.sistemagestionconstancias.modelos.PeriodoEscolar;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.ConectorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PeriodoEscolarDAO {
    public static final String ERROR_KEY = "error";
    public static final String MESSAGE_KEY = "mensaje";
    public static final String PERIODOS_KEY = "periodos";

    public static HashMap<String, Object> recuperarPeriodosEscolares() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(ERROR_KEY, true);

        Connection conexion = ConectorBD.obtenerConexion();

        if (conexion != null) {
            try {
                String consulta = "SELECT id, nombre, fecha_inicio, fecha_fin FROM PERIODO_ESCOLAR";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                ResultSet resultado = sentencia.executeQuery();

                List<PeriodoEscolar> periodos = new ArrayList<>();
                while (resultado.next()) {
                    PeriodoEscolar periodo = new PeriodoEscolar();
                    periodo.setId(resultado.getInt("id"));
                    periodo.setNombre(resultado.getString("nombre"));
                    periodo.setFechaInicio(resultado.getDate("fecha_inicio"));
                    periodo.setFechaFin(resultado.getDate("fecha_fin"));
                    periodos.add(periodo);
                }

                respuesta.put(ERROR_KEY, false);
                respuesta.put(PERIODOS_KEY, periodos);

            } catch (SQLException e) {
                respuesta.put(MESSAGE_KEY, "Error al recuperar los periodos escolares: " + e.getMessage());
                e.printStackTrace();
            } finally {
                ConectorBD.cerrarConexion(conexion);
            }
        } else {
            respuesta.put(MESSAGE_KEY, "No se pudo conectar a la base de datos");
        }

        return respuesta;
    }
}
