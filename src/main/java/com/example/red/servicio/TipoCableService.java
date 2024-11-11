package com.example.red.servicio;

import java.util.TreeMap;
import com.example.red.modelo.TipoCable;

/**
 * Interfaz que define las operaciones para gestionar tipos de cable
 * Provee los métodos para insertar, leer, modificar y eliminar
 */
public interface TipoCableService {

    /**
     * Insertar un nuevo tipo de cable en la red
     * 
     * @param tipoCable
     */
    void insertar(TipoCable tipoCable);

    /**
     * Actualizar un tipo de cable de la red
     * 
     * @param tipoCable
     */
    void actualizar(TipoCable tipoCable);

    /**
     * Borrar un tipo de cable de la red
     * 
     * @param tipoCable
     */
    void borrar(TipoCable tipoCable);

    /**
     * Obtener todos los tipos de cable de la red
     * 
     * @return mapa de tipos de cable
     */
    TreeMap<String, TipoCable> buscarTodos();
}
