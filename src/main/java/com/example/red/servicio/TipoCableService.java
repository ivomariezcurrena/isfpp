package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.modelo.TipoCable;

public interface TipoCableService {
    void insertar(TipoCable tipoCable);

    void actualizar(TipoCable tipoCable);

    void borrar(TipoCable tipoCable);

    TreeMap<String, TipoCable> buscarTodos();
}
