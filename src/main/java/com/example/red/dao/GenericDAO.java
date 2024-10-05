package com.example.red.dao;

import java.util.TreeMap;

public interface GenericDAO<T, K> {
    void insertar(T entidad); // Inserta una entidad

    void actualizar(T entidad); // Actualiza una entidad

    void borrar(T entidad); // Borra una entidad

    TreeMap<K, T> buscarTodos(); // Busca y devuelve todas las entidades en un TreeMap
}
