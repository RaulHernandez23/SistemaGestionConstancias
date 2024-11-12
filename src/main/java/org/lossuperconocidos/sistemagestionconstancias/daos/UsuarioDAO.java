package org.lossuperconocidos.sistemagestionconstancias.daos;

import org.lossuperconocidos.sistemagestionconstancias.modelos.Categoria;
import org.lossuperconocidos.sistemagestionconstancias.modelos.TipoContratacion;
import org.lossuperconocidos.sistemagestionconstancias.modelos.Usuario;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.ConectorBD;
import org.lossuperconocidos.sistemagestionconstancias.utilidades.Constantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UsuarioDAO {

    public static HashMap<String, Object> iniciarSesion (String noPersonal, String contrasena) {

        HashMap<String, Object> respuesta = new HashMap<>();

        respuesta.put("error", true);

        Connection conexion = ConectorBD.obtenerConexion();

        if (conexion != null) {

            try {
                //TODO: La vista ya no tiene el password, verificar si la nueva implementacion esta bien
                //String consulta = "SELECT * FROM vista_usuarios WHERE no_personal = ? AND password = ?";
                String consulta = "SELECT v.*, u.password " +
                        "FROM vista_usuarios v JOIN usuario u ON v.no_personal = u.no_personal " +
                        "WHERE  u.no_personal = ? AND u.password = ?";

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

    public static HashMap<String, Object> consultarCategorias() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);
        Connection conexionBD = ConectorBD.obtenerConexion();

        if (conexionBD != null) {
            try {
                String consulta = "SELECT id, nombre FROM categoria";
                PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultadoConsulta = sentencia.executeQuery();

                ArrayList<Categoria> categorias = new ArrayList<>();

                while (resultadoConsulta.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setIdCategoria(resultadoConsulta.getInt("id"));
                    categoria.setNombreCategoria(resultadoConsulta.getString("nombre"));
                    categorias.add(categoria);
                }

                respuesta.put("error", false);
                respuesta.put("categorias", categorias);

            } catch (SQLException se) {
                respuesta.put("mensaje", Constantes.MENSAJE_ERROR_REGISTRO);
                se.printStackTrace();
            } finally {
                ConectorBD.cerrarConexion(conexionBD);
            }
        } else {
            respuesta.put("mensaje", Constantes.MENSAJE_ERROR_REGISTRO);
        }
        return respuesta;
    }

    public static HashMap<String, Object> consultarTiposContratacion() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);
        Connection conexionBD = ConectorBD.obtenerConexion();

        if (conexionBD != null) {
            try {
                String consulta = "SELECT id, nombre FROM tipo_contratacion";
                PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultadoConsulta = sentencia.executeQuery();

                ArrayList<TipoContratacion> tiposContratacion = new ArrayList<>();

                while (resultadoConsulta.next()) {
                    TipoContratacion tipoContratacion = new TipoContratacion();
                    tipoContratacion.setIdTipoContratacion(resultadoConsulta.getInt("id"));
                    tipoContratacion.setNombreTipoContratacion(resultadoConsulta.getString("nombre"));
                    tiposContratacion.add(tipoContratacion);
                }

                respuesta.put("error", false);
                respuesta.put("tiposContratacion", tiposContratacion);

            } catch (SQLException se) {
                respuesta.put("mensaje", Constantes.MENSAJE_ERROR_REGISTRO);
            } finally {
                ConectorBD.cerrarConexion(conexionBD);
            }
        } else {
            respuesta.put("mensaje", Constantes.MENSAJE_ERROR_REGISTRO);
        }

        return respuesta;
    }

}
