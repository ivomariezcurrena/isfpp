package com.example.red.controlador;

import com.example.red.conexion.ConexionBD;
import com.example.red.negocio.Calculo;
import com.example.red.negocio.EquipoExistenteExeption;
import com.example.red.negocio.Red;
import com.example.red.gui.app.Main;

/**
 * Clase principal que inicia la ejecución de la aplicación
 */
public class AplicacionConsultas {
    /** Referencia al objeto que modela la red */
    private Red red;

    /** Referencia al objeto que permite realizar cálculos */
    private Calculo calculo;

    /** Referencia al coordinador */
    private Coordinador coordinador;

    /**
     * Método que inicia la ejecución
     */
    public static void main(String[] args) throws EquipoExistenteExeption {
        AplicacionConsultas app = new AplicacionConsultas();
        app.iniciar();
        app.consultar();
    }

    /**
     * Inicia la obtención de datos de los objetos necesarios para el funcionamiento
     * 
     * @throws EquipoExistenteExeption si sucede un error de equipo duplicado
     */
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
        calculo.setCoordinador(coordinador);
        coordinador.setCalculo(calculo);
        coordinador.setRed(red);
    }

    /**
     * Inicia la carga de datos en el sistema y la interfaz para realizar
     * operaciones
     */
    private void consultar() {
        calculo.cargarDatos(red.getTablaEquipos(), red.getConexiones());

        // vista
        Main.main(null);
    }
}
