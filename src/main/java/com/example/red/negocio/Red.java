package com.example.red.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.Ubicacion;
import com.example.red.servicio.ConexionService;
import com.example.red.servicio.ConexionServiceImpl;
import com.example.red.servicio.EquipoService;
import com.example.red.servicio.EquipoServiceImpl;
import com.example.red.servicio.UbicacionService;
import com.example.red.servicio.UbicacionServiceImpl;

public class Red {
	private static Red red = null;

	private String nombre;
	private List<Ubicacion> ubicaciones;
	private UbicacionService ubicacionService;
	private List<Equipo> equipos;
	private EquipoService equipoService;
	private List<Conexion> conexiones;
	private ConexionService conexionService;

	public static Red getRed() {
		if (red == null) {
			red = new Red();
		}
		return red;
	}

	@SuppressWarnings("unchecked")
	private Red() {
		super();
		// ubis
		ubicaciones = new ArrayList<Ubicacion>();
		ubicacionService = new UbicacionServiceImpl();
		ubicaciones.addAll(((TreeMap<String, Ubicacion>) ubicacionService).values());

		// Equipos
		equipos = new ArrayList<Equipo>();
		equipoService = new EquipoServiceImpl();
		equipos.addAll(((TreeMap<String, Equipo>) equipoService).values());

		// Conexiones
		conexiones = new ArrayList<Conexion>();
		conexionService = new ConexionServiceImpl();
		conexiones.addAll(((TreeMap<String, Conexion>) conexionService).values());
	}

	public void agregarEquipo(Equipo equipo) throws EquipoExistenteExeption {
		if (equipos.contains(equipo))
			throw new EquipoExistenteExeption();
		equipos.add(equipo);
		equipoService.insertar(equipo);
	}

	public void modificarEquipo(Equipo equipo) {
		int pos = equipos.indexOf(equipo);
		equipos.set(pos, equipo);
		equipoService.actualizar(equipo);
	}

	public void borrarEquipo(Equipo equipo) {
		Equipo emp = buscarEquipo(equipo);
		equipos.remove(emp);
		equipoService.borrar(equipo);
	}

	public Equipo buscarEquipo(Equipo equipo) {
		int pos = equipos.indexOf(equipo);
		if (pos == -1)
			return null;
		return equipos.get(pos);
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
