package org.lossuperconocidos.sistemagestionconstancias.daos;

import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.ConectorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UsuarioDAO {

    public static HashMap<String, Object> iniciarSesion (String noPersonal, String contrasena) {

        HashMap<String, Object> respuesta = new HashMap<>();

        respuesta.put("error", true);

        Connection conexion = ConectorBD.obtenerConexion();

        if (conexion != null) {

            try {

                String consulta = "SELECT * FROM vista_usuarios WHERE no_personal = ? AND password = ?";

                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setString(1, noPersonal);
                sentencia.setString(2, contrasena);

                ResultSet resultadoConsulta = sentencia.executeQuery();

                if(resultadoConsulta.next()) {

                    Usuario usuario = new Usuario();

                    usuario.setNo_personal(resultadoConsulta.getString("no_personal"));
                    usuario.setNombre(resultadoConsulta.getString("nombre"));
                    usuario.setApellidoPaterno(resultadoConsulta.getString("apellido_paterno"));
                    usuario.setApellidoMaterno(resultadoConsulta.getString("apellido_materno"));
                    usuario.setCorreoElectronico(resultadoConsulta.getString("correo_electronico"));
                    usuario.setTipoUsuario(resultadoConsulta.getString("tipo_usuario"));
                    usuario.setCategoria(resultadoConsulta.getString("categoria"));
                    usuario.setTipoContratacion(resultadoConsulta.getString("tipo_contratacion"));

                    respuesta.put("error", false);
                    respuesta.put("mensaje", "Inicio de sesión exitoso");
                    respuesta.put("usuario", usuario);
                } else {
                    respuesta.put("mensaje", "Usuario y/o contraseña incorrectos. Por favor, verifique sus datos");
                }
            } catch (SQLException sqlEx){
                respuesta.put("mensaje", "Error: " + sqlEx.getMessage());
            } finally {
                ConectorBD.cerrarConexion(conexion);
            }
        } else {
            respuesta.put("mensaje", "No se pudo conectar a la red, por favor revise su conexión");
        }

        return respuesta;
    }


}
