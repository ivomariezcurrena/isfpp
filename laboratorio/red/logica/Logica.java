package red.logica;

import java.util.*;

import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Edge;
import net.datastructures.Graph;
import net.datastructures.GraphAlgorithms;
import net.datastructures.Position;
import net.datastructures.PositionalList;
import net.datastructures.Vertex;
import red.modelo.Conexion;
import red.modelo.Equipo;

public class Logica {
    private Graph<Equipo, Conexion> red;
    private TreeMap<String, Vertex<Equipo>> vertices;

    public Logica(TreeMap<String, Equipo> equipos, List<Conexion> conexiones) {
        red = new AdjacencyMapGraph<>(false);
        vertices = new TreeMap<>();

        // Cargar equipos
        for (Map.Entry<String, Equipo> entry : equipos.entrySet()) {
            String ip = entry.getValue().getIpAddress();
            String id = entry.getValue().getId();
            Vertex<Equipo> vertex = red.insertVertex(entry.getValue());
            vertices.put(ip, vertex); // Usar la IP como clave
            System.out.println("Insertar vértice: " + ip + " Id: " + id);
        }

        // Cargar conexiones
        for (Conexion conexion : conexiones) {
            String ipSource = conexion.getSource().getIpAddress();
            String ipTarget = conexion.getTarget().getIpAddress();
            String idSource = conexion.getSource().getId();
            String idTarget = conexion.getTarget().getId();

            Vertex<Equipo> sourceVertex = vertices.get(ipSource);
            Vertex<Equipo> targetVertex = vertices.get(ipTarget);

            if (sourceVertex != null && targetVertex != null) {
                System.out.println(
                        "Insertar arista entre: " + idSource + ": " + ipSource + " y " + idTarget + ": " + ipTarget);
                red.insertEdge(sourceVertex, targetVertex, conexion);
            } else {
                System.err.println("Error: Vértice no encontrado para IPs " + ipSource + " o " + ipTarget);
            }
        }
    }

    public List<Conexion> traceroute(String ipAddressOrigen, String ipAddressDestino) {
        // Crear un grafo para el traceroute
        Graph<Equipo, Double> tracerouteGrafo = new AdjacencyMapGraph<>(false);
        Map<Equipo, Vertex<Equipo>> verticesMap = new HashMap<>();

        // Clonar vetices del grafo original, si estan activos
        for (Vertex<Equipo> vertex : red.vertices()) {
            if (ping(vertex.getElement().getIpAddress())) {
                verticesMap.put(vertex.getElement(), tracerouteGrafo.insertVertex(vertex.getElement()));
            }
        }

        // Clonar aristas con pesos invertidos (1 / bandwidth), si las aristas estan activas
        for (Edge<Conexion> edge : red.edges()) {
            if (edge.getElement().isStatus()) {
                Vertex<Equipo>[] endpoints = red.endVertices(edge);
                Equipo equipoOrigen = endpoints[0].getElement();
                Equipo equipoDestino = endpoints[1].getElement();

                if (verticesMap.containsKey(equipoOrigen) && verticesMap.containsKey(equipoDestino)) {
                    double inversoBandwidth = 1.0 / edge.getElement().getBandwidth(); // Inverso del ancho de banda
                    tracerouteGrafo.insertEdge(verticesMap.get(equipoOrigen), verticesMap.get(equipoDestino),
                            inversoBandwidth);
                }
            }
        }

        // Buscar vertices en el grafo clonado
        Vertex<Equipo> origen = verticesMap.get(vertices.get(ipAddressOrigen).getElement());
        Vertex<Equipo> destino = verticesMap.get(vertices.get(ipAddressDestino).getElement());

        //Verifica que las direcciones esten en el grafo
        if (origen == null || destino == null) {
            System.err.println("Error: Una de las direcciones IP no se encuentra en el grafo o esta desactivada.");
            return Collections.emptyList();
        }

        // Verificar si existe un camino entre origen y destino
        if (!existeCamino(tracerouteGrafo, origen, destino)) {
            System.err.println("Error: No existe un camino entre los nodos de origen y destino.");
            return Collections.emptyList();
        }

        // Ejecutar Dijkstra con el grafo modificado
        PositionalList<Vertex<Equipo>> caminoVertices = GraphAlgorithms.shortestPathList(tracerouteGrafo, origen,
                destino);

        // Convertir la lista de vertices en una lista de conexiones
        List<Conexion> camino = new ArrayList<>();
        Position<Vertex<Equipo>> current = caminoVertices.first();
        while (caminoVertices.after(current) != null) {
            Vertex<Equipo> v1 = current.getElement();
            current = caminoVertices.after(current);
            Vertex<Equipo> v2 = current.getElement();
            Edge<Conexion> edge = red.getEdge(vertices.get(v1.getElement().getIpAddress()),
                    vertices.get(v2.getElement().getIpAddress()));
            camino.add(edge.getElement());
        }

        return camino;
    }

