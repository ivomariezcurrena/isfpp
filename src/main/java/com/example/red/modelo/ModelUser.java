package com.example.red.modelo;

/**
 * Modela a un usuario de la aplicación, para registrar sus datos durante el
 * registro de una cuenta nueva o durante el inicio de sesión
 */
public class ModelUser {

    /** ID del usuario */
    private int userID;

    /** Nombre de usuario */
    private String userName;

    /** Dirección de correo electrónico */
    private String email;

    /** Contraseña */
    private String password;

    /** Bandera que indica si es admin */
    private boolean administrador;

    /** Código de verificación */
    private String verifyCode;

    /** Constructor completo */
    public ModelUser(int userID, String userName, String email, String password, boolean administrador,
            String verifyCode) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
        this.verifyCode = verifyCode;
    }

    /** Constructor con solo ID, nombre, email y contraseña */
    public ModelUser(int userID, String userName, String email, String password) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    /** Constructor sin parámetros */
    public ModelUser() {
    }

    /**
     * Obtener el ID del usuario
     * 
     * @return id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Asignar el ID del usuario
     * 
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Obtener el nombre de usuario
     * 
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Asignar el nombre de usuario
     * 
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Obtener el email del usuario
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Asignar el email del usuario
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtener la contraseña del usuario
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Asignar la contraseña del usuario
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtener el código de verificación para validar el registro del usuario
     * 
     * @return verifyCode
     */
    public String getVerifyCode() {
        return verifyCode;
    }

    /**
     * Asignar el código de verificación para validar el registro del usuario
     * 
     * @param verifyCode
     */
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    /**
     * Obtener la bandera que indica si el usuario es admin
     * 
     * @return true si es admin, false si no lo es
     */
    public boolean isAdministrador() {
        return administrador;
    }

    /**
     * Asignar la bandera que indica si el usuario es admin
     * 
     * @param administrador true si es admin, false si no lo es
     */
    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

}