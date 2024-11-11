package com.example.red.modelo;

/**
 * Modela un objeto 'login' que se usa en el inicio de sesión
 * 
 */
public class ModelLogin {

    /** La dirección de correo electrónico ingresada */
    private String email;

    /** La contraseña ingresada */
    private String password;

    /** Constructor sin parámetros */
    public ModelLogin() {

    }

    /** Constructor con parámetros */
    public ModelLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Obtener el email ingresado
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ingresar el email
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtener la contraseña ingresada
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Ingresa la contraseña
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
