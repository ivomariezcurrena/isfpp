package com.example.red.dao;

import java.util.TreeMap;

/**
 * Interfaz que declara los métodos de forma genérica, que deben implementar los
 * DAO
 */
public interface GenericDAO<T, K> {
    /**
     * Insertar un objeto
     * 
     * @param entidad
     */
    void insertar(K entidad); 

    /**
     * Actualizar un objeto
     * 
     * @param entidad
     */
    void actualizar(K entidad);

    /**
     * Borrar un objeto
     * 
     * @param entidad
     */
    void borrar(K entidad); 

    /**
     * Buscar y retornar todos los objetos en un mapa
     * 
     * @return mapa de objetos
     */
    TreeMap<T, K> buscarTodos();
}