package com.example.red.negocio;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

import com.example.red.controlador.Constantes;
import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.TipoCable;
import com.example.red.modelo.TipoEquipo;
import com.example.red.modelo.TipoPuerto;
import com.example.red.modelo.Ubicacion;
import com.example.red.servicio.*;

/**
 * Modela la red de equipos, posee referencias a cada uno de sus componentes y
 * permite manipularlos
 * 
 */
public class Red {
	/** Referencia la instancia única de Red */
	private static Red red = null;

	/** Nombre de la red */
	private String nombre;

	/** Lista de ubicaciones en la red */
	private List<Ubicacion> ubicaciones;

	/** Servicio de gestión de ubicaciones */
	private UbicacionService ubicacionService;

	/** Lista de equipos en la red */
	private List<Equipo> equipos;

	/** Servicio de gestión de equipos */
	private EquipoService equipoService;

	/** Lista de conexiones entre equipos en la red */
	private List<Conexion> conexiones;

	/** Servicio de gestión de conexiones */
	private ConexionService conexionService;

	/** Lista de tipos de cables disponibles en la red */
	private List<TipoCable> tipoCable;

	/** Servicio de gestión de tipos de cables */
	private TipoCableService tipoCableService;

	/** Lista de tipos de equipos utilizados en la red */
	private List<TipoEquipo> tipoEquipo;

	/** Servicio de gestión de tipos de equipos */
	private TipoEquipoService TipoEquipoService;

	/** Lista de tipos de puertos disponibles en los equipos de la red */
	private List<TipoPuerto> tipoPuerto;

	/** Servicio de gestión de tipos de puertos */
	private TipoPuertoService tipoPuertoService;

	/**
	 * Retorna la referencia de la unica instancia de esta clase. Si aún no existe,
	 * se crea y se retorna
	 * 
	 * @return referencia
	 */
	public static Red getRed() {
		if (red == null) {
			red = new Red();
		}
		return red;
	}

	/** Constructor */
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

		// verificar el modo
		String modoActual = ModoRealService.getModo();
		if (Constantes.MODO_REAL.equals(modoActual))
			actualizarEstadosReales(true, null);
	}

	/**
	 * Permite actualizar los estados de actividad de los equipos reales
	 * 
	 * @param todos true si se desea actualizar la lista completa de equipos, false
	 *              si solo se quiere actualizar una lista de ellos
	 * @param ids   lista de IDs que indica qué equipos se desean actualizar. En
	 *              caso de que el parámetro 'todos' sea true, se ignora este
	 *              parámetro
	 */
	public void actualizarEstadosReales(boolean todos, List<String> ids) {
		ModoRealService.cargarEstadosReales(equipos, todos, ids);
	}

	/**
	 * Permite agregar un equipo a la red
	 * 
	 * @param equipo
	 * @throws EquipoExistenteExeption si ya existe
	 */
	public void agregarEquipo(Equipo equipo) throws EquipoExistenteExeption {
		if (equipos.contains(equipo))
			throw new EquipoExistenteExeption();
		equipos.add(equipo);
		equipoService.insertar(equipo);
	}

	/**
	 * Permite modificar un equipo de la red
	 * 
	 * @param equipo
	 */
	public void modificarEquipo(Equipo equipo) {
		int pos = equipos.indexOf(equipo);
		equipos.set(pos, equipo);
		equipoService.actualizar(equipo);
	}

	/**
	 * Permite eliminar un equipo de la red
	 * 
	 * @param equipo
	 */
	public void borrarEquipo(Equipo equipo) {
		Equipo emp = buscarEquipo(equipo);
		equipos.remove(emp);
		equipoService.borrar(equipo);
	}

	/**
	 * Permite buscar un equipo en la red
	 * 
	 * @param equipo
	 * @return null si no lo encontró
	 */
	public Equipo buscarEquipo(Equipo equipo) {
		int pos = equipos.indexOf(equipo);
		if (pos == -1)
			return null;
		return equipos.get(pos);
	}

	/**
	 * Permite obtener el nombre de la red
	 * 
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Permite asignar el nombre de la red
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Lista las ubicaciones de la red.
	 *
	 * @return lista de ubicaciones en la red
	 */
	public List<Ubicacion> getUbicaciones() {
		return ubicaciones;
	}

	/**
	 * Obtiene la lista de equipos de la red.
	 *
	 * @return lista de equipos en la red
	 */
	public List<Equipo> getEquipos() {
		return equipos;
	}

	/**
	 * Obtiene la lista de conexiones entre los equipos en la red.
	 *
	 * @return lista de conexiones en la red
	 */
	public List<Conexion> getConexiones() {
		return conexiones;
	}

	/**
	 * Obtiene la lista de tipos de cables disponibles en la red.
	 *
	 * @return lista de tipos de cables
	 */
	public List<TipoCable> getTipoCable() {
		return tipoCable;
	}

	/**
	 * Obtiene la lista de tipos de equipos utilizados en la red.
	 *
	 * @return lista de tipos de equipos
	 */
	public List<TipoEquipo> getTipoEquipos() {
		return tipoEquipo;
	}

	/**
	 * Obtiene la lista de tipos de puertos disponibles en los equipos de la red.
	 *
	 * @return lista de tipos de puertos
	 */
	public List<TipoPuerto> getTipoPuertos() {
		return tipoPuerto;
	}

	/**
	 * Obtiene la lista de tipos de equipos en la red.
	 *
	 * @return lista de tipos de equipos
	 */
	public List<TipoEquipo> getTipoEquipo() {
		return tipoEquipo;
	}

	/**
	 * Obtiene la lista de tipos de puertos en la red.
	 *
	 * @return lista de tipos de puertos
	 */
	public List<TipoPuerto> getTipoPuerto() {
		return tipoPuerto;
	}

	/**
	 * Genera y retorna un nuevo mapa de equipos por ID
	 * 
	 * @return mapa
	 */
	public Map<String, Equipo> getTablaEquipos() {
		Map<String, Equipo> tablaEquipos = new TreeMap<>(); // Tabla "ID" -> Equipo
		for (Equipo equipo : equipos)
			tablaEquipos.put(equipo.getCodigo(), equipo);
		return tablaEquipos;
	}

	/**
	 * Genera y retorna un nuevo mapa de conexiones. La clave de cada entrada es una
	 * combinación de los IDs de los equipos de cada extremo de la conexión, por
	 * ejemplo "AP01 AP02"
	 * 
	 * @return mapa
	 */
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

	/**
	 * Genera y retorna un nuevo mapa que asocia la dirección IP con el ID de cada
	 * equipo. La clave es la IP y el valor el ID
	 * 
	 * @return mapa
	 */
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

	/**
	 * Valida si un equipo existe
	 * 
	 * @param Identificador (ID o IP) del equipo
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

	/**
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
