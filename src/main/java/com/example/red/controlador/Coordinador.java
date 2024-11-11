package com.example.red.controlador;

import java.util.List;

import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.Ubicacion;
import com.example.red.negocio.Calculo;
import com.example.red.negocio.Red;

/**
 * Intermediario para gestionar el objeto que modela la red y el objeto para los
 * cálculos
 */
public class Coordinador {
    /** Referencia al objeto que modela la red */
    private Red red;
    /** Referencia al objeto que permite realizar cálculos */
    private Calculo calculo;

    /**
     * Retorna la referencia del objeto Red que posea esta instancia
     * 
     * @return red
     */
    public Red getRed() {
        return red;
    }

    /**
     * Asigna a la instancia de Coordinador, una referencia de un objeto Red
     * 
     * @param red referencia
     */
    public void setRed(Red red) {
        this.red = red;
    }

    /**
     * Retorna la referencia del objeto Calculo que posea esta instancia
     * 
     * @return calculo
     */
    public Calculo getCalculo() {
        return calculo;
    }

    /**
     * Asigna a la instancia de Coordinador, una referencia de un objeto Calculo
     * 
     * @param calculo
     */
    public void setCalculo(Calculo calculo) {
        this.calculo = calculo;
    }

    /**
     * Verifica la existencia de un equipo en la red
     * 
     * @param equipo
     * @return referencia del equipo encontrado en la red
     */
    public Equipo buscarEquipos(Equipo equipo) {
        return red.buscarEquipo(equipo);
    }

    /**
     * Lista todos los equipos que contiene la red
     * 
     * @return lista de equipos
     */
    public List<Equipo> listarEquipos() {
        return red.getEquipos();
    }

    /**
     * Lista todas las ubicaciones que contiene la red
     * 
     * @return lista de ubicaciones
     */
    public List<Ubicacion> listarUbicaciones() {
        return red.getUbicaciones();
    }

    /**
     * Lista todas las ubicaciones que contiene la red
     * 
     * @return lista de conexiones
     */
    public List<Conexion> listarConexiones() {
        return red.getConexiones();
    }
}