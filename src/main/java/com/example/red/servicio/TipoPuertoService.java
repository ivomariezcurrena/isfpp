package com.example.red.servicio;

import java.util.TreeMap;
import com.example.red.modelo.TipoPuerto;

/**
 * Interfaz que define las operaciones para gestionar tipos de puertos
 * Provee los métodos para insertar, leer, modificar y eliminar
 */
public interface TipoPuertoService {

    /**
     * Insertar un nuevo tipo de puerto en la red
     * 
     * @param tipoPuerto
     */
    void insertar(TipoPuerto tipoPuerto);

    /**
     * Actualizar un tipo de puerto de la red
     * 
     * @param tipoPuerto
     */
    void actualizar(TipoPuerto tipoPuerto);

    /**
     * Borrar un tipo de puerto de la red
     * 
     * @param tipoPuerto
     */
    void borrar(TipoPuerto tipoPuerto);

    /**
     * Obtener todos los tipos de puertos de la red
     * 
     * @return mapa de tipos de puertos
     */
    TreeMap<String, TipoPuerto> buscarTodos();
}
