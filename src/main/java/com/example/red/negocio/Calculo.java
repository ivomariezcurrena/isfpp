package com.example.red.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.example.red.controlador.Coordinador;
import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;

public class Calculo {
    private Coordinador coordinador;
	private Graph<Equipo, DefaultWeightedEdge> red; // red para hacer los cálculos
	private Map<String, Equipo> tablaEquipos; // "ID" -> Equipo

    public void setCoordinador(Coordinador coordinador){
        this.coordinador = coordinador;
    }

	// Se cargan los equipos y conexiones a una red que se usará para los cálculos
	public void cargarDatos(Map<String, Equipo> tablaEquipos, List<Conexion> conexiones) {
		red = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.tablaEquipos = tablaEquipos;

		// Se cargan los equipos al grafo "red"
		for (Equipo equipo : tablaEquipos.values()) 
			red.addVertex(equipo);

		// Se cargan las conexiones
		for (Conexion conexion : conexiones) {
			Equipo source = conexion.getEquipo1();
			Equipo target = conexion.getEquipo2();

			if (source != null || target != null) {
				DefaultWeightedEdge edge = red.addEdge(source, target); // Añadir arista
				// Asignar peso de la arista
				// El peso es la menor velocidad entre la velocidad de cable y la de los puertos
				int velocidadMaxima = Math.min(source.getVelocidadPuerto(0), target.getVelocidadPuerto(0));
				velocidadMaxima = Math.min(velocidadMaxima, conexion.getTipocable().getVelocidad());
				double peso = (1/ (double) velocidadMaxima);
				red.setEdgeWeight(edge, peso);
			}
		}
	}

	/*
	 * Dado dos equipos mostrar todos los equipos intermedios y sus conexiones.
	 * Calcular la velocidad máxima de acuerdo al tipo de puerto y cables por donde
	 * se transmiten los datos.
	 * 
	 * @param ID del equipo origen
	 * @param ID del equipo destino
	 * @return lista de IDs de los equipos que forman el camino, null si no existe camino
	 */
	public List<String> traceRoute(String origen, String destino) {		
		List<String> resultado = new ArrayList<String>();
		// Obtener los equipos
		Equipo equipoOrigen = tablaEquipos.get(origen);
		Equipo equipoDestino = tablaEquipos.get(destino);
		
		// Si al menos uno no se encontró se termina el método
		if (!red.containsVertex(equipoOrigen) || !red.containsVertex(equipoDestino)) {
			return null;
		}

		// Hallar el camino, sin importar la inactividad de los equipos
		DijkstraShortestPath<Equipo, DefaultWeightedEdge> copia = new DijkstraShortestPath<Equipo, DefaultWeightedEdge>(red);
		GraphPath<Equipo,DefaultWeightedEdge> camino = copia.getPath(equipoOrigen, equipoDestino);
		if (camino == null) //no existe camino
			return null;

		// Cortar el camino ante el primer equipo inactivo
		// Se retorna la lista de codigos de equipos
		for (Equipo equipo : camino.getVertexList()){
			if (equipo.isActivo())
				resultado.add(equipo.getCodigo());
			else 
				break;
		}
		return resultado;
	}

	/*
	 * Realizar un ping a un equipo
	 * 
	 * @param ID del equipo
	 * @return true si está activo, false si está inactivo
	 */
	public boolean ping(String destino) {
		Equipo equipo = tablaEquipos.get(destino);
		if (equipo != null)
			return equipo.isActivo();
		return false;
	}

	/*
	 * Realizar un ping a una lista de equipos
	 * 
	 * @param lista de "ID" de equipos
	 * @return Mapa con entries "ID" -> true/false segun su actividad
	 */
	public Map<String, Boolean> rangoPing(List<String> ids){
		Map<String, Boolean> resultado = new TreeMap<>();

		for (String id : ids){
			Equipo equipo = tablaEquipos.get(id);
			resultado.put(id, equipo.isActivo());
		}

		return resultado;
	}
}