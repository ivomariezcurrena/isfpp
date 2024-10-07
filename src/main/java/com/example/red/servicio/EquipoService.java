package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.modelo.Equipo;

public interface EquipoService {
    void insertar(Equipo equipo);

    void actualizar(Equipo equipo);

    void borrar(Equipo equipo);

    TreeMap<String, Object> buscarTodos();
}
