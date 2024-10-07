package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.modelo.TipoEquipo;

public interface TipoEquipoService {
    void insertar(TipoEquipo tipoEquipo);

    void actualizar(TipoEquipo tipoEquipo);

    void borrar(TipoEquipo tipoEquipo);

    TreeMap<String, Object> buscarTodos();
}
