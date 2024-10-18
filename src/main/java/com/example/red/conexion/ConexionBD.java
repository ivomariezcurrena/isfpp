package com.example.red.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static ConexionBD instance;
    private Connection connection;

    public static ConexionBD getInstance() {
        if (instance == null) {
            instance = new ConexionBD();
        }
        return instance;
    }

    private ConexionBD() {

    }

    public void connectToDatabase() throws SQLException {
        String host = "pgs.fi.mdn.unp.edu.ar";
        String puerto = "30000";
        String bd = "bd1";
        String usuario = "estudiante";
        String clave = "estudiante";
        // la URL a PostgreSQL
        connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + puerto + "/" + bd, usuario, clave);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
