package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.modelo.Equipo;

/**
 * Interfaz que define las operaciones para gestionar equipos
 * Provee los métodos para insertar, leer, modificar y eliminar
 */
public interface EquipoService {

    /**
     * Insertar un nuevo equipo en la red
     * 
     * @param equipo
     */
    void insertar(Equipo equipo);

    /**
     * Actualizar un equipo de la red
     * 
     * @param equipo
     */
    void actualizar(Equipo equipo);

    /**
     * Borrar un equipo la red
     * 
     * @param equipo
     */
    void borrar(Equipo equipo);

    /**
     * Obtener todos los equipos de la red
     * 
     * @return mapa de equipos
     */
    TreeMap<String, Equipo> buscarTodos();
}
