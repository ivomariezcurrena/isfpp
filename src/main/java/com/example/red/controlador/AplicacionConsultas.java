package com.example.red.controlador;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
        interfaz.mostrarMenu();
        bucleEntradaConsola();
        interfaz.cerrarConsola();
        interfaz.cerrar();
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

            interfaz.setEstiloNodoTodos("default");
            if (comando.equals("exit")) // salir
                break;

            else if (comando.equals("")) // Ignorar entrada
                continue;

            else if (comando.equals("help")) // ayuda
                interfaz.mostrarMenu();

            else if (comando.equals("ping") && !parametro1.equals("")){ // ping
                interfaz.mostrar("Equipo '" + parametro1 + "' " + (calculo.ping(parametro1) ? "activo" : "inactivo"));
                interfaz.setEstiloNodo(parametro1, "highlight");
            }
            else if (comando.equals("traceroute") && !parametro1.equals("") && !parametro2.equals("")){ // traceroute
                List<String> resultado = calculo.traceRoute(parametro1, parametro2);
                if (resultado == null)
                    interfaz.mostrar("Al menos un equipo no se ha encontrado en la red");
                if (resultado.isEmpty())
                    interfaz.mostrar("No se ha encontrado un camino de equipos activos");
                else 
                    interfaz.setEstiloCaminoNodos(resultado, "highlight");
                
            } 
            else if (comando.equals("rango") && !parametro1.equals("")) {
                Map<String, Boolean> resultado = calculo.rangoPing(parametro1);
                interfaz.mostrar("Estado de los equipos: \n");
                for (Entry<String, Boolean> entry : resultado.entrySet()) {
                    interfaz.mostrar(entry.getKey() + " " + (entry.getValue() ? "activo" : "inactivo"));
                    interfaz.setEstiloNodo(entry.getKey(), "highlight");
                }
            } 
            else
                interfaz.mostrarError("Error de sintaxis\nPara más información por favor ingrese 'help'"); // error de
                                                                                              // sintaxis
        }
    }
}
