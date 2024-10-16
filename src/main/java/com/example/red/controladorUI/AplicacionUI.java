package com.example.red.controladorUI;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.example.red.controlador.AplicacionConsultas;
import com.example.red.controlador.Coordinador;
import com.example.red.interfaz_ui.InterfazUI;
import com.example.red.negocio.Calculo;
import com.example.red.negocio.EquipoExistenteExeption;
import com.example.red.negocio.Red;

public class AplicacionUI {
    private Red red;
    private Calculo calculo;

    private InterfazUI interfaz;
    // controlador
    private Coordinador coordinador;
    private String mensage;

    public static void main(String[] args) throws EquipoExistenteExeption {
        AplicacionConsultas app = new AplicacionConsultas();

        new InterfazUI().setVisible(true);

    }

    private void iniciar() throws EquipoExistenteExeption {
        /* Se instancian las clases */
        red = Red.getRed();
        calculo = new Calculo();
        coordinador = new Coordinador();
        interfaz = new InterfazUI();

        calculo.setCoordinador(coordinador);

        coordinador.setCalculo(calculo);

        coordinador.setRed(red);
    }

    public void consultar() {
        calculo.cargarDatos(red.getTablaEquipos(), red.getConexiones());

    }

    public String ping(String parametro1) {
        String id = red.validarEquipo(parametro1);
        if (id != null) {
            mensage = ("Equipo '" + id + "' " + (calculo.ping(id) ? "activo" : "inactivo"));

        } else {
            mensage = ("El equipo '" + parametro1 + "' no se ha encontrado en la red");
        }
        return mensage;
    }

    private String traceRoute(String parametro1, String parametro2) {
        String id1 = red.validarEquipo(parametro1);
        String id2 = red.validarEquipo(parametro2);
        List<String> resultado = null;
        if (id1 != null && id2 != null)
            resultado = calculo.traceRoute(id1, id2);
        if (resultado == null)
            mensage = ("Al menos un equipo no se ha encontrado en la red");
        else if (resultado.isEmpty())
            mensage = ("No se ha encontrado un camino de equipos activos");
        else if (!resultado.get(0).equals(id1) || !resultado.get(resultado.size() - 1).equals(id2))
            mensage = ("No se ha encontrado un camino de equipos activos");
        else {
            mensage = ("Camino encontrado:");
            for (int i = 0; i < resultado.size() - 1; i++) {
                id1 = resultado.get(i);
                id2 = resultado.get(i + 1);
                mensage = ("- " + id1 + " -> " + id2);
            }

        }
        return mensage;
    }

    private String rangoPing(String parametro1) {
        List<String> equiposConEsaIP = red.rangoEquiposIP(parametro1);
        if (equiposConEsaIP.isEmpty())
            mensage = ("Ningun equipo encontrado");
        else {
            Map<String, Boolean> resultado = calculo.rangoPing(equiposConEsaIP);
            mensage = ("Estado de los equipos: \n");
            for (Entry<String, Boolean> entry : resultado.entrySet()) {
                String id = entry.getKey();
                boolean isActivo = entry.getValue();
                mensage = (id + " " + (isActivo ? "activo" : "inactivo"));

            }
        }
        return mensage;
    }

}
