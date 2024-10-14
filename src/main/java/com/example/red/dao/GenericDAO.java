package com.example.red.dao;

import java.util.TreeMap;

public interface GenericDAO<T, K> {
    void insertar(K entidad); // Inserta una entidad

    void actualizar(K entidad); // Actualiza una entidad

    void borrar(K entidad); // Borra una entidad

    TreeMap<T, K> buscarTodos(); // Busca y devuelve todas las entidades en un TreeMap
}
