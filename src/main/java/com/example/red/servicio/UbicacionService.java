package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.modelo.Ubicacion;

public interface UbicacionService {

	void insertar(Ubicacion ubicacion);

	void actualizar(Ubicacion ubicacion);

	void borrar(Ubicacion ubicacion);

	TreeMap<String, Ubicacion> buscarTodos();
}
