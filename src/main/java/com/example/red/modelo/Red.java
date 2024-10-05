package com.example.red.modelo;

import java.util.ArrayList;
import java.util.List;

public class Red {
	private String nombre;
	private List<Ubicacion> ubicaciones;
	private List<Equipo> equipos;
	private List<Conexion> conexiones;

	public Red() {

	}

	public Red(String nombre) {
		this.nombre = nombre;
		ubicaciones = new ArrayList<Ubicacion>();
		equipos = new ArrayList<Equipo>();
		conexiones = new ArrayList<Conexion>();

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Ubicacion> getUbicaciones() {
		return ubicaciones;
	}

	public List<Equipo> getEquipos() {
		return equipos;
	}

	public List<Conexion> getConexiones() {
		return conexiones;
	}

	@Override
	public String toString() {
		return "Red [nombre=" + nombre + "]";
	}

}
