package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.modelo.Conexion;

public interface ConexionService {
    void insertar(Conexion conexion);

    void actualizar(Conexion conexion);

    void borrar(Conexion conexion);

    TreeMap<String, Object> buscarTodos();
}
