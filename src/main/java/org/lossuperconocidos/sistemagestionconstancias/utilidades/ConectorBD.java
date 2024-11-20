package org.lossuperconocidos.sistemagestionconstancias.utilidades;

import java.sql.Connection;
import java.sql.SQLException;

public class ConectorBD {

    private static String driver = "com.mysql.jdbc.Driver";
    private static String nombreBD = "sistema_constancias";
    private static String hostname = "localhost";
    private static String puerto = "3306";
    private static String usuario = "Miguel";
    private static String clave = "Morales300802";

    public static final String URL_CONEXION = "jdbc:mysql://" + hostname + ":" + puerto + "/" + nombreBD +
            "?allowPublicKeyRetrieval=true&useSSL=false";

    public static Connection obtenerConexion() {

        Connection conexion = null;

        try {

            Class.forName(driver);

            conexion = java.sql.DriverManager.getConnection(URL_CONEXION, usuario, clave);

        } catch (ClassNotFoundException | SQLException cnfEx) {

            cnfEx.printStackTrace();

        }

        return conexion;
    }

    public static void cerrarConexion(Connection conexion) {

        if(conexion != null) {

            try {

                conexion.close();

            } catch (SQLException sqlEx) {

                sqlEx.printStackTrace();

            }
        }
    }
}
