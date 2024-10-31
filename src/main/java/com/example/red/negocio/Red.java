package com.example.red.negocio;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.TipoCable;
import com.example.red.modelo.TipoEquipo;
import com.example.red.modelo.TipoPuerto;
import com.example.red.modelo.Ubicacion;
import com.example.red.servicio.ConexionService;
import com.example.red.servicio.ConexionServiceImpl;
import com.example.red.servicio.EquipoService;
import com.example.red.servicio.EquipoServiceImpl;
import com.example.red.servicio.TipoCableService;
import com.example.red.servicio.UbicacionService;
import com.example.red.servicio.*;

public class Red {
	private static Red red = null;

	private String nombre;
	private List<Ubicacion> ubicaciones;
	private UbicacionService ubicacionService;
	private List<Equipo> equipos;
	private EquipoService equipoService;
	private List<Conexion> conexiones;
	private ConexionService conexionService;
	private List<TipoCable> tipoCable;
	private TipoCableService tipoCableService;
	private List<TipoEquipo> tipoEquipo;
	private TipoEquipoService TipoEquipoService;
	private List<TipoPuerto> tipoPuerto;
	private TipoPuertoService tipoPuertoService;

	public static Red getRed() {
		if (red == null) {
			red = new Red();
		}
		return red;
	}

	public Red() {
		super();
		// ubis
		ubicaciones = new ArrayList<Ubicacion>();
		ubicacionService = new UbicacionServiceImpl();
		ubicaciones.addAll(ubicacionService.buscarTodos().values());

		// Equipos
		equipos = new ArrayList<Equipo>();
		equipoService = new EquipoServiceImpl();
		equipos.addAll(equipoService.buscarTodos().values());

		// Conexiones
		conexiones = new ArrayList<Conexion>();
		conexionService = new ConexionServiceImpl();
		conexiones.addAll(conexionService.buscarTodos().values());

		// TipoCable
		tipoCable = new ArrayList<TipoCable>();
		tipoCableService = new TipoCableServiceImpl();
		tipoCable.addAll(tipoCableService.buscarTodos().values());

		// TipoEquipo
		tipoEquipo = new ArrayList<TipoEquipo>();
		TipoEquipoService = new TipoEquipoServiceImpl();
		tipoEquipo.addAll(TipoEquipoService.buscarTodos().values());

		// TipoPuerto
		tipoPuerto = new ArrayList<TipoPuerto>();
		tipoPuertoService = new TipoPuertoServiceImpl();
		tipoPuerto.addAll(tipoPuertoService.buscarTodos().values());
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

	public List<TipoCable> getTipoCable() {
		return tipoCable;
	}

	public List<TipoEquipo> getTipoEquipos() {
		return tipoEquipo;
	}

	public List<TipoPuerto> getTipoPuertos() {
		return tipoPuerto;
	}

	public List<TipoEquipo> getTipoEquipo() {
		return tipoEquipo;
	}

	public List<TipoPuerto> getTipoPuerto() {
		return tipoPuerto;
	}

	public Map<String, Equipo> getTablaEquipos() {
		Map<String, Equipo> tablaEquipos = new TreeMap<>(); // Tabla "ID" -> Equipo
		for (Equipo equipo : equipos)
			tablaEquipos.put(equipo.getCodigo(), equipo);
		return tablaEquipos;
	}

	public Map<String, Conexion> getTablaConexiones() {
		Map<String, Conexion> tablaConexiones = new TreeMap<>(); // Tabla "ID ID" -> Conexion
		for (Conexion conexion : conexiones) {
			Equipo source = conexion.getEquipo1();
			Equipo target = conexion.getEquipo2();
			if (source != null || target != null) {
				String clave = source.getCodigo() + " " + target.getCodigo();
				tablaConexiones.put(clave, conexion);
			}
		}
		return tablaConexiones;
	}

	public Map<String, String> getLocalDns() {
		Map<String, String> localDns = new TreeMap<>();
		for (Equipo equipo : equipos) {
			if (equipo != null) {
				String id = equipo.getCodigo();
				if (id != null) {
					for (String ip : equipo.getDireccionesIP()) {
						if (ip != null) {
							localDns.put(ip, id);
						}
					}
				}
			}
		}
		return localDns;
	}

	/*
	 * Valida si un equipo existe
	 * 
	 * @param Identificador (ID o IP) del equipo
	 * 
	 * @return ID del equipo, o null si no se encontró
	 */
	public String validarEquipo(String identificador) {
		Map<String, String> localDns = getLocalDns();
		Map<String, Equipo> tablaEquipos = getTablaEquipos();

		// obtener por id
		if (tablaEquipos.get(identificador) != null) // Se encontró, existe
			return identificador;

		// obtener por ip
		String id = localDns.get(identificador);
		if (id != null)
			return id;

		return null;
	}

	/*
	 * Valida un rango de equipos por sus IP
	 * 
	 * @param IP para un rango de equipos
	 * 
	 * @return lista de IDs de equipos, lista vacia si no hay ninguno
	 */
	public List<String> rangoEquiposIP(String rango) {
		List<String> resultado = new ArrayList<>();
		Map<String, Equipo> tablaEquipos = getTablaEquipos();

		for (Equipo e : tablaEquipos.values()) {
			List<String> direccionesIP = e.getDireccionesIP();
			for (String ip : direccionesIP) {
				if (ip != null && ip.startsWith(rango)) {
					resultado.add(e.getCodigo());
					break; // Para evitar añadir múltiples veces el mismo equipo
				}
			}
		}

		return resultado;
	}

	@Override
	public String toString() {
		return "Red [nombre=" + nombre + "]";
	}

}
