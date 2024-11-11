package com.example.red.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Permite conectarse a una base de datos PostgreSQL usando parámetros del
 * archivo de configuraciones
 * 
 */
public class ConexionBD {

    /** La instancia de esta clase */
    private static ConexionBD instance;

    /** La instancia de la conexión a la base de datos */
    private Connection connection;

    /**
     * Retorna la referencia de la unica instancia de esta clase. Si aún no existe,
     * se crea y se retorna
     * 
     * @return referencia
     */
    public static ConexionBD getInstance() {
        if (instance == null) {
            instance = new ConexionBD();
        }
        return instance;
    }

    /** Constructor privado para evitar multiples instancias */
    private ConexionBD() {

    }

    /**
     * Intenta conectarse a la base de datos. Utiliza los parametros de conexión
     * indicados en el archivo de configuraciones
     * 
     * @throws SQLException en caso de errores de conexión
     */
    public void connectToDatabase() throws SQLException {
        String host = "pgs.fi.mdn.unp.edu.ar";
        String puerto = "30000";
        String bd = "bd1";
        String usuario = "estudiante";
        String clave = "estudiante";
        // la URL a PostgreSQL
        connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + puerto + "/" + bd, usuario, clave);
    }

    /**
     * Retorna la referencia del objeto de conexión retornado por DriverManager
     * 
     * @return referencia
     */
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
