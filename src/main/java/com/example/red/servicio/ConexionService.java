package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.modelo.Conexion;

/**
 * Interfaz que define las operaciones para gestionar conexiones
 * Provee los métodos para insertar, leer, modificar y eliminar
 */
public interface ConexionService {
    /**
     * Insertar una nueva conexión en la red
     * 
     * @param conexion
     */
    void insertar(Conexion conexion);

    /**
     * Actualizar una conexión de la red
     * 
     * @param conexion
     */
    void actualizar(Conexion conexion);

    /**
     * Borrar una conexión de la red
     * 
     * @param conexion
     */
    void borrar(Conexion conexion);

    /**
     * Obtener todas las conexiones de la red
     * 
     * @return mapa de conexiones
     */
    TreeMap<String, Conexion> buscarTodos();
}
