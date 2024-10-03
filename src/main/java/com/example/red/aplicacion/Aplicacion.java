package com.example.red.aplicacion;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.red.datos.CargarParametros;
import com.example.red.datos.Dato;
import com.example.red.logica.Logica;
import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.Red;

public class Aplicacion {
    public static void main(String[] args) {

        try {
            CargarParametros.parametros();
        } catch (IOException e) {
            System.err.print("Error al cargar parámetros");
            System.exit(-1);
        }

        Red red = null;
        try {
            red = Dato.cargarRed("unpsjb", CargarParametros.getArchivoConexion(), CargarParametros.getArchivoEquipo(),
                    CargarParametros.getArchivoTipoCable(), CargarParametros.getArchivoTipoEquipo(),
                    CargarParametros.getArchivoTipoPuerto(), CargarParametros.getArchivoUbicacion());
        } catch (FileNotFoundException e) {
            System.err.print("Error al cargar archivos de datos");
            System.exit(-1);
        }

        // Ejemplo de uso:
        for (Equipo id : red.getEquipos()) {
            System.out.println("Nodo: " + id.getDescripcion());
        }
        for (Conexion conexion : red.getConexiones()) {
            System.out.println("Conexion de " + conexion.getEquipo1().getDescripcion() + " a "
                    + conexion.getEquipo2().getDescripcion());

        }

        Logica redUni = new Logica(red);
        try {

            System.out.println("-----------Grafo cargado exitosamente.-----------");
        } catch (Exception e) {
            System.err.println("Error al cargar iel grafo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
