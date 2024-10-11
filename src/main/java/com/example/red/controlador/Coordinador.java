package com.example.red.controlador;

import java.util.List;

import com.example.red.interfaz.Interfaz;
import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.Ubicacion;
import com.example.red.negocio.Calculo;
import com.example.red.negocio.Red;

public class Coordinador {
    private Red red;
    private Calculo calculo;
    private Interfaz interfaz;

    public Red getRed() {
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

    public Calculo getCalculo() {
        return calculo;
    }

    public void setCalculo(Calculo calculo) {
        this.calculo = calculo;
    }

    public Interfaz getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(Interfaz interfaz) {
        this.interfaz = interfaz;
    }

    public Equipo buscarEquipos(Equipo equipo) {
        return red.buscarEquipo(equipo);
    }

    public List<Equipo> listarEquipos() {
        return red.getEquipos();
    }

    public List<Ubicacion> listarUbicaciones() {
        return red.getUbicaciones();
    }

    public List<Conexion> listarConexiones() {
        return red.getConexiones();
    }
}
