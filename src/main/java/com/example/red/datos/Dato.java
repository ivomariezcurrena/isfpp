package com.example.red.datos;

import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.Red;
import com.example.red.modelo.TipoCable;
import com.example.red.modelo.TipoEquipo;
import com.example.red.modelo.TipoPuerto;
import com.example.red.modelo.Ubicacion;

import java.io.*;
import java.util.*;

public class Dato {
    private static TreeMap<String, TipoCable> cargarTipoCables(String archivoTipoCable) throws FileNotFoundException {
        TreeMap<String, TipoCable> tipoCables = new TreeMap<>();
        Scanner read = new Scanner(new File(archivoTipoCable));
        read.useDelimiter("\\s*;\\s*");

        while (read.hasNext()) {
            String codigo = read.next();
            String descripcion = read.next();
            int velocidad = read.nextInt();
            tipoCables.put(codigo, new TipoCable(codigo, descripcion, velocidad));
        }

        read.close();
        return tipoCables;
    }

    private static TreeMap<String, TipoEquipo> cargarTipoEquipos(String archivoTipoEquipo)
            throws FileNotFoundException {
        TreeMap<String, TipoEquipo> tipoEquipos = new TreeMap<>();
        Scanner read = new Scanner(new File(archivoTipoEquipo));
        read.useDelimiter("\\s*;\\s*");

        while (read.hasNext()) {
            String codigo = read.next();
            String descripcion = read.next();
            tipoEquipos.put(codigo, new TipoEquipo(codigo, descripcion));
        }

        read.close();
        return tipoEquipos;
    }

    private static TreeMap<String, TipoPuerto> cargarTipoPuertos(String archivoTipoPuerto)
            throws FileNotFoundException {
        TreeMap<String, TipoPuerto> tipoPuertos = new TreeMap<>();
        Scanner read = new Scanner(new File(archivoTipoPuerto));
        read.useDelimiter("\\s*;\\s*");

        while (read.hasNext()) {
            String codigo = read.next();
            String descripcion = read.next();
            int velocidad = read.nextInt();
            tipoPuertos.put(codigo, new TipoPuerto(codigo, descripcion, velocidad));
        }

        read.close();
        return tipoPuertos;
    }

    private static TreeMap<String, Ubicacion> cargarUbicaciones(String archivoUbicacion) throws FileNotFoundException {
        TreeMap<String, Ubicacion> ubicaciones = new TreeMap<>();
        Scanner read = new Scanner(new File(archivoUbicacion));
        read.useDelimiter("\\s*;\\s*");

        while (read.hasNext()) {
            String codigo = read.next();
            String descripcion = read.next();
            ubicaciones.put(codigo, new Ubicacion(codigo, descripcion));
        }

        read.close();
        return ubicaciones;
    }

    private static List<Equipo> cargarEquipos(String archivoEquipos, TreeMap<String, TipoEquipo> tipoEquipos,
            TreeMap<String, Ubicacion> ubicaciones, TreeMap<String, TipoPuerto> tipoPuertos)
            throws FileNotFoundException {
        List<Equipo> equipos = new ArrayList<>();
        Scanner read = new Scanner(new File(archivoEquipos));
        read.useDelimiter("\\s*;\\s*");

        while (read.hasNext()) {
            String codigo = read.next();
            String descripcion = read.next();
            String marca = read.next();
            String modelo = read.next();
            TipoEquipo tipoEquipo = tipoEquipos.get(read.next());
            Ubicacion ubicacion = ubicaciones.get(read.next());
            String[] puertoDatos = read.next().split(",");
            String[] direccionesIP = read.next().split(",");
            boolean activo = Boolean.parseBoolean(read.next());

            Equipo equipo = new Equipo(codigo, descripcion, marca, modelo, tipoEquipo, ubicacion, activo);

            // Añadir los puertos
            for (int i = 0; i < puertoDatos.length; i += 2) {
                TipoPuerto tipoPuerto = tipoPuertos.get(puertoDatos[i]);
                int cantidad = Integer.parseInt(puertoDatos[i + 1]);
                equipo.agregarPuerto(tipoPuerto, cantidad);
            }

            // Añadir direcciones IP
            Collections.addAll(equipo.getDireccionesIP(), direccionesIP);

            equipos.add(equipo);
        }

        read.close();
        return equipos;
    }

    private static List<Conexion> cargarConexiones(String archivoConexiones, Map<String, Equipo> equipos,
            Map<String, TipoCable> tipoCables) throws FileNotFoundException {
        List<Conexion> conexiones = new ArrayList<>();
        Scanner read = new Scanner(new File(archivoConexiones));
        read.useDelimiter("\\s*;\\s*");

        while (read.hasNext()) {
            Equipo equipo1 = equipos.get(read.next());
            Equipo equipo2 = equipos.get(read.next());
            TipoCable tipoCable = tipoCables.get(read.next());

            conexiones.add(new Conexion(equipo1, equipo2, tipoCable));
        }

        read.close();
        return conexiones;
    }

    public static Red cargarRed(String nombreRed, String archivoConexiones, String archivoEquipos,
            String archivoTipoCable, String archivoTipoEquipo, String archivoTipoPuerto, String archivoUbicaciones)
            throws FileNotFoundException {
        Red red = new Red(nombreRed);

        TreeMap<String, TipoCable> tipoCables = cargarTipoCables(archivoTipoCable);
        TreeMap<String, TipoEquipo> tipoEquipos = cargarTipoEquipos(archivoTipoEquipo);
        TreeMap<String, TipoPuerto> tipoPuertos = cargarTipoPuertos(archivoTipoPuerto);
        TreeMap<String, Ubicacion> ubicaciones = cargarUbicaciones(archivoUbicaciones);

        // Cargar equipos
        List<Equipo> equipos = cargarEquipos(archivoEquipos, tipoEquipos, ubicaciones, tipoPuertos);

        // Crear un TreeMap para acceder a los equipos por su codigo
        TreeMap<String, Equipo> equipoMap = new TreeMap<>();
        for (Equipo equipo : equipos) {
            equipoMap.put(equipo.getCodigo(), equipo);
        }

        // Cargar conexiones
        List<Conexion> conexiones = cargarConexiones(archivoConexiones, equipoMap, tipoCables);

        // Agregar a la red
        red.getEquipos().addAll(equipos);
        red.getConexiones().addAll(conexiones);

        return red;
    }
}
