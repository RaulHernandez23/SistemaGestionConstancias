package org.lossuperconocidos.sistemagestionconstancias.daos;

import org.lossuperconocidos.sistemagestionconstancias.utilidades.ConectorBD;

import java.sql.Connection;
import java.util.HashMap;

public class UsuarioDAO {

    public static HashMap<String, Object> iniciarSesion (String noPersonal, String contrasena) {

        HashMap<String, Object> respuesta = new HashMap<>();

        respuesta.put("error", true);

        Connection conexion = ConectorBD.obtenerConexion();

        if (conexion != null) {

            try {

                String consulta = "SELECT "
                        + "u.no_personal, "
                        + "u.nombre, "
                        + "u.apellido_paterno, "
                        + "u.apellido_materno ";
            }
        }

        return null;
    }
}
