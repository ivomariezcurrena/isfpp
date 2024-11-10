 package com.example.red.controlador;

import com.example.red.conexion.ConexionBD;
import com.example.red.negocio.Calculo;
import com.example.red.negocio.EquipoExistenteExeption;
import com.example.red.negocio.Red;
import com.example.red.gui.app.Main;

public class AplicacionConsultas {
    private Red red;
    private Calculo calculo;

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
        calculo.setCoordinador(coordinador);
        coordinador.setCalculo(calculo);
        coordinador.setRed(red);
    }

    private void consultar() {
        calculo.cargarDatos(red.getTablaEquipos(), red.getConexiones());

        // vista
        Main.main(null);
    }
}
