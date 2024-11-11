package com.example.red.servicio;

import java.util.TreeMap;
import com.example.red.modelo.Ubicacion;

/**
 * Interfaz que define las operaciones para gestionar ubicaciones
 * Provee los métodos para insertar, leer, modificar y eliminar
 */
public interface UbicacionService {

	/**
	 * Insertar una nueva ubicacion en la red
	 * 
	 * @param ubicacion
	 */
	void insertar(Ubicacion ubicacion);

	/**
	 * Actualizar una ubicacion de la red
	 * 
	 * @param ubicacion
	 */
	void actualizar(Ubicacion ubicacion);

	/**
	 * Borrar una ubicacion de la red
	 * 
	 * @param ubicacion
	 */
	void borrar(Ubicacion ubicacion);

	/**
	 * Obtener todas las ubicaciones de la red
	 * 
	 * @return mapa de ubicaciones
	 */
	TreeMap<String, Ubicacion> buscarTodos();
}