    public boolean ping(String ipAddress) {
        for (Vertex<Equipo> vertex : red.vertices()) {
            Equipo equipo = vertex.getElement();
            if (equipo.getIpAddress().equals(ipAddress)) {
                return equipo.isStatus();
            }
        }
        return false;
    }

    //Metodo arbol de expansion minima
    public List<Conexion> calcularMST() {
        // Crear un grafo para el MST
        Graph<Equipo, Double> mstGrafo = new AdjacencyMapGraph<>(false);
        Map<Equipo, Vertex<Equipo>> verticesMap = new HashMap<>();

        // Clonar V del grafo original si estan activos
        for (Vertex<Equipo> vertex : red.vertices()) {
            if (ping(vertex.getElement().getIpAddress())) {
                verticesMap.put(vertex.getElement(), mstGrafo.insertVertex(vertex.getElement()));
            }
        }

        // Clonar aristas con el inverso del ancho de banda como peso si estan activos
        for (Edge<Conexion> edge : red.edges()) {
            if (edge.getElement().isStatus()) {
                Vertex<Equipo>[] endpoints = red.endVertices(edge);
                Equipo equipoOrigen = endpoints[0].getElement();
                Equipo equipoDestino = endpoints[1].getElement();

                if (verticesMap.containsKey(equipoOrigen) && verticesMap.containsKey(equipoDestino)) {
                    double inversoBandwidth = 1.0 / edge.getElement().getBandwidth(); // Inverso del ancho de banda
                    mstGrafo.insertEdge(verticesMap.get(equipoOrigen), verticesMap.get(equipoDestino),
                            inversoBandwidth);
                }
            }
        }

        PositionalList<Edge<Double>> mstEdges = GraphAlgorithms.MST(mstGrafo);

        List<Conexion> mstConexiones = new ArrayList<>();
        for (Edge<Double> edge : mstEdges) {
            Vertex<Equipo>[] vertices = mstGrafo.endVertices(edge);
            Equipo equipoOrigen = vertices[0].getElement();
            Equipo equipoDestino = vertices[1].getElement();

            // Buscar la arista original en el grafo red
            Edge<Conexion> originalEdge = red.getEdge(
                    this.vertices.get(equipoOrigen.getIpAddress()),
                    this.vertices.get(equipoDestino.getIpAddress()));

            // Agregar la conexión original a la lista del MST
            if (originalEdge != null) {
                mstConexiones.add(originalEdge.getElement());
            }
        }
        return mstConexiones;
    }


    /**
    *  auxiliar para verificar si existe un camino entre origen y destino
    * en el grafo
    */
    private boolean existeCamino(Graph<Equipo, Double> grafo, Vertex<Equipo> origen, Vertex<Equipo> destino) {
        Set<Vertex<Equipo>> visitados = new HashSet<>();
        return dfs(grafo, origen, destino, visitados);
    }

    /**
    * Algoritmo DFS para buscar un camino en el grafo
    */
    private boolean dfs(Graph<Equipo, Double> grafo, Vertex<Equipo> actual, Vertex<Equipo> destino,
            Set<Vertex<Equipo>> visitados) {
        if (actual.equals(destino))
            return true;
        visitados.add(actual);
        for (Edge<Double> edge : grafo.outgoingEdges(actual)) {
            Vertex<Equipo> vecino = grafo.opposite(actual, edge);
            if (!visitados.contains(vecino) && dfs(grafo, vecino, destino, visitados)) {
                return true;
            }
        }
        return false;
    }
}
