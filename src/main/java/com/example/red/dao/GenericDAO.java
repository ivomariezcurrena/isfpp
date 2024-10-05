package com.example.red.dao;

import java.util.List;
import java.util.TreeMap;

import com.example.red.modelo.Ubicacion;

public interface GenericDAO<Ubicacion> {
    void insertar(Ubicacion ubicacion);

    void actualizar(Ubicacion ubicacion);

    void borrar(Ubicacion ubicacion);

    TreeMap<String, Ubicacion> buscarTodos();
}
