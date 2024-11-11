package com.example.red.modelo;

/**
 * Modela un mensaje de aviso durante el inicio de sesión
 */
public class ModelMessage {

    /** Bandera que indica el estado del inicio de sesión */
    private boolean success;

    /** Mensaje */
    private String message;

    /** Constructor sin parámetros */
    public ModelMessage() {

    }

    /** Constructor con parámetros */
    public ModelMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Obtener si la operación fue exitosa
     * 
     * @return sucess true si fue exitosa, false si hubo un error
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Asignar el estado de la operación
     * 
     * @param success true si fue exitosa, false si hubo un error
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Obtener el mensaje
     * 
     * @return mensaje
     */
    public String getMessage() {
        return message;
    }

    /**
     * Asignar el mensaje
     * 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
