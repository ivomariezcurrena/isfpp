package com.example.red.servicio;

import java.util.TreeMap;
import com.example.red.modelo.TipoEquipo;

/**
 * Interfaz que define las operaciones para gestionar tipos de equipos
 * Provee los métodos para insertar, leer, modificar y eliminar
 */
public interface TipoEquipoService {

    /**
     * Insertar un nuevo tipo de equipo en la red
     * 
     * @param tipoEquipo
     */
    void insertar(TipoEquipo tipoEquipo);

    /**
     * Actualizar un tipo de equipo de la red
     * 
     * @param tipoEquipo
     */
    void actualizar(TipoEquipo tipoEquipo);

    /**
     * Borrar un tipo de equipo de la red
     * 
     * @param tipoEquipo
     */
    void borrar(TipoEquipo tipoEquipo);

    /**
     * Obtener todos los tipos de equipo de la red
     * 
     * @return mapa de tipos de equipos
     */
    TreeMap<String, TipoEquipo> buscarTodos();
}