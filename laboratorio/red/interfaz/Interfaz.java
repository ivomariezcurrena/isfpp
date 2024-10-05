package red.interfaz;
import red.modelo.*;
import java.util.*;

public class Interfaz {
     private static Scanner scanner = new Scanner(System.in);
    
    public static int opcion() {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Ping");
        System.out.println("2. Traceroute");
        System.out.println("3. Árbol de Expansión Mínimo");
        System.out.println("4. Salir");
        return scanner.nextInt();
    }

    public static String solicitarIp(String mensaje) {
        System.out.println(mensaje);
        return scanner.next();
    }

    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public static void ping(String ipAddress, Boolean activo) {
        if (activo) {
            mostrarMensaje("El equipo con IP " + ipAddress + " está activo.");
        } else {
            mostrarMensaje("El equipo con IP " + ipAddress + " no está activo o no se encuentra en la red.");
        }
    }

    public static void resultadoTraceroute(List<Conexion> camino) {
        int tiempoTotal = 0;
        for (Conexion conexion : camino) {
            Equipo origen = conexion.getSource();
            Equipo destino = conexion.getTarget();
            int tiempo = conexion.getBandwidth();
            mostrarMensaje(origen.getIpAddress() + " -> " + destino.getIpAddress() + " : " + tiempo + " ms");
            tiempoTotal += tiempo;
        }
        mostrarMensaje("Tiempo total: " + tiempoTotal + " ms");
    }

    public static void resultadoMST(List<Conexion> mst) {
        mostrarMensaje("El árbol de expansión mínima contiene las siguientes conexiones:");
        for (Conexion conexion : mst) {
            Equipo origen = conexion.getSource();
            Equipo destino = conexion.getTarget();
            double anchoBanda = conexion.getBandwidth();
            mostrarMensaje(origen.getIpAddress() + ":" + origen.getId() + " ---> " + destino.getIpAddress() + " : " + destino.getId() + " Ancho de banda: " + anchoBanda);
        }
    }
}