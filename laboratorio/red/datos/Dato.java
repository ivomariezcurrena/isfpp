package red.datos;

import red.modelo.*;
import java.io.*;
import java.util.*;


public class Dato {

    private static TreeMap<String, Equipo> cargarRouters(String archivoRouters) throws FileNotFoundException {
		Scanner read;

		TreeMap<String, Equipo> equipo = new TreeMap<String, Equipo>();
		
			read = new Scanner(new File(archivoRouters));
			read.useDelimiter("\\s*;\\s*");
			String id, ipAddress, macAddress, modelo, firmware, status, throughput, ubicacion;

			while (read.hasNext()) {
				id = read.next();
				ipAddress = read.next();
                macAddress = read.next();
				modelo = read.next();
                firmware = read.next();
				status = read.next();
                throughput = read.next();
                ubicacion = read.next();
				equipo.put(id, new Router(id,   ipAddress,macAddress,modelo,firmware,Boolean.parseBoolean(status),Integer.parseInt(throughput),ubicacion));
			}
			read.close();
		
		return equipo;
	}

	private static TreeMap<String, Equipo> cargarComputadoras(String archivoComputadoras) throws FileNotFoundException {
		Scanner read;
		TreeMap<String, Equipo> equipo = new TreeMap<String, Equipo>();
		
			read = new Scanner(new File(archivoComputadoras));
			read.useDelimiter("\\s*;\\s*");
			String id, ipAddress, macAddress, status, ubicacion;
			while (read.hasNext()) {				
                id = read.next();
				ipAddress = read.next();
                macAddress = read.next();
				status = read.next();
                ubicacion = read.next();
				equipo.put(id,new Computadora(id, ipAddress, macAddress,Boolean.parseBoolean(status),ubicacion));
			}
			read.close();
		
		return equipo;
	}




    public static TreeMap<String, Equipo> cargarEquipos(String archivoComputadoras, String archivoRouters) throws FileNotFoundException {
        TreeMap<String, Equipo> equipos = new TreeMap<>();

        equipos.putAll(Dato.cargarRouters(archivoRouters));
        equipos.putAll(Dato.cargarComputadoras(archivoComputadoras));
        
        return equipos;
    }

    public static List<Conexion> cargarConexiones(String archivoConexiones, TreeMap<String, Equipo> equipos) throws FileNotFoundException {
        Scanner read;
    List<Conexion> conexiones = new ArrayList<Conexion>();

    read = new Scanner(new File(archivoConexiones));
    read.useDelimiter("\\s*;\\s*");
    Equipo e1, e2;
    String idEquipo1, idEquipo2, tipoConexion, bandwidth, latencia, status, errorRate;
    while (read.hasNext()) {
        idEquipo1 = read.next();
        idEquipo2 = read.next();
        if (equipos.containsKey(idEquipo1) && equipos.containsKey(idEquipo2)) {
            e1 = equipos.get(idEquipo1);
            e2 = equipos.get(idEquipo2);
            tipoConexion = read.next();
            bandwidth = read.next();
            latencia = read.next();
            status = read.next();
            errorRate = read.next();
            conexiones.add(new Conexion(e1, e2, tipoConexion, Integer.parseInt(bandwidth), Integer.parseInt(latencia), Boolean.parseBoolean(status), Double.parseDouble(errorRate)));
        } else {
            System.err.println("Error: Los IDs de los equipos en la conexión no existen en el mapa de equipos.");
            // Puedes manejar este error de otra manera si lo prefieres
        }
    }
    read.close();

    return conexiones;
    }
}

