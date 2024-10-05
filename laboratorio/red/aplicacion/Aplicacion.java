package red.aplicacion;
import red.modelo.*;
import red.datos.*;
import red.interfaz.*;
import red.logica.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;
public class Aplicacion {
    public static void main(String[] args) throws FileNotFoundException {
        // Cargar parámetros
        try {
            CargarParametros.parametros();
        } catch (IOException e) {
            System.err.print("Error al cargar parámetros");
            System.exit(-1);
        }

        // Cargar datos
        TreeMap<String, Equipo> equipos = null;
        List<Conexion> conexiones = null;

        try {
            equipos = Dato.cargarEquipos(CargarParametros.getArchivoComputadoras(),
                    CargarParametros.getArchivoRouters());
            conexiones = Dato.cargarConexiones(CargarParametros.getArchivoConexiones(), equipos);
        } catch (FileNotFoundException e) {
            System.err.print("Error al cargar archivos de datos");
            System.exit(-1);
        }
        // Ejemplo de uso:
        for (String id : equipos.keySet()) {
            System.out.println("Nodo ID: " + id);
        }
        for (Conexion conexion : conexiones) {
            System.out.println("Conexion de " + conexion.getSource().getId() + " a " + conexion.getTarget().getId());

        }

        // Crear grafo
        Logica red = new Logica(equipos, conexiones);
        try {

            System.out.println("-----------Grafo cargado exitosamente.-----------");
        } catch (Exception e) {
            System.err.println("Error al cargar iel grafo: " + e.getMessage());
            e.printStackTrace();
        }
        while (true) {
            int opcion = Interfaz.opcion();
            switch (opcion) {
                case 1:
                    String ipAddress = Interfaz.solicitarIp("Ingrese la dirección IP que desea hacer ping:");
                    boolean activo = red.ping(ipAddress);
                    Interfaz.ping(ipAddress, activo);
                    break;

                case 2:
                    String ipOrigen = Interfaz.solicitarIp("Ingrese la dirección IP de origen:");
                    String ipDestino = Interfaz.solicitarIp("Ingrese la dirección IP de destino:");
                    List<Conexion> camino = red.traceroute(ipOrigen, ipDestino);
                    Interfaz.mostrarMensaje("Traceroute desde " + ipOrigen + " hasta " + ipDestino + ":");
                    Interfaz.resultadoTraceroute(camino);
                    break;

                case 3:
                    List<Conexion> mst = red.calcularMST();
                    Interfaz.mostrarMensaje("Árbol de Expansión Mínima:");
                    Interfaz.resultadoMST(mst);
                    break;

                case 4:
                    Interfaz.mostrarMensaje("Saliendo del programa.");
                   
                    System.exit(0);
                    break;
            }
        }
    }
}