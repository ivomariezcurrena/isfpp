package com.example.red.servicio;

import com.example.red.conexion.*;
import com.example.red.modelo.ModelLogin;
import com.example.red.modelo.ModelUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Provee métodos para el registro e inicio de sesión de usuarios a la
 * aplicación
 * 
 */
public class ServiceUsuario {

    /** Conexión a la base de datos */
    private final Connection con;

    /** Constructor sin parámetros */
    public ServiceUsuario() {
        // Obtener la conexión desde la instancia singleton
        con = ConexionBD.getInstance().getConnection();
    }

    /**
     * Método para iniciar sesión
     * 
     * @param login 
     * @return usuario
     * @throws SQLException en caso de errores de sentencias SQL
     */
    public ModelUser login(ModelLogin login) throws SQLException {
        ModelUser data = null;
        PreparedStatement p = con.prepareStatement(
                "SELECT id_usuario, nombre_usuario, Email FROM poo2024.usuarios_ivoma where Email=? and contraseña=? and estado='Verificado'");
        p.setString(1, login.getEmail());
        p.setString(2, login.getPassword());
        ResultSet r = p.executeQuery();
        if (r.next()) {
            int userID = r.getInt(1);
            String userName = r.getString(2);
            String email = r.getString(3);
            data = new ModelUser(userID, userName, email, "");
        }
        r.close();
        p.close();
        return data;
    }

    /**
     * Método para insertar un nuevo usuario en la tabla de usuarios
     * 
     * @param user usuario
     * @throws SQLException en caso de errores de sentencias SQL
     */
    public void insertUser(ModelUser user) throws SQLException {
        String sql = "INSERT INTO poo2024.usuarios_ivoma (nombre_usuario, Email, contraseña, administrador, codigo_verificacion, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement p = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            String code = generateVerifyCode();
            p.setString(1, user.getUserName());
            p.setString(2, user.getEmail());
            p.setString(3, user.getPassword());
            p.setBoolean(4, false);
            p.setString(5, code);
            p.setString(6, "Pendiente"); // Estado inicial del usuario
            p.executeUpdate();

            try (ResultSet r = p.getGeneratedKeys()) {
                if (r.next()) {
                    int userID = r.getInt(1);
                    user.setUserID(userID);
                }
            }
            user.setVerifyCode(code);
        }
    }

    /**
     * Método para generar un código de verificación único
     * 
     * @return código
     * @throws SQLException en caso de errores de sentencias SQL
     */
    private String generateVerifyCode() throws SQLException {
        DecimalFormat df = new DecimalFormat("000000");
        Random ran = new Random();
        String code = df.format(ran.nextInt(1000000)); // Random from 0 to 999999
        while (checkDuplicateCode(code)) {
            code = df.format(ran.nextInt(1000000));
        }
        return code;
    }

    /**
     * Método para verificar si un código de verificación ya existe en la tabla
     * 
     * @param code código de verificación
     * @return true si ya existe, false si aún no existe
     * @throws SQLException en caso de errores de sentencias SQL
     */
    private boolean checkDuplicateCode(String code) throws SQLException {
        String sql = "SELECT id_usuario FROM poo2024.usuarios_ivoma WHERE codigo_verificacion = ? LIMIT 1";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setString(1, code);
            try (ResultSet r = p.executeQuery()) {
                return r.next(); // Cambiado a r.next()
            }
        }
    }

    /**
     * Método para verificar si el nombre de usuario ya existe y está verificado
     * 
     * @param user Usuario
     * @return true si existe duplicado, false si no existe
     * @throws SQLException en caso de errores de sentencias SQL
     */
    public boolean checkDuplicateUser(String user) throws SQLException {
        String sql = "SELECT id_usuario FROM poo2024.usuarios_ivoma WHERE nombre_usuario = ? AND estado = 'Verificado' LIMIT 1";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setString(1, user);
            try (ResultSet r = p.executeQuery()) {
                return r.next(); // Cambiado a r.next()
            }
        }
    }

    /**
     * Método para verificar si el email ya existe y está verificado
     * 
     * @param email dirección de correo
     * @return true si existe duplicado, false si no existe
     * @throws SQLException en caso de errores de sentencias SQL
     */
    public boolean checkDuplicateEmail(String email) throws SQLException {
        String sql = "SELECT id_usuario FROM poo2024.usuarios_ivoma WHERE Email = ? AND estado = 'Verificado' LIMIT 1";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setString(1, email);
            try (ResultSet r = p.executeQuery()) {
                return r.next(); // Cambiado a r.next()
            }
        }
    }

    /**
     * Método para marcar un usuario como verificado
     * 
     * @param userID ID del usuario
     * @throws SQLException en caso de errores de sentencias SQL
     */
    public void doneVerify(int userID) throws SQLException {
        try (PreparedStatement p = con.prepareStatement(
                "UPDATE poo2024.usuarios_ivoma SET codigo_verificacion=null, estado='Verificado' WHERE id_usuario=?")) {
            p.setInt(1, userID);
            p.execute();
            p.close();
        }
    }

    /**
     * Método para verificar el código de verificación de un usuario
     * 
     * @param userID ID del usuario
     * @param code   código
     * @return true si es exitoso, false si hubo un error
     * @throws SQLException en caso de errores de sentencias SQL
     */
    public boolean verifyCodeWithUser(int userID, String code) throws SQLException {
        String sql = "SELECT id_usuario FROM poo2024.usuarios_ivoma WHERE id_usuario = ? AND codigo_verificacion = ? LIMIT 1";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, userID);
            p.setString(2, code);
            System.out.println("Verificando código para el usuario: " + userID + ", Código: " + code);
            try (ResultSet r = p.executeQuery()) {
                return r.next(); // Cambiado a r.next()
            }

        }
    }
}