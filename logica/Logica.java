package com.example.red.logica;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.Red;

import java.util.*;

public class Logica {
    private Graph<Equipo, DefaultEdge> red;
    private TreeMap<String, Equipo> vertices;

    public Logica(Red redUni) {
        red = new SimpleGraph<>(DefaultEdge.class);
        vertices = new TreeMap<>();

        for (Equipo equipo : redUni.getEquipos()) {
            List<String> ips = equipo.getDireccionesIP();
            String id = equipo.getCodigo();
            red.addVertex(equipo);

            for (String ip : ips) {
                vertices.put(ip, equipo); // Usar cada IP como clave
                System.out.println("Insertar vértice con IP: " + ip + " Id: " + id);
            }
        }

        for (Conexion conexion : redUni.getConexiones()) {
            Equipo source = conexion.getEquipo1();
            Equipo target = conexion.getEquipo2();
            List<String> sourceIPs = source.getDireccionesIP();
            List<String> targetIPs = target.getDireccionesIP();

            if (!sourceIPs.isEmpty() && !targetIPs.isEmpty()) {
                String ipSource = sourceIPs.get(0);
                String ipTarget = targetIPs.get(0);

                Equipo sourceVertex = vertices.get(ipSource);
                Equipo targetVertex = vertices.get(ipTarget);

                if (sourceVertex != null && targetVertex != null) {
                    System.out.println("Insertar arista entre: " + source.getCodigo() + ": " + ipSource + " y "
                            + target.getCodigo() + ": " + ipTarget);
                    red.addEdge(sourceVertex, targetVertex); // Añadir arista
                } else {
                    System.err.println("Error: Vértice no encontrado para IPs " + ipSource + " o " + ipTarget);
                }
            }
        }
    }

    public boolean ping(String codigo) {
        for (Equipo vertex : red.vertexSet()) {
            if (vertex.getCodigo().equals(codigo)) {
                return vertex.isActivo();
            }
        }
        return false;
    }

}
