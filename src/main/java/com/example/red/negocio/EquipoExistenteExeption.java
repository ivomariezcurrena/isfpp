package com.example.red.negocio;

/**
 * Excepción que se lanza cuando se intenta insertar un equipo que ya existe
 * (equipo duplicado)
 */
public class EquipoExistenteExeption extends Exception {

    /** Constructor sin parámetros */
    public EquipoExistenteExeption() {

    }

    /** Constructor con mensaje */
    public EquipoExistenteExeption(String mensaje) {
        super(mensaje);
    }
    
}
