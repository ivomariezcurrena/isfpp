package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.modelo.TipoPuerto;

public interface TipoPuertoService {

    void insertar(TipoPuerto tipoPuerto);

    void actualizar(TipoPuerto tipoPuerto);

    void borrar(TipoPuerto tipoPuerto);

    TreeMap<String, TipoPuerto> buscarTodos();
}
