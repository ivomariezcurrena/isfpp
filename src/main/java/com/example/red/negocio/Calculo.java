package com.example.red.negocio;

import java.util.List;
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
	private Map<String, String> localDns; // IP -> ID

    public void setCoordinador(Coordinador coordinador){
        this.coordinador = coordinador;
    }

	// Se cargan los equipos y conexiones a una red que se usará para los cálculos
	public void cargarDatos(Map<String, Equipo> tablaEquipos, List<Conexion> conexiones) {
		red = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		localDns = new TreeMap<>();
		this.tablaEquipos = tablaEquipos;

		// Se carga la lista de equipos al grafo "red"
		// se genera el "localDns"
		for (Equipo equipo : tablaEquipos.values()) {
			// red
			red.addVertex(equipo);

			// localDns
			String id = equipo.getCodigo();
			for (String ip : equipo.getDireccionesIP())
				localDns.put(ip, id);
		}

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

	// retorna null si no se encontró el equipo
	public Equipo validarEquipo(String identificador) {	
        // obtener por id
		Equipo equipo = tablaEquipos.get(identificador);
		if (equipo != null) 
            return equipo;
 
        //obtener por ip
        String id = localDns.get(identificador);
        if (id != null)
            equipo = tablaEquipos.get(id);
        return equipo;
	}

	/*
	 * 1. Dado dos equipos mostrar todos los equipos intermedios y sus conexiones.
	 * Calcular la velocidad máxima de acuerdo al tipo de puerto y cables por donde
	 * se transmiten los datos.
	 */	
	public List<String> traceRoute(String origen, String destino) {		
		List<String> resultado = new ArrayList<String>();
		// Obtener los equipos
		Equipo equipoOrigen = validarEquipo(origen);
		Equipo equipoDestino = validarEquipo(destino);
		
		// Si al menos uno no se encontró se termina el método
		if (!red.containsVertex(equipoOrigen) || !red.containsVertex(equipoDestino)) {
			return null;
		}

		DijkstraShortestPath<Equipo, DefaultWeightedEdge> copia = new DijkstraShortestPath<Equipo, DefaultWeightedEdge>(red);
		GraphPath<Equipo,DefaultWeightedEdge> camino = copia.getPath(equipoOrigen, equipoDestino);
		if (camino == null) //no existe camino
			return null;
;
		// Se descartan los equipos inactivos
		// Se retorna la lista de codigos
		for (Equipo equipo : camino.getVertexList()){
			if (equipo.isActivo())
				resultado.add(equipo.getCodigo());
			else // corta el camino ante el primer inactivo
				break;
		}
		return resultado;
	}

	/*
	 * 2. Realizar un ping a un equipo.
	 */
	public boolean ping(String destino) {
		Equipo equipo = validarEquipo(destino);
		if (equipo != null)
			return equipo.isActivo();
		return false;
	}

	public Map<String, Boolean> rangoPing(String rango){
		int i=0;
		Map<String, Boolean> resultado = new TreeMap<>();

		for (Equipo e : tablaEquipos.values()) {
			List<String> equipos = e.getDireccionesIP();
			String ip = equipos.get(i);
			
			if (ip.startsWith(rango)) {
				resultado.put(e.getCodigo(), e.isActivo());
			}
		}
		return resultado;
	}

}
