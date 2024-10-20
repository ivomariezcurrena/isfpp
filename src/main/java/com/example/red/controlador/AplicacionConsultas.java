package com.example.red.controlador;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.example.red.conexion.ConexionBD;
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
        app.consultar();
    }

    private void iniciar() throws EquipoExistenteExeption {
        /* Se instancian las clases */
        try {
            ConexionBD.getInstance().connectToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        red = Red.getRed();
        calculo = new Calculo();
        coordinador = new Coordinador();
        interfaz = new Interfaz();

        calculo.setCoordinador(coordinador);
        interfaz.setCoordinador(coordinador);

        coordinador.setCalculo(calculo);
        coordinador.setInterfaz(interfaz);
        coordinador.setRed(red);
    }

    private void consultar() {
        calculo.cargarDatos(red.getTablaEquipos(), red.getConexiones());
        interfaz.cargarDatos(red.getTablaEquipos(), red.getTablaConexiones());

        interfaz.iniciarConsola();
        interfaz.iniciarInterfaz();
        interfaz.mostrarMenu();
        bucleEntradaConsola(); // Se termina cuando el usuario desea salir
        interfaz.cerrarConsola();
        interfaz.cerrarInterfaz();
    }

    private void bucleEntradaConsola() {
        String[] instruccion;
        String comando = "";
        String parametro1 = "";
        String parametro2 = "";

        while (!comando.equals("exit")) {
            // Formato: { "Comando elegido", "1er parametro", "2do parametro" }
            instruccion = interfaz.getInstruccion();
            comando = instruccion[0];
            parametro1 = instruccion.length > 1 ? instruccion[1] : "";
            parametro2 = instruccion.length > 2 ? instruccion[2] : "";

            interfaz.setEstiloNodosTodos("default");
            interfaz.setEstiloArcosTodos("default");

            if (comando.equals("exit")) // salir
                break;

            else if (comando.equals("")) // Ignorar entrada
                continue;

            else if (comando.equals("help"))
                interfaz.mostrarMenu();

            else if (comando.equals("ping") && !parametro1.equals(""))
                ping(parametro1);

            else if (comando.equals("traceroute") && !parametro1.equals("") && !parametro2.equals(""))
                traceRoute(parametro1, parametro2);

            else if (comando.equals("rango") && !parametro1.equals(""))
                rangoPing(parametro1);

            else // error de sintaxis
                interfaz.mostrarError("Error de sintaxis\nPara más información por favor ingrese 'help'");
        }
    }

    // DEBERIA ESTAR EN INTERFAZ
    private void ping(String parametro1) {
        String id = red.validarEquipo(parametro1);
        if (id != null) {
            interfaz.mostrar("Equipo '" + id + "' " + (calculo.ping(id) ? "activo" : "inactivo"));
            interfaz.setEstiloNodo(id, "highlight");
        } else {
            interfaz.mostrar("El equipo '" + parametro1 + "' no se ha encontrado en la red");
        }
    }

    private void traceRoute(String parametro1, String parametro2) {
        String id1 = red.validarEquipo(parametro1);
        String id2 = red.validarEquipo(parametro2);
        List<String> resultado = null;
        if (id1 != null && id2 != null)
            resultado = calculo.traceRoute(id1, id2);
        if (resultado == null)
            interfaz.mostrar("Al menos un equipo no se ha encontrado en la red");
        else if (resultado.isEmpty())
            interfaz.mostrar("No se ha encontrado un camino de equipos activos");
        else if (!resultado.get(0).equals(id1) || !resultado.get(resultado.size() - 1).equals(id2))
            interfaz.mostrar("No se ha encontrado un camino de equipos activos");
        else {
            interfaz.mostrar("Camino encontrado:");
            for (int i = 0; i < resultado.size() - 1; i++) {
                id1 = resultado.get(i);
                id2 = resultado.get(i + 1);
                interfaz.mostrar("- " + id1 + " -> " + id2);
            }
            interfaz.setEstiloNodos(resultado, "highlight");
            interfaz.setEstiloCaminoArcos(resultado, "highlight");
        }
    }

    private void rangoPing(String parametro1) {
        List<String> equiposConEsaIP = red.rangoEquiposIP(parametro1);
        if (equiposConEsaIP.isEmpty())
            interfaz.mostrar("Ningun equipo encontrado");
        else {
            Map<String, Boolean> resultado = calculo.rangoPing(equiposConEsaIP);
            interfaz.mostrar("Estado de los equipos: \n");
            for (Entry<String, Boolean> entry : resultado.entrySet()) {
                String id = entry.getKey();
                boolean isActivo = entry.getValue();
                interfaz.mostrar(id + " " + (isActivo ? "activo" : "inactivo"));
                interfaz.setEstiloNodo(id, "highlight");
            }
        }
    }
}
