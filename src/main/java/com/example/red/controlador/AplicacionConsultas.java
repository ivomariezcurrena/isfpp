package com.example.red.controlador;

import com.example.red.interfaz.Interfaz;
import com.example.red.negocio.Calculo;
import com.example.red.negocio.EquipoExistenteExeption;
import com.example.red.negocio.Red;

public class AplicacionConsultas {
    private Red red;
    private Calculo calculo;

    // vista
    private Interfaz interfaz;
    // controlador
    private Coordinador coordinador;

    public static void main(String[] args) throws EquipoExistenteExeption {
        AplicacionConsultas app = new AplicacionConsultas();
        app.iniciar();
    }

    private void iniciar() throws EquipoExistenteExeption {
        /* Se instancian las clases */
        red = Red.getRed();
        calculo = new Calculo();
        coordinador = new Coordinador();
        interfaz = new Interfaz();
        System.out.println();
    }
}
