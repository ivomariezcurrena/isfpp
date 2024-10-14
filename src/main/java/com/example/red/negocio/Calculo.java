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
	private TreeMap<String, Equipo> tablaEquipos; // ID -> Equipo
	private TreeMap<String, String> localDns; // IP -> ID

    public void setCoordinador(Coordinador coordinador){
        this.coordinador = coordinador;
    }

	// Se cargan los equipos y conexiones a una red que se usará para los cálculos
	public void cargarDatos(List<Equipo> equipos, List<Conexion> conexiones) {
		red = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		tablaEquipos = new TreeMap<>();
		localDns = new TreeMap<>();

		// Se carga la lista de equipos al grafo "red", al treemap "tablaEquipos" y se
		// genera el "localDns"
		for (Equipo equipo : equipos) {
			// red
			red.addVertex(equipo);

			// equipos
			String id = equipo.getCodigo();
			tablaEquipos.put(id, equipo);

			// localDns
			for (String ip : equipo.getDireccionesIP())
				localDns.put(ip, id);

			System.out.println("Insertar vértice con Id: " + id);
		}

		// Se carga la lista de conexiones al grafo "red"
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
				
				System.out.println(
						"Insertar arista entre: " + source.getCodigo() + " y "
								+ target.getCodigo());
				System.out.printf("Velocidad cable en s/Mb: %.5f\n ",peso);
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
	public String traceRoute(String origen, String destino) {		
		
		// Obtener los equipos
		Equipo equipoOrigen = validarEquipo(origen);
		Equipo equipoDestino = validarEquipo(destino);
		
		// Si al menos uno no se encontró se termina el método
		if (!red.containsVertex(equipoOrigen) || !red.containsVertex(equipoDestino)) {
			return "Al menos un equipo no se ha encontrado en la red (Origen: " + equipoOrigen + ", Destino: " + equipoDestino
					+ ")";
		}

		// Camino mas corto
		String mensaje = "";

		DijkstraShortestPath<Equipo, DefaultWeightedEdge> copia = new DijkstraShortestPath<Equipo, DefaultWeightedEdge>(red);
		GraphPath<Equipo,DefaultWeightedEdge> camino = copia.getPath(equipoOrigen, equipoDestino);
		
		if (camino == null) //no existe camino
			return "No se ha encontrado un camino";
		
		List<DefaultWeightedEdge> conexiones = camino.getEdgeList();
		List<Equipo> equipos = camino.getVertexList();
					
		// Se retorna la información en un mensaje
		mensaje += "Recorrido de " + equipoOrigen.getCodigo() + " a " + equipoDestino.getCodigo() +"\n";
		for (int i = 0; i < equipos.size() - 1; i++) {
			Equipo equipo1 = equipos.get(i);
			Equipo equipo2 = equipos.get(i+1);
			double peso = red.getEdgeWeight(conexiones.get(i));
			mensaje += "- " + equipo1.getCodigo() + " -> " + equipo2.getCodigo(); // origen y destino 
			mensaje += " | Velocidad: " + 1/peso + " Mbps\n"; // velocidad de conexion
		}
		
		return mensaje;
	}

	/*
	 * 2. Realizar un ping a un equipo.
	 */
	public String ping(String destino) {
		Equipo equipo = validarEquipo(destino);
		String mensaje = "Equipo '" + destino + "' ";

		if (equipo == null)
			return mensaje + "no encontrado";
					
		return mensaje + (equipo.isActivo()? "activo":"inactivo");
	}

	/**
	 * @param rango espera un rango de ip como por ej 192.234.
	 * @return mensaje con todos los equipos en ese rango y su estado.
	 * 
	 */
	public String rangoPing(String rango){
		final int IP_PRINCIPAL=0;
		String mensaje = "Estado de los equipos: \n";

		for (Equipo e : tablaEquipos.values()) {
			List<String> equipos =e.getDireccionesIP();
			String ip =equipos.get(IP_PRINCIPAL);

			if (ip.startsWith(rango)) {
				mensaje += e.getCodigo()+" " +ip +" "+(e.isActivo()? "activo\n":"inactivo\n");
			}
		}
		return mensaje;

	}
}
